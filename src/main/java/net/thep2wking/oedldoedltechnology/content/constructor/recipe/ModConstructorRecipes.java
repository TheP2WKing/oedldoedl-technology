package net.thep2wking.oedldoedltechnology.content.constructor.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModConstructorRecipes {
	public static void register() {
		ConstructorRecipeRegistry.registerOreDictRecipe(new ItemStack(ModItems.POWER_SHARD, 1, 0), "powerSlugBlue", 1,
				8);
		ConstructorRecipeRegistry.registerOreDictRecipe(new ItemStack(ModItems.POWER_SHARD, 2, 0), "powerSlugYellow", 4,
				12);
		ConstructorRecipeRegistry.registerOreDictRecipe(new ItemStack(ModItems.POWER_SHARD, 5, 0), "powerSlugPurple", 1,
				24);

		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(Items.IRON_INGOT, 3, 0),
				new ItemStack(ModItems.IRON_PLATE, 2, 0), 6));
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(ModItems.IRON_PLATE, 1, 0),
				new ItemStack(ModItems.IRON_ROD, 1, 0), 4));
		ConstructorRecipeRegistry.registerRecipe(
				new ConstructorRecipe(new ItemStack(ModItems.IRON_ROD, 1, 0), new ItemStack(ModItems.SCREW, 4, 0), 6));
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(ModItems.COPPER_SHEET, 1, 0),
				new ItemStack(ModItems.WIRE, 2, 0), 4));
		ConstructorRecipeRegistry.registerRecipe(
				new ConstructorRecipe(new ItemStack(ModItems.WIRE, 2, 0), new ItemStack(ModItems.CABLE, 1, 0), 2));
		ConstructorRecipeRegistry.registerOreDictRecipe(new ItemStack(ModItems.COPPER_SHEET, 1, 0), "ingotCopper", 2,
				6);
		ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(new ItemStack(ModItems.STEEL_BEAM, 3, 0),
				new ItemStack(ModItems.STEEL_PIPE, 2, 0), 6));
		ConstructorRecipeRegistry.registerOreDictRecipe(new ItemStack(ModItems.STEEL_BEAM, 1, 0), "ingotSteel", 4, 4);
	}
}