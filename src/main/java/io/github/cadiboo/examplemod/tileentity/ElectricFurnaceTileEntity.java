package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.block.ElectricFurnaceBlock;
import io.github.cadiboo.examplemod.config.ExampleModConfig;
import io.github.cadiboo.examplemod.container.ElectricFurnaceContainer;
import io.github.cadiboo.examplemod.energy.SettableEnergyStorage;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Cadiboo
 */
public class ElectricFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;

	private static final String INVENTORY_TAG = "inventory";
	private static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
	private static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
	private static final String ENERGY_TAG = "energy";
	public final ItemStackHandler inventory = new ItemStackHandler(2) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			switch (slot) {
				case INPUT_SLOT:
					return isInput(stack);
				case OUTPUT_SLOT:
					return isOutput(stack);
				default:
					return false;
			}
		}

		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			// Mark the tile entity as having changed whenever its inventory changes.
			// "markDirty" tells vanilla that the chunk containing the tile entity has
			// changed and means the game will save the chunk to disk later.
			ElectricFurnaceTileEntity.this.markDirty();
		}
	};
	public final SettableEnergyStorage energy = new SettableEnergyStorage(100_000);

	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	// Machines (hoppers, pipes) connected to this furnace's top can only insert/extract items from the input slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
	// Machines (hoppers, pipes) connected to this furnace's bottom can only insert/extract items from the output slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));
	private final LazyOptional<EnergyStorage> energyCapabilityExternal = LazyOptional.of(() -> this.energy);

	public short smeltTimeLeft = -1;
	public short maxSmeltTime = -1;
	private int lastEnergy = -1;

	public ElectricFurnaceTileEntity() {
		super(ModTileEntityTypes.ELECTRIC_FURNACE.get());
	}

	/**
	 * @return If the stack is not empty and has a smelting recipe associated with it
	 */
	private boolean isInput(final ItemStack stack) {
		if (stack.isEmpty())
			return false;
		return getRecipe(stack).isPresent();
	}

	/**
	 * @return If the stack's item is equal to the result of smelting our input
	 */
	private boolean isOutput(final ItemStack stack) {
		final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
		return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
	}

	/**
	 * @return The smelting recipe for the input stack
	 */
	private Optional<FurnaceRecipe> getRecipe(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe so we make one here.
		return getRecipe(new Inventory(input));
	}

	/**
	 * @return The smelting recipe for the inventory
	 */
	private Optional<FurnaceRecipe> getRecipe(final IInventory inventory) {
		return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);
	}

	/**
	 * @return The result of smelting the input stack
	 */
	private Optional<ItemStack> getResult(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe and
		// AbstractCookingRecipe#getCraftingResult() so we make one here.
		final Inventory dummyInventory = new Inventory(input);
		return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
	}

	/**
	 * Called every tick to update our tile entity
	 */
	@Override
	public void tick() {
		if (world == null || world.isRemote)
			return;

		// Energy will get pushed into our machine by generators/wires

		// Smelting code

		final ItemStack input = inventory.getStackInSlot(INPUT_SLOT).copy();
		final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);

		if (!result.isEmpty() && isInput(input)) {
			final boolean canInsertResultIntoOutput = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
			if (canInsertResultIntoOutput) {
				// Energy consuming code
				final int energySmeltCostPerTick = ExampleModConfig.electricFurnaceEnergySmeltCostPerTick;
				boolean hasEnergy = false;
				if (energy.extractEnergy(energySmeltCostPerTick, true) == energySmeltCostPerTick) {
					hasEnergy = true;
					energy.extractEnergy(energySmeltCostPerTick, false);
				}
				if (hasEnergy) {
					if (smeltTimeLeft == -1) { // Item has not been smelted before
						smeltTimeLeft = maxSmeltTime = getSmeltTime(input);
					} else { // Item was already being smelted
						--smeltTimeLeft;
						if (smeltTimeLeft == 0) {
							inventory.insertItem(OUTPUT_SLOT, result, false);
							if (input.hasContainerItem())
								inventory.setStackInSlot(INPUT_SLOT, input.getContainerItem());
							else {
								input.shrink(1);
								inventory.setStackInSlot(INPUT_SLOT, input); // Update the data
							}
							smeltTimeLeft = -1; // Set to -1 so we smelt the next stack on the next tick
						}
					}
				} else // No energy -> add to smelt time left to simulate cooling
					if (smeltTimeLeft < maxSmeltTime)
						++smeltTimeLeft;
			}
		} else // We have an invalid input stack (somehow)
			smeltTimeLeft = maxSmeltTime = -1;

		// If the energy has changed.
		if (lastEnergy != energy.getEnergyStored()) {

			// "markDirty" tells vanilla that the chunk containing the tile entity has
			// changed and means the game will save the chunk to disk later.
			this.markDirty();

			// Notify clients of a block update.
			// This will result in the packet from getUpdatePacket being sent to the client
			// and our energy being synced.
			final BlockState blockState = this.getBlockState();
			// Flag 2: Send the change to clients
			world.notifyBlockUpdate(pos, blockState, blockState, 2);

			// Update the last synced energy to the current energy
			lastEnergy = energy.getEnergyStored();
		}

	}

	/**
	 * Mimics the code in {@link AbstractFurnaceTileEntity#func_214005_h()}
	 *
	 * @return The custom smelt time or 200 if there is no recipe for the input
	 */
	private short getSmeltTime(final ItemStack input) {
		return getRecipe(input)
				.map(AbstractCookingRecipe::getCookTime)
				.orElse(200)
				.shortValue();
	}

	/**
	 * Retrieves the Optional handler for the capability requested on the specific side.
	 *
	 * @param cap  The capability to check
	 * @param side The Direction to check from. CAN BE NULL! Null is defined to represent 'internal' or 'self'
	 * @return The requested an optional holding the requested capability.
	 */
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == null)
				return inventoryCapabilityExternal.cast();
			switch (side) {
				case DOWN:
					return inventoryCapabilityExternalDown.cast();
				case UP:
				case NORTH:
				case SOUTH:
				case WEST:
				case EAST:
					return inventoryCapabilityExternalUpAndSides.cast();
			}
		}
		if (cap == CapabilityEnergy.ENERGY)
			return energyCapabilityExternal.cast();
		return super.getCapability(cap, side);
	}

	/**
	 * Handle a packet created in {@link #getUpdatePacket()}
	 */
	@Override
	public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
		this.energy.setEnergy(pkt.getNbtCompound().getInt(ENERGY_TAG));
	}

	@Override
	public void onLoad() {
		super.onLoad();
		// We set this in onLoad instead of the constructor so that TileEntities
		// constructed from NBT (saved tile entities) have this set to the proper value
		if (world != null && !world.isRemote)
			lastEnergy = energy.getEnergyStored();
	}

	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
		this.smeltTimeLeft = compound.getShort(SMELT_TIME_LEFT_TAG);
		this.maxSmeltTime = compound.getShort(MAX_SMELT_TIME_TAG);
		this.energy.setEnergy(compound.getInt(ENERGY_TAG));
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
		compound.putShort(SMELT_TIME_LEFT_TAG, this.smeltTimeLeft);
		compound.putShort(MAX_SMELT_TIME_TAG, this.maxSmeltTime);
		compound.putInt(ENERGY_TAG, this.energy.getEnergyStored());
		return compound;
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is re-synced via World#notifyBlockUpdate.
	 * This packet comes back client-side in {@link #onDataPacket}
	 */
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		final CompoundNBT tag = new CompoundNBT();
		tag.putInt(ENERGY_TAG, this.energy.getEnergyStored());
		// We pass 0 for tileEntityTypeIn because we have a modded TE. See ClientPlayNetHandler#handleUpdateTileEntity(SUpdateTileEntityPacket)
		return new SUpdateTileEntityPacket(this.pos, 0, tag);
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the
	 * chunk or when many blocks change at once.
	 * This compound comes back to you client-side in {@link #handleUpdateTag}
	 * The default implementation ({@link TileEntity#handleUpdateTag}) calls {@link #writeInternal)}
	 * which doesn't save any of our extra data so we override it to call {@link #write} instead
	 */
	@Nonnull
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	/**
	 * Invalidates our tile entity
	 */
	@Override
	public void remove() {
		super.remove();
		// We need to invalidate our capability references so that any cached references (by other mods) don't
		// continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
		inventoryCapabilityExternal.invalidate();
		energyCapabilityExternal.invalidate();
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.ELECTRIC_FURNACE.get().getTranslationKey());
	}

	/**
	 * Called from {@link NetworkHooks#openGui}
	 * (which is called from {@link ElectricFurnaceBlock#onBlockActivated} on the logical server)
	 *
	 * @return The logical-server-side Container for this TileEntity
	 */
	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new ElectricFurnaceContainer(windowId, inventory, this);
	}

}
