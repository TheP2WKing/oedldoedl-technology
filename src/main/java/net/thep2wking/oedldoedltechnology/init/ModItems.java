package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.item.Item;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.api.item.ModItemBlockBase;
import net.thep2wking.oedldoedlcore.util.ModRarities;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.projectpart.ModItemBlockProjectPartBase;
import net.thep2wking.oedldoedltechnology.api.projectpart.ModItemProjectPartBase;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.content.item.ItemAlienEgg;
import net.thep2wking.oedldoedltechnology.content.item.ItemClusterNobelisk;
import net.thep2wking.oedldoedltechnology.content.item.ItemNobeliskDetonator;
import net.thep2wking.oedldoedltechnology.content.item.ItemNormalNobelisk;
import net.thep2wking.oedldoedltechnology.content.item.ItemPowerShard;
import net.thep2wking.oedldoedltechnology.content.item.ItemRailgun;
import net.thep2wking.oedldoedltechnology.content.item.ItemUpNAtomizer;
import net.thep2wking.oedldoedltechnology.util.ModPhaseTier;

public class ModItems {
	public static final Item CONSTRUCTOR = new ModItemBlockBase(ModBlocks.CONSTRUCTOR, ModRarities.GOLD, false, 1, 0);
	public static final Item ASSEMBLER = new ModItemBlockBase(ModBlocks.ASSEMBLER, ModRarities.GOLD, false, 1, 0);

	public static final Item BLUE_POWER_SLUG = new ModItemBlockBase(ModBlocks.BLUE_POWER_SLUG, ModRarities.AQUA, false, 1, 0);
	public static final Item YELLOW_POWER_SLUG = new ModItemBlockBase(ModBlocks.YELLOW_POWER_SLUG, ModRarities.YELLOW, false, 1, 0);
	public static final Item PURPLE_POWER_SLUG = new ModItemBlockBase(ModBlocks.PURPLE_POWER_SLUG, ModRarities.LIGHT_PURPLE, false, 1, 0);

	public static final ItemRailgun RAILGUN = new ItemRailgun(OedldoedlTechnology.MODID, "railgun", OedldoedlTechnology.TAB, TechnologyConfig.CONTENT.RAILGUN.RANGE, TechnologyConfig.CONTENT.RAILGUN.COOLDOWN, TechnologyConfig.CONTENT.RAILGUN.DAMAGE, TechnologyConfig.CONTENT.RAILGUN.MAX_USE_TIME, TechnologyConfig.CONTENT.RAILGUN.SHOT_SPEED, (float) TechnologyConfig.CONTENT.RAILGUN.ZOOM, TechnologyConfig.CONTENT.RAILGUN.MAX_HEAT, TechnologyConfig.CONTENT.RAILGUN.MAX_ENERGY, TechnologyConfig.CONTENT.RAILGUN.ENERGY_PER_SHOT, ModRarities.RED, false, 2, 1);
	public static final ItemUpNAtomizer UP_N_ATOMIZER = new ItemUpNAtomizer(OedldoedlTechnology.MODID, "up_n_atomizer", OedldoedlTechnology.TAB, TechnologyConfig.CONTENT.UP_N_ATOMIZER.RANGE, TechnologyConfig.CONTENT.UP_N_ATOMIZER.COOLDOWN, TechnologyConfig.CONTENT.UP_N_ATOMIZER.DAMAGE, TechnologyConfig.CONTENT.UP_N_ATOMIZER.MAX_USE_TIME, TechnologyConfig.CONTENT.UP_N_ATOMIZER.SHOT_SPEED, (float) TechnologyConfig.CONTENT.UP_N_ATOMIZER.ZOOM, TechnologyConfig.CONTENT.UP_N_ATOMIZER.MAX_HEAT, TechnologyConfig.CONTENT.UP_N_ATOMIZER.MAX_ENERGY, TechnologyConfig.CONTENT.UP_N_ATOMIZER.ENERGY_PER_SHOT, ModRarities.BLUE, false, 3, 1);
	
	public static final Item OEDLDOEDL_ISOLINEAR_CIRCUIT = new ModItemBase(OedldoedlTechnology.MODID, "oedldoedl_isolinear_circuit", OedldoedlTechnology.TAB, ModRarities.LIGHT_PURPLE, true, 2, 0);
	
