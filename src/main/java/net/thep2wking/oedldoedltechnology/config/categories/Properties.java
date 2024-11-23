package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class Properties {
	@Config.Name("Factory Energy Storage")
	@Config.RangeInt(min = 0, max = 10000000)
	public int FACTORY_ENERGY_STORAGE = 100000;
}