package net.thep2wking.oedldoedltechnology.content.assembler.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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

	public static void registerOreDictRecipe(ItemStack output, String inputOreDict1, int count1, String inputOreDict2,
			int count2, int time) {
		List<ItemStack> inputs1 = OreDictionary.getOres(inputOreDict1);
		List<ItemStack> inputs2 = OreDictionary.getOres(inputOreDict2);
		for (ItemStack input1 : inputs1) {
			for (ItemStack input2 : inputs2) {
				customRecipeList.add(new AssemblerRecipe(new ItemStack(input1.getItem(),
						count1, input1.getMetadata()),
						new ItemStack(input2.getItem(), count2, input2.getMetadata()), output,
						time));
			}
		}
	}

	public static void registerOreDictRecipe(ItemStack output, ItemStack input1, String inputOreDict2, int count2,
			int time) {
		for (ItemStack inputs : OreDictionary.getOres(inputOreDict2)) {
			customRecipeList.add(new AssemblerRecipe(input1,
					new ItemStack(inputs.getItem(), count2, inputs.getMetadata()), output, time));
		}
	}

	public static void registerOreDictRecipe(ItemStack output, String inputOreDict1, int count1, ItemStack input2,
			int time) {
		for (ItemStack inputs : OreDictionary.getOres(inputOreDict1)) {
			customRecipeList
					.add(new AssemblerRecipe(new ItemStack(inputs.getItem(), count1, inputs.getMetadata()), input2,
							output, time));
		}
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