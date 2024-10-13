package net.thep2wking.oedldoedltechnology.registry;

import matteroverdrive.MatterOverdrive;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRecipeHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModMatterOverdriveHelper;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRecipes {
	public static void registerOreDict() {
		ModLogger.registeredOreDictLogger(OedldoedlTechnology.MODID);

		if (TechnologyConfig.RECIPES.DEFAULT_OREDICT) {
			ModRecipeHelper.addOreDict("isolinearCircuitOedldoedl", ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 0);

			ModRecipeHelper.addOreDict("receiverRailgun", ModItems.RAILGUN_RECEIVER, 0);
			ModRecipeHelper.addOreDict("handleRailgun", ModItems.RAILGUN_HANDLE, 0);
			ModRecipeHelper.addOreDict("teslaCore", ModItems.TESLA_CORE, 0);

			ModRecipeHelper.addOreDict("receiverUpNAtomizer", ModItems.UP_N_ATOMIZER_RECEIVER, 0);
			ModRecipeHelper.addOreDict("handleUpNAtomizer", ModItems.UP_N_ATOMIZER_HANDLE, 0);
			ModRecipeHelper.addOreDict("shockwaveEmitter", ModItems.SOCKWAVE_EMITTER, 0);

			ModRecipeHelper.addOreDict("eggAlien", ModItems.ALIEN_EGG, 0);

			ModRecipeHelper.addOreDict("iPhone", ModItems.IPHONE_14_PRO_MAX, 0);

			ModRecipeHelper.addOreDict("plateTritanium", MatterOverdrive.ITEMS.tritanium_plate, 0);

			ModRecipeHelper.addOreDict("isolinearCircuitMk1", MatterOverdrive.ITEMS.isolinear_circuit, 0);
			ModRecipeHelper.addOreDict("isolinearCircuitMk2", MatterOverdrive.ITEMS.isolinear_circuit, 1);
			ModRecipeHelper.addOreDict("isolinearCircuitMk3", MatterOverdrive.ITEMS.isolinear_circuit, 2);
			ModRecipeHelper.addOreDict("isolinearCircuitMk4", MatterOverdrive.ITEMS.isolinear_circuit, 3);

			ModRecipeHelper.addOreDict("glassIndustrial", MatterOverdrive.BLOCKS.industrialGlass, 0);

			ModRecipeHelper.addOreDict("superconductorMagnet", MatterOverdrive.ITEMS.s_magnet, 0);
			ModRecipeHelper.addOreDict("forcefieldEmitter", MatterOverdrive.ITEMS.forceFieldEmitter, 0);
			ModRecipeHelper.addOreDict("matterContainer", MatterOverdrive.ITEMS.matterContainer, 0);

			ModRecipeHelper.addOreDict("powerShard", ModItems.POWER_SHARD, 0);
			
			ModRecipeHelper.addOreDict("ingotSteel", ModItems.ALIEN_EGG, 0);

			addProjectPartOreDict("ironPlate", ModItems.IRON_PLATE);
			addProjectPartOreDict("ironRod", ModItems.IRON_ROD);
			addProjectPartOreDict("screw", ModItems.SCREW);
			addProjectPartOreDict("reinforcedIronPlate", ModItems.REINFORCED_IRON_PLATE);
			addProjectPartOreDict("wire", ModItems.WIRE);
			addProjectPartOreDict("cable", ModItems.CABLE);
			addProjectPartOreDict("copperSheet", ModItems.COPPER_SHEET);
			addProjectPartOreDict("modularFrame", ModItems.MODULAR_FRAME);
			addProjectPartOreDict("rotor", ModItems.ROTOR);
			addProjectPartOreDict("smartPlating", ModItems.SMART_PLATING);
			addProjectPartOreDict("steelBeam", ModItems.STEEL_BEAM);
			addProjectPartOreDict("steelPipe", ModItems.STEEL_PIPE);
			addProjectPartOreDict("versatileFramework", ModItems.VERSATILE_FRAMEWORK);
			addProjectPartOreDict("automatedWiring", ModItems.AUTOMATED_WIRING);
			addProjectPartOreDict("encasedIndustrialBeam", ModItems.ENCASED_INDUSTRIAL_BEAM);
			addProjectPartOreDict("heavyModularFrame", ModItems.HEAVY_MODULAR_FRAME);
			addProjectPartOreDict("motor", ModItems.MOTOR);
			addProjectPartOreDict("stator", ModItems.STATOR);
		}
	}

	public static void addProjectPartOreDict(String name, Item item) {
		ModRecipeHelper.addOreDict("projectPart", item, 0);
		ModRecipeHelper.addOreDict("projectPart" + Character.toUpperCase(name.charAt(0)) + name.substring(1), item, 0);
	}

	public static void registerRecipes() {
		ModLogger.registeredRecipesLogger(OedldoedlTechnology.MODID);

		if (TechnologyConfig.RECIPES.DEFAULT_RECIPES) {
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "industrial_glass",
					new ItemStack(MatterOverdrive.BLOCKS.industrialGlass, 4, 0), " A ", "ABA", " A ", 'A', "blockGlass",
					'B', "plateTritanium");

			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun", new ItemStack(ModItems.RAILGUN, 1, 0),
					"ABA", "CDD", "EFG", 'A', "ingotTritanium", 'B', "isolinearCircuitOedldoedl", 'C',
					"receiverRailgun", 'D', "teslaCore", 'E', new ItemStack(MatterOverdrive.ITEMS.hc_battery, 1, 0),
					'F', "handleRailgun", 'G', new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, 2));
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "up_n_atomizer",
					new ItemStack(ModItems.UP_N_ATOMIZER, 1, 0), "ABA", "CDD", "EFG", 'A', "ingotTritanium", 'B',
					"isolinearCircuitOedldoedl", 'C', "receiverUpNAtomizer", 'D', "shockwaveEmitter", 'E',
					new ItemStack(MatterOverdrive.ITEMS.hc_battery, 1, 0), 'F', "handleUpNAtomizer", 'G', "eggAlien");

			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun_handle",
					new ItemStack(ModItems.RAILGUN_HANDLE, 1, 0), "ABA", "CDC", "CEC", 'A', "plateTritanium", 'B',
					"obsidian", 'C', "ingotTritanium", 'D', "isolinearCircuitMk3", 'E', "dyeBlack");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "railgun_receiver",
					new ItemStack(ModItems.RAILGUN_RECEIVER, 1, 0), "ABC", "DEF", "AAC", 'A', "ingotTritanium", 'B',
					"dustGlowstone", 'C', "plateTritanium", 'D', "obsidian", 'E', "isolinearCircuitMk3", 'F',
					"dyeBlack");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "tesla_core",
					new ItemStack(ModItems.TESLA_CORE, 1, 0), "ABC", "DED", "CBA", 'A', "glassIndustrial", 'B',
					"ingotTritanium", 'C', "dyeBlack", 'D', "forcefieldEmitter", 'E', "matterContainer");

			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "up_n_atomizer_handle",
					new ItemStack(ModItems.UP_N_ATOMIZER_HANDLE, 1, 0), "ABC", "ADE", "AFC", 'A', "ingotTritanium", 'B',
					"obsidian", 'C', "plateTritanium", 'D', "isolinearCircuitMk3", 'E',
					new ItemStack(Blocks.STONE_BUTTON, 1, 0), 'F', "dyeBlack");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "up_n_atomizer_receiver",
					new ItemStack(ModItems.UP_N_ATOMIZER_RECEIVER, 1, 0), "ABC", "DEF", "AAC", 'A', "ingotTritanium",
					'B', "gemDilithium", 'C', "plateTritanium", 'D', "obsidian", 'E', "isolinearCircuitMk3", 'F',
					"dyeBlue");
			ModRecipeHelper.addShapedRecipe(OedldoedlTechnology.MODID, "shickwave_emitter",
					new ItemStack(ModItems.SOCKWAVE_EMITTER, 1, 0), "ABA", "CDC", "ABA", 'A', "dustGlowstone", 'B',
					"glowstone", 'C', "superconductorMagnet", 'D', "forcefieldEmitter");
		}

		if (TechnologyConfig.RECIPES.DEFAULT_INSCRIBER_RECIPES) {
			ModMatterOverdriveHelper.addInscriberRecipe(new ItemStack(ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT, 1, 0),
					new ItemStack(MatterOverdrive.ITEMS.isolinear_circuit, 1, 3),
					new ItemStack(net.thep2wking.oedldoedlresources.init.ModItems.OEDLDOEDL_INGOT), 256000, 2400);
		}
	}
}