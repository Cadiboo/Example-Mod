package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.block.HeatCollectorBlock;
import io.github.cadiboo.examplemod.container.HeatCollectorContainer;
import io.github.cadiboo.examplemod.energy.SettableEnergyStorage;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
public class HeatCollectorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	// Cache all the directions instead of calling Direction.values()
	// each time (because each call creates a new Direction[] which is wasteful)
	// TODO: change to Direction.VALUES once it's ATed
	private static final Direction[] DIRECTIONS = Direction.values();

	public final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			return !stack.isEmpty() && FurnaceTileEntity.isFuel(stack);
		}

		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			// Mark the tile entity as having changed whenever its inventory changes.
			// "markDirty" tells vanilla that the chunk containing the tile entity has
			// changed and means the game will save the chunk to disk later.
			HeatCollectorTileEntity.this.markDirty();
		}
	};
	public final SettableEnergyStorage energy = new SettableEnergyStorage(100_000);
	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	private final LazyOptional<EnergyStorage> energyCapabilityExternal = LazyOptional.of(() -> this.energy);

	private int lastSyncedEnergy = -1;

	public HeatCollectorTileEntity() {
		super(ModTileEntityTypes.HEAT_COLLECTOR);
	}

	@Override
	public void tick() {

		final World world = this.world;
		if (world == null || world.isRemote)
			return;

		final BlockPos pos = this.pos;
		final SettableEnergyStorage energy = this.energy;

		final ItemStack fuelStack = this.inventory.getStackInSlot(0);
		if (!fuelStack.isEmpty()) {
			int energyToRecieve = ForgeHooks.getBurnTime(fuelStack);
			// Only use the stack if we can receive 100% of the energy from it
			if (energy.receiveEnergy(energyToRecieve, true) == energyToRecieve) {
				energy.receiveEnergy(energyToRecieve, false);
				fuelStack.shrink(1);
			}
		}

		// How much energy to try and transfer in each direction.
		final int transferAmount = 100;

		// Skip trying to transfer if there isn't enough energy to transfer
		if (energy.getEnergyStored() >= transferAmount) {
			for (Direction direction : DIRECTIONS) {
				final TileEntity te = world.getTileEntity(pos.offset(direction));
				if (te == null) {
					continue;
				}
				te.getCapability(CapabilityEnergy.ENERGY, direction).ifPresent(otherTileEnergy -> {
					if (!otherTileEnergy.canReceive()) {
						// Optimisation: Skip this tile if it can't receive
						return;
					}
					energy.extractEnergy(
							otherTileEnergy.receiveEnergy(
									energy.extractEnergy(100, true),
									false
							),
							false
					);
				});
			}
		}

		// If the energy has changed.
		if (lastSyncedEnergy != energy.getEnergyStored()) {

			// This code is commented out since our energy is only ever used client-side
			// by our GUI and we use our Container to sync energy in the GUI.

//			// "markDirty" tells vanilla that the chunk containing the tile entity has
//			// changed and means the game will save the chunk to disk later.
//			this.markDirty();
//
//			// Notify clients of a block update.
//			// This will result in the packet from getUpdatePacket being sent to the client
//			// and our energy being synced.
//			final BlockState blockState = this.getBlockState();
//			// Flag 2: Send the change to clients
//			world.notifyBlockUpdate(pos, blockState, blockState, 2);
//
			// Update the last synced energy to the current energy
			lastSyncedEnergy = energy.getEnergyStored();
		}

	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventoryCapabilityExternal.cast();
		if (cap == CapabilityEnergy.ENERGY)
			return energyCapabilityExternal.cast();
		return super.getCapability(cap, side);
	}

	/**
	 * Handle a packet created in {@link #getUpdatePacket()}
	 */
	@Override
	public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
		// This code is commented out since our energy is only ever used client-side
		// by our GUI and we use our Container to sync energy in the GUI.
//		this.energy.setEnergy(pkt.getNbtCompound().getInt("energy"));
	}

	@Override
	public void onLoad() {
		// We set this in onLoad instead of the constructor so that TileEntities
		// constructed from NBT (saved tile entities) have this set to the proper value
		lastSyncedEnergy = energy.getEnergyStored();
	}

	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound("inventory"));
		this.energy.setEnergy(compound.getInt("energy"));
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put("inventory", this.inventory.serializeNBT());
		compound.putInt("energy", this.energy.getEnergyStored());
		return compound;
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is re-synced via World#notifyBlockUpdate.
	 * This packet comes back client-side in {@link #onDataPacket}
	 */
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		// This code is commented out since our energy is only ever used client-side
		// by our GUI and we use our Container to sync energy in the GUI.
//		final CompoundNBT tag = new CompoundNBT();
//		tag.putInt("energy", this.energy.getEnergyStored());
//		return new SUpdateTileEntityPacket(this.pos, 0, tag);
		return null;
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.HEAT_COLLECTOR.getTranslationKey());
	}

	/**
	 * Called from {@link NetworkHooks#openGui}
	 * (which is called from {@link HeatCollectorBlock#onBlockActivated} on the logical server)
	 *
	 * @return The logical-server-side Container for this TileEntity
	 */
	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new HeatCollectorContainer(windowId, this, inventory, player);
	}

}