	public static final Item RAILGUN_HANDLE = new ModItemBase(OedldoedlTechnology.MODID, "railgun_handle", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);
	public static final Item RAILGUN_RECEIVER = new ModItemBase(OedldoedlTechnology.MODID, "railgun_receiver", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);
	public static final Item TESLA_CORE = new ModItemBase(OedldoedlTechnology.MODID, "tesla_core", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);
	
	public static final Item UP_N_ATOMIZER_HANDLE = new ModItemBase(OedldoedlTechnology.MODID, "up_n_atomizer_handle", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);
	public static final Item UP_N_ATOMIZER_RECEIVER = new ModItemBase(OedldoedlTechnology.MODID, "up_n_atomizer_receiver", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);
	public static final Item SOCKWAVE_EMITTER = new ModItemBase(OedldoedlTechnology.MODID, "shockwave_emitter", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 1, 0);

	public static final Item ALIEN_EGG = new ItemAlienEgg(OedldoedlTechnology.MODID, "alien_egg", OedldoedlTechnology.TAB, ModRarities.GREEN, true, 1, 0);

	public static final Item IPHONE_14_PRO_MAX = new ModItemBase(OedldoedlTechnology.MODID, "iphone_14_pro_max", OedldoedlTechnology.TAB, ModRarities.LIGHT_PURPLE, false, 1, 0);

	public static final Item POWER_SHARD = new ItemPowerShard(OedldoedlTechnology.MODID, "power_shard", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 3, 1);

	public static final Item IRON_PLATE = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "iron_plate", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);
	public static final Item IRON_ROD = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "iron_rod", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);
	public static final Item SCREW = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "screw", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);
	public static final Item REINFORCED_IRON_PLATE = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "reinforced_iron_plate", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);

	public static final Item WIRE = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "wire", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);
	public static final Item CABLE = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "cable", OedldoedlTechnology.TAB, ModPhaseTier.TIER_0, false, 1);
	public static final Item COPPER_SHEET = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "copper_sheet", OedldoedlTechnology.TAB, ModPhaseTier.TIER_2, false, 2);
	public static final Item MODULAR_FRAME = new ModItemBlockProjectPartBase(ModBlocks.MODULAR_FRAME, ModPhaseTier.TIER_2, false, 1);
	public static final Item ROTOR = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "rotor", OedldoedlTechnology.TAB, ModPhaseTier.TIER_2, false, 1);
	public static final Item SMART_PLATING = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "smart_plating", OedldoedlTechnology.TAB, ModPhaseTier.TIER_2, false, 2);

	public static final Item STEEL_BEAM = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "steel_beam", OedldoedlTechnology.TAB, ModPhaseTier.TIER_3, false, 2);
	public static final Item STEEL_PIPE = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "steel_pipe", OedldoedlTechnology.TAB, ModPhaseTier.TIER_3, false, 2);
	public static final Item VERSATILE_FRAMEWORK = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "versatile_framework", OedldoedlTechnology.TAB, ModPhaseTier.TIER_3, false, 2);

	public static final Item AUTOMATED_WIRING = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "automated_wiring", OedldoedlTechnology.TAB, ModPhaseTier.TIER_4, false, 2);
	public static final Item ENCASED_INDUSTRIAL_BEAM = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "encased_industrial_beam", OedldoedlTechnology.TAB, ModPhaseTier.TIER_4, false, 2);
	public static final Item HEAVY_MODULAR_FRAME = new ModItemBlockProjectPartBase(ModBlocks.HEAVY_MODULAR_FRAME, ModPhaseTier.TIER_4, false, 1);
	public static final Item MOTOR = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "motor", OedldoedlTechnology.TAB, ModPhaseTier.TIER_4, false, 2);
	public static final Item STATOR = new ModItemProjectPartBase(OedldoedlTechnology.MODID, "stator", OedldoedlTechnology.TAB, ModPhaseTier.TIER_4, false, 2);

	public static final Item NOBELISK_DETONATOR = new ItemNobeliskDetonator(OedldoedlTechnology.MODID, "nobelisk_detonator", OedldoedlTechnology.TAB, ModRarities.YELLOW, false, 2, 0);
	public static final Item NORMAL_NOBELISK = new ItemNormalNobelisk(OedldoedlTechnology.MODID, "nobelisk", OedldoedlTechnology.TAB, ModRarities.RED, false, 2, 0);
	public static final Item CLUSTER_NOBELISK = new ItemClusterNobelisk(OedldoedlTechnology.MODID, "cluster_nobelisk", OedldoedlTechnology.TAB, ModRarities.GOLD, false, 3, 0);
}