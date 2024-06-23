package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageModifiable extends EnergyStorage {
	public EnergyStorageModifiable(int capacity) {
		super(capacity);
	}

	public void setEnergyStored(int energy) {
		this.energy = energy;
	}
}