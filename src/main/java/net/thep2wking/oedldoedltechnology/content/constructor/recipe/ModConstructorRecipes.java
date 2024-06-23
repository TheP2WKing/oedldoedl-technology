package net.thep2wking.oedldoedltechnology.content.constructor.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModConstructorRecipes {
	public static void reggister() {
		// iron plate
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(Items.IRON_INGOT, 3, 0), new ItemStack(ModItems.IRON_PLATE, 2, 0), 6));

		// iron rod
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(Items.IRON_INGOT, 1, 0), new ItemStack(ModItems.IRON_ROD, 1, 0), 4));

		// screw
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(ModItems.IRON_ROD, 1, 0), new ItemStack(ModItems.SCREW, 4, 0), 6));
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(Items.IRON_INGOT, 5, 0), new ItemStack(ModItems.SCREW, 20, 0), 6));
	}
}