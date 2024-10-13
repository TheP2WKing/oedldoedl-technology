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
	}
}