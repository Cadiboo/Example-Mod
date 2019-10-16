package io.github.cadiboo.examplemod.energy;

import net.minecraftforge.energy.EnergyStorage;

/**
 * @author Cadiboo
 */
public class SettableEnergyStorage extends EnergyStorage {

	public SettableEnergyStorage(final int capacity) {
		super(capacity);
	}

	public SettableEnergyStorage(final int capacity, final int maxTransfer) {
		super(capacity, maxTransfer);
	}

	public SettableEnergyStorage(final int capacity, final int maxReceive, final int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public SettableEnergyStorage(final int capacity, final int maxReceive, final int maxExtract, final int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	/**
	 * @return The amount of energy that was put into the storage
	 */
	public int setEnergy(final int maxSet) {
		return this.energy = Math.min(this.capacity, maxSet);
	}

}
