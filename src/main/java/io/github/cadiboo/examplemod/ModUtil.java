package io.github.cadiboo.examplemod;

import net.minecraft.util.Direction;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;

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
	 * This method calculates a comparator output for how "full" the energy storage is.
	 *
	 * @param energy The energy storage to test.
	 * @return A redstone value in the range [0,15] representing how "full" this energy storage is.
	 */
	public static int calcRedstoneFromEnergyStorage(final EnergyStorage energy) {
		if (energy == null)
			return 0;
		return Math.round(energy.getEnergyStored() / ((float) energy.getMaxEnergyStored()) * 15F);
	}

}
