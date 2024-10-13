package net.thep2wking.oedldoedltechnology.content.constructor.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ConstructorRecipeRegistry {
	private static final List<ConstructorRecipe> customRecipeList = new ArrayList<>();

	@Nullable
	public static ConstructorRecipe getRecipe(ItemStack input) {
		for (ConstructorRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(input, recipe.getInput1())) {
				return recipe;
			}
		}
		return null;
	}

	public static boolean isValidRecipe(ItemStack input1) {
		for (ConstructorRecipe recipe : customRecipeList) {
			if (recipe.matches(input1)) {
				return true;
			}
		}
		return false;
	}

	public static void registerRecipe(ConstructorRecipe recipe) {
		customRecipeList.add(recipe);
	}

	public static void registerOreDictRecipe(ItemStack output, String inputOreDict, int count, int time) {
		for (ItemStack inputs : OreDictionary.getOres(inputOreDict)) {
			customRecipeList.add(new ConstructorRecipe(new ItemStack(inputs.getItem(), count, inputs.getMetadata()), output, time));
		}
	}

	public static void removeRecipe(ItemStack output) {
		Iterator<ConstructorRecipe> iterator = customRecipeList.iterator();
		while (iterator.hasNext()) {
			ConstructorRecipe recipe = iterator.next();
			if (ItemStack.areItemsEqual(output, recipe.getOutput1())) {
				iterator.remove();
				break;
			}
		}
	}

	public static boolean isInput1(ItemStack item) {
		for (ConstructorRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(item, recipe.getInput1())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOutput1(ItemStack item) {
		for (ConstructorRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(item, recipe.getOutput1())) {
				return true;
			}
		}
		return false;
	}

	public static List<ConstructorRecipe> getRecipeList() {
		return customRecipeList;
	}
}