package net.thep2wking.oedldoedltechnology.registry;

import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRecipeHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRecipes {
	public static void registerOreDict() {
		ModLogger.registeredOreDictLogger(OedldoedlTechnology.MODID);

		ModRecipeHelper.addOreDict("iPhone", ModItems.IPHONE_14_PRO_MAX, 0);
	}

	public static void registerRecipes() {
		ModLogger.registeredRecipesLogger(OedldoedlTechnology.MODID);
	}
}