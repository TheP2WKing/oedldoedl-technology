package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.item.Item;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.util.ModRarities;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.content.item.ItemAlienEgg;
import net.thep2wking.oedldoedltechnology.content.item.ItemRailgun;
import net.thep2wking.oedldoedltechnology.content.item.ItemUpNAtomizer;

public class ModItems {
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
}