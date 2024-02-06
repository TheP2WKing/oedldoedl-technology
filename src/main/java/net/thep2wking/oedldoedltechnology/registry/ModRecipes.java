package net.thep2wking.oedldoedltechnology.registry;

import matteroverdrive.MatterOverdrive;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRecipeHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModMatterOverdriveHelper;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.util.ModModules;

public class ModRecipes {
	public static void registerOreDict() {
		ModLogger.registeredOreDictLogger(OedldoedlTechnology.MODID);

		if (TechnologyConfig.RECIPES.DEFAULT_OREDICT) {
			ModRecipeHelper.addOreDict("iPhone", ModItems.IPHONE_14_PRO_MAX, 0);

			ModRecipeHelper.addOreDict("isolinearCircuitOedldoedl", ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 0);

			ModRecipeHelper.addOreDict("receiverRailgun", ModItems.RAILGUN_RECEIVER, 0);
			ModRecipeHelper.addOreDict("handleRailgun", ModItems.RAILGUN_HANDLE, 0);
			ModRecipeHelper.addOreDict("teslaCore", ModItems.TESLA_CORE, 0);

			if (ModModules.isMatterOverdriveLoaded()) {
				ModRecipeHelper.addOreDict("plateTritanium", MatterOverdrive.ITEMS.tritanium_plate, 0);
			}
		}
	}

	public static void registerRecipes() {
		ModLogger.registeredRecipesLogger(OedldoedlTechnology.MODID);

		if (ModModules.isMatterOverdriveLoaded() && TechnologyConfig.RECIPES.DEFAULT_RECIPES) {
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun", new ItemStack(ModItems.RAILGUN, 1, 0),
					"ABA", "CDD", "EFG", 'A', "ingotTritanium", 'B', "isolinearCircuitOedldoedl", 'C',
					"receiverRailgun", 'D', "teslaCore", 'E', new ItemStack(MatterOverdrive.ITEMS.hc_battery, 1, 0),
					'F', "handleRailgun", 'G', new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, 2));

			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun_handle",
					new ItemStack(ModItems.RAILGUN_HANDLE, 1, 0), "ABA", "CDC", "CEC", 'A', "plateTritanium", 'B',
					"obsidian", 'C', "ingotTritanium", 'D',
					new ItemStack(MatterOverdrive.ITEMS.isolinear_circuit, 1, 2), 'E', "dyeBlack");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun_receiver",
					new ItemStack(ModItems.RAILGUN_RECEIVER, 1, 0), "ABC", "DEF", "AAC", 'A', "ingotTritanium", 'B',
					"dustGlowstone", 'C', "plateTritanium", 'D', "obsidian", 'E',
					new ItemStack(MatterOverdrive.ITEMS.isolinear_circuit, 1, 2), 'F', "dyeBlack");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "tesla_core",
					new ItemStack(ModItems.TESLA_CORE, 1, 0), "ABC", "DED", "CBA", 'A',
					new ItemStack(MatterOverdrive.BLOCKS.industrialGlass, 1, 0), 'B', "ingotTritanium", 'C', "dyeBlack",
					'D', new ItemStack(MatterOverdrive.ITEMS.forceFieldEmitter, 1, 0), 'E',
					new ItemStack(MatterOverdrive.ITEMS.matterContainer, 1, 0));
		}

		if (ModModules.isMatterOverdriveLoaded() && TechnologyConfig.RECIPES.DEFAULT_INSCRIBER_RECIPES) {
			ModMatterOverdriveHelper.addInscriberRecipe(new ItemStack(ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 1, 0),
					new ItemStack(MatterOverdrive.ITEMS.isolinear_circuit, 1, 3),
					new ItemStack(net.thep2wking.oedldoedlresources.init.ModItems.OEDLDOEDL_INGOT), 256000, 2400);
		}
	}
}