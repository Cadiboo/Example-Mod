package io.github.cadiboo.examplemod.container;

import io.github.cadiboo.examplemod.energy.SettableEnergyStorage;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.init.ModContainerTypes;
import io.github.cadiboo.examplemod.tileentity.HeatCollectorTileEntity;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/*
 The following is commented out because we handle energy syncing in our HeatCollectorTileEntity
 and because container data syncing like this only works for shorts, not full integers
 * Energy is synced with
 * Server: Each tick {@link #detectAndSendChanges()} is called ({@link ServerPlayerEntity#tick()})
 * Server: The (tracked) value of the tile's energy is updated ({@link #updateProgressBar(int, int)})
 * Server: If the value is different from the value last sent to the client ({@link IntReferenceHolder#isDirty()}),
 * it is synced to the client ({@link ServerPlayerEntity#sendWindowProperty(Container, int, int)})
 * Client: The sync packet is received ({@link ClientPlayNetHandler#handleWindowProperty(SWindowPropertyPacket)})
 * and the tracked value of is updated ({@link Container#updateProgressBar(int, int)})
 * Client: The tile's energy is set to the new value ({@link EnergyReferenceHolder#set(int)})
 */

/**
 * @author Cadiboo
 */
public class HeatCollectorContainer extends Container {

	public final HeatCollectorTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	/**
	 * Logical-server-side constructor, called from {@link HeatCollectorTileEntity#createMenu}
	 */
	public HeatCollectorContainer(final int windowId, final HeatCollectorTileEntity tileEntity, final PlayerInventory playerInventory, final PlayerEntity player) {
		super(ModContainerTypes.HEAT_COLLECTOR, windowId);

		canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		this.tileEntity = tileEntity;

		// The following code is commented out because we handle energy syncing in our HeatCollectorTileEntity
//		// Add tracking for energy server-side (Syncs to client when it changes)
//		this.trackInt(IntReferenceHolder.single()).set(tileEntity.energy.getEnergyStored());

		addSlots(tileEntity, playerInventory);

	}

	/**
	 * Logical-client-side constructor, called from {@link ContainerType#create(IContainerFactory)}
	 */
	public HeatCollectorContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer data) {
		super(ModContainerTypes.HEAT_COLLECTOR, windowId);

		final HeatCollectorTileEntity tileEntity = getHeatCollectorTileEntity(playerInventory, data);
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		this.tileEntity = tileEntity;

		// The following code is commented out because we handle energy syncing in our HeatCollectorTileEntity
//		// Add tracking for energy client-side (Updates value when it changes)
//		this.trackInt(new EnergyReferenceHolder(tileEntity.energy));

		addSlots(tileEntity, playerInventory);
	}

	/**
	 * Gets the HeatCollectorTileEntity at the position or crashes
	 *
	 * @param playerInventory The {@link PlayerInventory} to get the {@link World} from
	 * @param data            The data containing the blockpos
	 * @return The HeatCollectorTileEntity at the position
	 * @throws ReportedException if the TileEntity at the position is null or not a HeatCollectorTileEntity
	 */
	private HeatCollectorTileEntity getHeatCollectorTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		final BlockPos pos = data.readBlockPos();
		final World world = playerInventory.player.world;
		final TileEntity tileAtPos = world.getTileEntity(pos);

		if (!(tileAtPos instanceof HeatCollectorTileEntity)) {
			final Throwable error;
			if (tileAtPos == null) // instanceof returns false for null, so we check it here
				error = new NullPointerException("No HeatCollectorTileEntity at position");
			else
				error = new ClassCastException(tileAtPos.getClass() + " is not a HeatCollectorTileEntity");
			CrashReport crashReport = CrashReport.makeCrashReport(error, "Creating Container for a HeatCollectorTileEntity");
			CrashReportCategory category = crashReport.makeCategory("Block at position");
			CrashReportCategory.addBlockInfo(category, pos, world.getBlockState(pos));
			throw new ReportedException(crashReport);
		}
		return (HeatCollectorTileEntity) tileAtPos;
	}

	/**
	 * Adds all the slots for the tileEntity's inventory and the playerInventory to this container
	 */
	private void addSlots(final HeatCollectorTileEntity tileEntity, final PlayerInventory playerInventory) {
		// Tile inventory slot
		this.addSlot(new SlotItemHandler(tileEntity.inventory, 0, 80, 35));

		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 84;
		final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

		// Player Top Inventory slots
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
			}
		}

		final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
		// Player Hotbar slots
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
		}
	}

	@Override
	public void detectAndSendChanges() {
		// The following code is commented out because we handle energy syncing in our HeatCollectorTileEntity
//		// Update energy server-side (Syncs to client when it changes)
//		updateProgressBar(0, tileEntity.energy.getEnergyStored());
		super.detectAndSendChanges();
	}

	/**
	 * Generic & dynamic version of {@link Container#transferStackInSlot(PlayerEntity, int)}.
	 * Handle when the stack in slot {@code index} is shift-clicked.
	 * Normally this moves the stack between the player inventory and the other inventory(s).
	 *
	 * @param player the player passed in
	 * @param index  the index passed in
	 * @return the {@link ItemStack}
	 */
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if ((slot != null) && slot.getHasStack()) {
			final ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!mergeItemStack(itemstack1, containerSlots, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}

	@Override
	public boolean canInteractWith(@Nonnull final PlayerEntity player) {
		return isWithinUsableDistance(canInteractWithCallable, player, ModBlocks.HEAT_COLLECTOR);
	}

	/**
	 * An {@link IntReferenceHolder} backed by a {@link SettableEnergyStorage}
	 */
	private static class EnergyReferenceHolder extends IntReferenceHolder {

		private final SettableEnergyStorage energy;

		public EnergyReferenceHolder(final SettableEnergyStorage energy) {
			this.energy = energy;
		}

		@Override
		public int get() {
			return energy.getEnergyStored();
		}

		@Override
		public void set(final int newValue) {
			energy.setEnergy(newValue);
		}

	}

}
