package net.thep2wking.oedldoedltechnology.registry;

import matteroverdrive.MatterOverdrive;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRecipeHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModMatterOverdriveHelper;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRecipes {
	public static void registerOreDict() {
		ModLogger.registeredOreDictLogger(OedldoedlTechnology.MODID);

		ModRecipeHelper.addOreDict("iPhone", ModItems.IPHONE_14_PRO_MAX, 0);

		ModRecipeHelper.addOreDict("isolinearCircuitOedldoedl", ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 0);

		ModRecipeHelper.addOreDict("receiverRailgun", ModItems.RAILGUN_RECEIVER, 0);
		ModRecipeHelper.addOreDict("handleRailgun", ModItems.RAILGUN_HANDLE, 0);
		ModRecipeHelper.addOreDict("teslaCore", ModItems.TESLA_CORE, 0);
	}

	public static void registerRecipes() {
		ModLogger.registeredRecipesLogger(OedldoedlTechnology.MODID);

		registerMatterOverdriveRecipes();
	}

	public static void registerMatterOverdriveRecipes() {
		if (Loader.isModLoaded("matteroverdrive")) {
			ModMatterOverdriveHelper.addInscriberRecipe(new ItemStack(ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 1, 0),
					new ItemStack(MatterOverdrive.ITEMS.isolinear_circuit, 1, 3),
					new ItemStack(net.thep2wking.oedldoedlresources.init.ModItems.OEDLDOEDL_INGOT), 256000, 2400);
		}

		ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun", new ItemStack(ModItems.RAILGUN, 1, 0),
				"ABA", "CDD", "EFG", 'A', "ingotIron", 'B', "isolinearCircuitOedldoedl", 'C', "receiverRailgun", 'D',
				"teslaCore", 'E', new ItemStack(MatterOverdrive.ITEMS.hc_battery, 1, 0), 'F', "handleRailgun", 'G',
				new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, 2));
	}
}