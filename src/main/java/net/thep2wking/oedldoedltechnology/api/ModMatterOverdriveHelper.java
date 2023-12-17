package net.thep2wking.oedldoedltechnology.api;

import java.lang.reflect.Field;

import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.init.MatterOverdriveRecipes;
import net.minecraft.item.ItemStack;

public class ModMatterOverdriveHelper {
	private static Field input1 = null;
	private static Field input2 = null;
	private static Field output = null;
	private static Field energy = null;
	private static Field ticks = null;

	private static InscriberRecipe getRecipe(ItemStack mainInput, ItemStack secondInput, ItemStack mainOutput,
			int energyRequired, int ticksRequired) {
		try {
			if (input1 == null || input2 == null || output == null || energy == null || ticks == null) {
				input1 = InscriberRecipe.class.getDeclaredField("main");
				input1.setAccessible(true);
				input2 = InscriberRecipe.class.getDeclaredField("sec");
				input2.setAccessible(true);
				output = InscriberRecipe.class.getDeclaredField("output");
				output.setAccessible(true);
				energy = InscriberRecipe.class.getDeclaredField("energy");
				energy.setAccessible(true);
				ticks = InscriberRecipe.class.getDeclaredField("time");
				ticks.setAccessible(true);
			}
			InscriberRecipe recipe = new InscriberRecipe();
			input1.set(recipe, mainInput);
			input2.set(recipe, secondInput);
			output.set(recipe, mainOutput);
			energy.set(recipe, energyRequired);
			ticks.set(recipe, ticksRequired);
			return recipe;
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void addInscriberRecipe(ItemStack output, ItemStack mainInout, ItemStack secondaryInput, int energy,
			int ticks) {
		MatterOverdriveRecipes.INSCRIBER
				.register((InscriberRecipe) getRecipe(mainInout, secondaryInput, output, energy, ticks));
	}
}