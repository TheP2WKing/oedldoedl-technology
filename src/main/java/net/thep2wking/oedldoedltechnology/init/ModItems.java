package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.item.Item;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.util.ModRarities;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.content.item.ItemRailgun;

public class ModItems {
	public static final Item IPHONE_14_PRO_MAX = new ModItemBase(OedldoedlTechnology.MODID, "iphone_14_pro_max", OedldoedlTechnology.TAB, ModRarities.LIGHT_PURPLE, false, 1, 0);

	public static final ItemRailgun RAILGUN = new ItemRailgun(OedldoedlTechnology.MODID, "railgun", OedldoedlTechnology.TAB, 64, 100, 5, 5, 512, 8, 0.1f, 100, 64000, 4096, ModRarities.RED, false);
}