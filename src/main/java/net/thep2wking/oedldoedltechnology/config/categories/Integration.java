package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class Integration {
	@Config.Name("jer")
	public final JER JER = new JER();
	
	public static class JER {
		@Config.Name("Republican Space Ranger Mob Drops")
		public boolean REPUBLICAN_SPACE_RANGER_MOB_DROPS = true;
		
		@Config.Name("Crash Site Dungeon Chests")
		public boolean CRASH_SITE_DUNGEON_CHEST = true;

		@Config.Name("Power Slug World Gen")
		public boolean POWER_SLUG_WORLD_GEN = true;
	}
	
	@Config.Name("jei")
	public final JEI JEI = new JEI();

	public static class JEI {
		@Config.Name("Factory Recipe Category")
		public boolean FACTORY_RECIPE_CATEGORY = true;
	}

	@Config.Name("theoneprobe")
	public final TheOneProbe THEONEPROBE = new TheOneProbe();

	public static class TheOneProbe {
		@Config.Name("Factory Information")
		public boolean FACTORY_INFORMATION = true;

		@Config.Name("Factory Information Expanded By Default")
		public boolean FACTORY_INFORMATION_EXPANDED_BY_DEFAULT = false;
	}
}