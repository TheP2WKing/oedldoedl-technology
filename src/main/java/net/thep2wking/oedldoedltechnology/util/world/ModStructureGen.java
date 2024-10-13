package net.thep2wking.oedldoedltechnology.util.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;

public class ModStructureGen implements IWorldGenerator {
	private ModStructureGenerator crashSiteGenerator;
	
	public ModStructureGen() {
		if (TechnologyConfig.WORLD.CRASH_SITE_NO_FIRE_EDITION) {
			if (TechnologyConfig.WORLD.CRASH_SITE_LOOT_CHEST) {
				this.crashSiteGenerator = new ModStructureGenerator(OedldoedlTechnology.MODID, "drop_pod_with_chest");
			} else {
				this.crashSiteGenerator = new ModStructureGenerator(OedldoedlTechnology.MODID, "drop_pod_no_chest");
			}
		} else {
			if (TechnologyConfig.WORLD.CRASH_SITE_LOOT_CHEST) {
				this.crashSiteGenerator = new ModStructureGenerator(OedldoedlTechnology.MODID,
						"drop_pod_fire_with_chest");
			} else {
				this.crashSiteGenerator = new ModStructureGenerator(OedldoedlTechnology.MODID,
						"drop_pod_fire_no_chest");
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		int blockX = chunkX * 16 + random.nextInt(16);
		int blockZ = chunkZ * 16 + random.nextInt(16);

		switch (world.provider.getDimension()) {
			case 0:
				// The overworld
				if (TechnologyConfig.WORLD.CRASH_SITE_STRUCTURE_GENERATION) {
					runGenerator(this.crashSiteGenerator, world, blockX, blockZ,
							TechnologyConfig.WORLD.CRASH_SITE_GENERATION_CHANCE, random); // 5 common 250 rare?
				}
				break;
			case -1:
				// The nether
				break;
			case 1:
				// The end
				break;
		}
	}

	private void runGenerator(ModStructureGenerator generator, World world, int blockX, int blockZ, int chance,
			Random random) {
		if ((int) (Math.random() * chance) == 0) {
			generator.generate(world, random,
					new BlockPos(blockX, ModStructureGenerator.getGroundFromAbove(world, blockX, blockZ), blockZ));
		}
	}

	public static void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new ModStructureGen(), 0);
		GameRegistry.registerWorldGenerator(new ModPowerSlugGen(), 0);

	}
}