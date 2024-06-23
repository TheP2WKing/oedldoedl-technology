package net.thep2wking.oedldoedltechnology.content.assembler.recipe;

import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModAssemblerRecipes {
	public static void reggister() {
		// reinforced iron plate
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.IRON_PLATE, 6, 0), new ItemStack(ModItems.SCREW, 12, 0), new ItemStack(ModItems.REINFORCED_IRON_PLATE, 1, 0), 12));
		AssemblerRecipeRegistry.registerRecipe(new AssemblerRecipe(new ItemStack(ModItems.IRON_PLATE, 18, 0), new ItemStack(ModItems.SCREW, 50, 0), new ItemStack(ModItems.REINFORCED_IRON_PLATE, 3, 0), 16));
	}
}