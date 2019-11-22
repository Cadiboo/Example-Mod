package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.block.ElectricFurnaceBlock;
import io.github.cadiboo.examplemod.container.ModFurnaceContainer;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Cadiboo
 */
public class ModFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public static final int FUEL_SLOT = 0;
	public static final int INPUT_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	private static final String INVENTORY_TAG = "inventory";
	private static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
	private static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
	private static final String FUEL_BURN_TIME_LEFT_TAG = "fuelBurnTimeLeft";
	private static final String MAX_FUEL_BURN_TIME_TAG = "maxFuelBurnTime";

	public final ItemStackHandler inventory = new ItemStackHandler(3) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			switch (slot) {
				case FUEL_SLOT:
					return FurnaceTileEntity.isFuel(stack);
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
			ModFurnaceTileEntity.this.markDirty();
		}
	};

	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);

	public short smeltTimeLeft = -1;
	public short maxSmeltTime = -1;
	public short fuelBurnTimeLeft = -1;
	public short maxFuelBurnTime = -1;
	public boolean lastBurning = false;

	public ModFurnaceTileEntity() {
		super(ModTileEntityTypes.MOD_FURNACE);
	}

	private boolean isInput(final ItemStack stack) {
		if (stack.isEmpty())
			return false;
		return getRecipe(stack).isPresent();
	}

	private boolean isOutput(final ItemStack stack) {
		final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
		return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
	}

	private Optional<FurnaceRecipe> getRecipe(final ItemStack input) {
		return getRecipe(new Inventory(input));
	}

	private Optional<FurnaceRecipe> getRecipe(final Inventory dummyInventory) {
		return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, dummyInventory, world);
	}

	private Optional<ItemStack> getResult(final ItemStack input) {
		final Inventory dummyInventory = new Inventory(input);
		return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
	}

	@Override
	public void tick() {
		if (world == null || world.isRemote)
			return;

		boolean hasFuel = false;
		if (isBurning()) {
			hasFuel = true;
			--fuelBurnTimeLeft;
		}

		final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
		final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);
		if (!result.isEmpty() && isInput(input)) {
			final boolean canInsertResultIntoOutput = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
			if (canInsertResultIntoOutput) {
				if (!hasFuel)
					if (burnFuel())
						hasFuel = true;
				if (hasFuel) {
					if (smeltTimeLeft == -1) { // Item just got inserted
						smeltTimeLeft = maxSmeltTime = getSmeltTime(input);
					} else { // Item was already in here
						--smeltTimeLeft;
						if (smeltTimeLeft == 0) {
							inventory.insertItem(OUTPUT_SLOT, result, false);
							input.shrink(1);
							inventory.setStackInSlot(INPUT_SLOT, input); // Update the data
							if (input.hasContainerItem()) {
								final ItemStack containerItem = input.getContainerItem();
								final boolean canInsertContainerItemIntoInput = inventory.insertItem(INPUT_SLOT, containerItem, true).isEmpty();
								if (canInsertContainerItemIntoInput)
									inventory.insertItem(INPUT_SLOT, containerItem, false);
								else // Drop the container item if we can't insert it
									InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), containerItem);
							}
						}
					}
				} else // No fuel -> add to smelt time to simulate cooling
					if (smeltTimeLeft < maxSmeltTime)
						++smeltTimeLeft;
			}
		} else // Invalid input (somehow)
			smeltTimeLeft = maxSmeltTime = -1;

		// If the burning state has changed.
		if (lastBurning != this.isBurning()) {

			// "markDirty" tells vanilla that the chunk containing the tile entity has
			// changed and means the game will save the chunk to disk later.
			this.markDirty();

			// Notify clients of a block update.
			// This will result in the packet from getUpdatePacket being sent to the client
			// and our burning being synced.
			final BlockState blockState = this.getBlockState();
			// Flag 2: Send the change to clients
			world.notifyBlockUpdate(pos, blockState, blockState, 2);

			// Update the last synced burning state to the current burning state
			lastBurning = this.isBurning();
		}

	}

	private short getSmeltTime(final ItemStack input) {
		final Optional<FurnaceRecipe> recipe = getRecipe(input);
		if (recipe.isPresent())
			return (short) recipe.get().getCookTime();
		return 200;
	}

	/**
	 * @return If the fuel was burnt
	 */
	private boolean burnFuel() {
		final ItemStack fuelStack = inventory.getStackInSlot(FUEL_SLOT);
		if (!fuelStack.isEmpty()) {
			final int burnTime = ForgeHooks.getBurnTime(fuelStack);
			if (burnTime > 0) {
				this.fuelBurnTimeLeft = this.maxFuelBurnTime = ((short) burnTime);
				fuelStack.shrink(1);
				return true;
			}
		}
		return false;
	}

	public boolean isBurning() {
		return this.fuelBurnTimeLeft > 0;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventoryCapabilityExternal.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		// We set this in onLoad instead of the constructor so that TileEntities
		// constructed from NBT (saved tile entities) have this set to the proper value
		if (world != null && !world.isRemote)
			lastBurning = isBurning();
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
		this.fuelBurnTimeLeft = compound.getShort(FUEL_BURN_TIME_LEFT_TAG);
		this.maxFuelBurnTime = compound.getShort(MAX_FUEL_BURN_TIME_TAG);
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
		compound.putShort("smeltTimeLeft", this.smeltTimeLeft);
		compound.putShort("maxSmeltTime", this.maxSmeltTime);
		compound.putShort("fuelBurnTimeLeft", this.fuelBurnTimeLeft);
		compound.putShort("maxFuelBurnTime", this.maxFuelBurnTime);
		return compound;
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
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.MOD_FURNACE.getTranslationKey());
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
		return new ModFurnaceContainer(windowId, inventory, this);
	}

}
