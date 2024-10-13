package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRegistryHelper;
import net.thep2wking.oedldoedlcore.util.world.ModBiomeUtil;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityAlienEgg;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityClusterExplosion;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityClusterNobelisk;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityNormalNobelisk;

public class ModEntities {
	public static void registerEntities() {
		ModLogger.registeredEntitiesLogger(OedldoedlTechnology.MODID);

		int id = 0;
		ModRegistryHelper.registerEntity(OedldoedlTechnology.MODID, "alien_egg", OedldoedlTechnology.INSTANCE, id++, EntityAlienEgg.class, 64, 10, true);
		ModRegistryHelper.registerEntity(OedldoedlTechnology.MODID, "cluster_explosion", OedldoedlTechnology.INSTANCE, id++, EntityClusterExplosion.class, 64, 10, true);
		
		ModRegistryHelper.registerEntity(OedldoedlTechnology.MODID, "normal_nobelisk", OedldoedlTechnology.INSTANCE, id++, EntityNormalNobelisk.class, 64, 10, true);
		ModRegistryHelper.registerEntity(OedldoedlTechnology.MODID, "cluster_nobelisk", OedldoedlTechnology.INSTANCE, id++, EntityClusterNobelisk.class, 64, 10, true);

		ModRegistryHelper.registerEntityWithSpawnEgg(OedldoedlTechnology.MODID, "republican_space_ranger", OedldoedlTechnology.INSTANCE, id++, EntityRepublicanSpaceRanger.class, 80, 3, false, 0x5d8407, 0x333f00);
	
		if (TechnologyConfig.CONTENT.SPAWN_REPUBLICAN_SPACE_RANGER) {
			ModRegistryHelper.registerEntitySpawn(EntityRepublicanSpaceRanger.class, EnumCreatureType.MONSTER, 25, 1, 2, ModBiomeUtil.getBiomesWithMonsters(EntityZombie.class));
		}
	}
}