package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class World {
	@Config.Name("Crash Site Structure Generation")
	public boolean CRASH_SITE_STRUCTURE_GENERATION = true;

	@Config.Name("Crash Site Generation Chance")
	@Config.RangeInt(min = 0, max = 1000)
	public int CRASH_SITE_GENERATION_CHANCE = 150;

	@Config.Name("Crash Site Loot Chest")
	public boolean CRASH_SITE_LOOT_CHEST = true;

	@Config.Name("Crash Site No Fire Edition")
	public boolean CRASH_SITE_NO_FIRE_EDITION = false;
}