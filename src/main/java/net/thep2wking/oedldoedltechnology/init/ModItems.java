package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.item.Item;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.util.ModRarities;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.content.item.ItemRailgun;
import net.thep2wking.oedldoedltechnology.content.item.ItemUpNAtomizer;

public class ModItems {
	public static final Item IPHONE_14_PRO_MAX = new ModItemBase(OedldoedlTechnology.MODID, "iphone_14_pro_max", OedldoedlTechnology.TAB, ModRarities.LIGHT_PURPLE, false, 1, 0);

	public static final ItemRailgun RAILGUN = new ItemRailgun(OedldoedlTechnology.MODID, "railgun", OedldoedlTechnology.TAB, 64, 100, 5, 512, 8, 0.1f, 100, 64000, 4096, ModRarities.RED, false);
	public static final ItemUpNAtomizer UP_N_ATOMIZER = new ItemUpNAtomizer(OedldoedlTechnology.MODID, "up_n_atomizer", OedldoedlTechnology.TAB, 20, 50, 4, 256, 2, 0.2f, 100, 32000, 2048, ModRarities.RED, false);

}