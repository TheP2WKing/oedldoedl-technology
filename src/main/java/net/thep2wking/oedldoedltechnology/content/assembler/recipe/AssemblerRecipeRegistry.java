package net.thep2wking.oedldoedltechnology.content.assembler.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public class AssemblerRecipeRegistry {
	private static final List<AssemblerRecipe> customRecipeList = new ArrayList<>();

	@Nullable
	public static AssemblerRecipe getRecipe(ItemStack input, ItemStack input2) {
		for (AssemblerRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(input, recipe.getInput1())
					&& ItemStack.areItemsEqual(input2, recipe.getInput2())) {
				return recipe;
			}
		}
		return null;
	}

	public static boolean isValidRecipe(ItemStack input1, ItemStack input2) {
		for (AssemblerRecipe recipe : customRecipeList) {
			if (recipe.matches(input1, input2)) {
				return true;
			}
		}
		return false;
	}

	public static void registerRecipe(AssemblerRecipe recipe) {
		customRecipeList.add(recipe);
	}

	public static void removeRecipe(ItemStack output) {
		Iterator<AssemblerRecipe> iterator = customRecipeList.iterator();
		while (iterator.hasNext()) {
			AssemblerRecipe recipe = iterator.next();
			if (ItemStack.areItemsEqual(output, recipe.getOutput1())) {
				iterator.remove();
				break;
			}
		}
	}

	public static boolean isInput1(ItemStack item) {
		for (AssemblerRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(item, recipe.getInput1())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInput2(ItemStack item) {
		for (AssemblerRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(item, recipe.getInput2())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOutput1(ItemStack item) {
		for (AssemblerRecipe recipe : customRecipeList) {
			if (ItemStack.areItemsEqual(item, recipe.getOutput1())) {
				return true;
			}
		}
		return false;
	}

	public static List<AssemblerRecipe> getRecipeList() {
		return customRecipeList;
	}
}