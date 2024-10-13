package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class Properties {
	@Config.Name("Only Use Thermal Copper And Steel Ingot")
	public boolean ONLY_USE_THERMAL_COPPER_AND_STEEL_INGOT = false;

	@Config.Name("Machine Energy Storage")
	@Config.RangeInt(min = 0, max = Integer.MAX_VALUE)
	public int MACHINE_ENERGY_STORAGE = 100000;
}