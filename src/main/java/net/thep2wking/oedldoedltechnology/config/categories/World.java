package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class World {
	@Config.Name("Crash Site Structure")
	public boolean CRASH_SITE_STRUCTURE = true;

	@Config.Name("Crash Site Chance")
	@Config.RangeInt(min = 0, max = 1000)
	public int CRASH_SITE_GENERATION = 150;

	@Config.Name("Crash Site Loot Chest")
	public boolean CRASH_SITE_LOOT_CHEST = true;

	@Config.Name("Crash Site No Fire Edition")
	public boolean CRASH_SITE_NO_FIRE_EDITION = false;

	@Config.Name("Power Slug")
	public boolean POWER_SLUG = true;

	@Config.Name("Power Slug Chance")
	@Config.RangeDouble(min = 0, max = 10)
	public double POWER_SLUG_CHANCE = 1.5;
}