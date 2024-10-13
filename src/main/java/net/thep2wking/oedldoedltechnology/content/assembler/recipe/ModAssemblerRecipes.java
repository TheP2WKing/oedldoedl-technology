package net.thep2wking.oedldoedltechnology.content.assembler.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModAssemblerRecipes {
	public static void register() {
		// reinforced iron plate
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.IRON_PLATE, 6, 0), new ItemStack(ModItems.SCREW, 12, 0), new ItemStack(ModItems.REINFORCED_IRON_PLATE, 1, 0), 12));
	
		// modular frame
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.REINFORCED_IRON_PLATE, 3, 0), new ItemStack(ModItems.IRON_ROD, 12, 0), new ItemStack(ModItems.MODULAR_FRAME, 2, 0), 60));
	
		// smart plating
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.REINFORCED_IRON_PLATE, 1, 0), new ItemStack(ModItems.ROTOR, 2, 0), new ItemStack(ModItems.SMART_PLATING, 1, 0), 30));
	
		// rotor
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.IRON_ROD, 5, 0), new ItemStack(ModItems.SCREW, 25, 0), new ItemStack(ModItems.ROTOR, 1, 0), 15));
	
		// versatile framework
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.MODULAR_FRAME, 1, 0), new ItemStack(ModItems.STEEL_BEAM, 12, 0), new ItemStack(ModItems.VERSATILE_FRAMEWORK, 2, 0), 24));
		
		// automated wiring
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.STATOR, 1, 0), new ItemStack(ModItems.CABLE, 20, 0), new ItemStack(ModItems.AUTOMATED_WIRING, 1, 0), 24));
		
		// encased industrial beam
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.STEEL_BEAM, 4, 0), new ItemStack(Blocks.CONCRETE, 5, 8), new ItemStack(ModItems.ENCASED_INDUSTRIAL_BEAM, 1, 0), 10));
		
		// motor
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.ROTOR, 2, 0), new ItemStack(ModItems.STATOR, 2, 0), new ItemStack(ModItems.MOTOR, 1, 0), 12));
		
		// motor
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.STEEL_PIPE, 3, 0), new ItemStack(ModItems.WIRE, 8, 0), new ItemStack(ModItems.ROTOR, 1, 0), 12));
	}
}