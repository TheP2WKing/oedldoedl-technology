package net.thep2wking.oedldoedltechnology.integration.jei.constructor;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipe;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipeRegistry;

public class ConstructorRecipeWrapper implements IRecipeWrapper {
	public ItemStack inoutStack1;
	public ItemStack outputStack1;
	public int time;

	public ConstructorRecipeWrapper(ItemStack inoutStack1, ItemStack outputStack1, int time) {
		this.inoutStack1 = inoutStack1;
		this.outputStack1 = outputStack1;
		this.time = time;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> inputList = new ArrayList<>();
		inputList.add(inoutStack1);
		ingredients.setInputs(VanillaTypes.ITEM, inputList);
		ingredients.setOutput(VanillaTypes.ITEM, outputStack1);
	}

	public int getSeconds() {
		return time / 20;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		String time = String.valueOf(getSeconds() + "s");
		int textWidth = minecraft.fontRenderer.getStringWidth(time) / 2;
		int centerX = 176 / 2;
		minecraft.fontRenderer.drawString(time, (centerX - textWidth), 60, 0xda943b);
	}

	public static List<ConstructorRecipeWrapper> getRecipes() {
		List<ConstructorRecipeWrapper> recipes = new ArrayList<>();
		for (ConstructorRecipe recipeConstructor : ConstructorRecipeRegistry.getRecipeList()) {
			recipes.add(new ConstructorRecipeWrapper(recipeConstructor.getInput1(), recipeConstructor.getOutput1(),
					recipeConstructor.getTime()));
		}
		return recipes;
	}
}