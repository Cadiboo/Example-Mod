package io.github.cadiboo.examplemod;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Assorted common utility code
 *
 * @author Cadiboo
 */
public final class ModUtil {

	/**
	 * Cache all the directions instead of calling Direction.values()
	 * each time (because each call creates a new Direction[] which is wasteful)
	 * TODO: change to Direction.VALUES once it's ATed
	 */
	public static final Direction[] DIRECTIONS = Direction.values();

	/**
	 * Returns null, while claiming to never return null.
	 * Useful for constants with @ObjectHolder who's values are null at compile time, but not at runtime
	 *
	 * @return null
	 */
	@Nonnull
	// Get rid of "Returning null from Nonnull method" warnings
	@SuppressWarnings("ConstantConditions")
	public static <T> T _null() {
		return null;
	}

	/**
	 * Gets the HeatCollectorTileEntity at the position or crashes
	 *
	 * @param playerInventory The {@link PlayerInventory} to get the {@link World} from
	 * @param data            The data containing the blockpos
	 * @param tileEntityClass The class of the TileEntity
	 * @return The HeatCollectorTileEntity at the position
	 * @throws ReportedException if the TileEntity at the position is null or not a HeatCollectorTileEntity
	 */
	public static <T extends TileEntity> T getTileEntityOrCrash(final PlayerInventory playerInventory, final PacketBuffer data, final Class<T> tileEntityClass) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		Objects.requireNonNull(tileEntityClass, "tileEntityClass cannot be null!");
		final BlockPos pos = data.readBlockPos();
		final World world = playerInventory.player.world;
		final TileEntity tileAtPos = world.getTileEntity(pos);

		final Throwable error;
		if (tileAtPos == null)
			error = new NullPointerException("No TileEntity at position");
		else if (!tileEntityClass.isAssignableFrom(tileAtPos.getClass()))
			error = new ClassCastException(tileAtPos.getClass() + " is not a " + tileEntityClass);
		else
			return (T) tileAtPos;
		CrashReport crashReport = CrashReport.makeCrashReport(error, "Creating Container for a HeatCollectorTileEntity");
		CrashReportCategory category = crashReport.makeCategory("Block at position");
		CrashReportCategory.addBlockInfo(category, pos, world.getBlockState(pos));
		throw new ReportedException(crashReport);
	}

}
