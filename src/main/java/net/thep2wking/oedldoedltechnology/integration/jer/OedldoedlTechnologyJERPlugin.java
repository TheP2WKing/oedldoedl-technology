package net.thep2wking.oedldoedltechnology.integration.jer;

import jeresources.api.conditionals.LightLevel;
import net.thep2wking.oedldoedlcore.api.integration.ModJERPluginBase;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;
import net.thep2wking.oedldoedltechnology.init.ModLootTables;

public class OedldoedlTechnologyJERPlugin extends ModJERPluginBase {
	@Override
	public String getModId() {
		return OedldoedlTechnology.MODID;
	}

	@Override
	public void register() {
		if (TechnologyConfig.INTEGRATION.JER.REPUBLICAN_SPACE_RANGER_MOB_DROPS
				&& TechnologyConfig.CONTENT.SPAWN_REPUBLICAN_SPACE_RANGER) {
			addMob(new EntityRepublicanSpaceRanger(getWorld()), LightLevel.hostile,
					EntityRepublicanSpaceRanger.EXPERIENCE_VALUE, EntityRepublicanSpaceRanger.LOOT_TABLE);
			registerMobRenderHook(EntityRepublicanSpaceRanger.class, ModRenderHooks.REPUBLICAN_SPACE_RANGER);
		}

		if (TechnologyConfig.INTEGRATION.JER.CRASH_SITE_DUNGEON_CHEST
				&& TechnologyConfig.WORLD.CRASH_SITE_STRUCTURE_GENERATION) {
			addDungeonLoot("crash_site", ModLootTables.CRASH_SITE);
		}
	}
}