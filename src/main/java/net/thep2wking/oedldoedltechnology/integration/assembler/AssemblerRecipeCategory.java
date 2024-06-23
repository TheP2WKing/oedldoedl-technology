package net.thep2wking.oedldoedltechnology.integration.assembler;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModBlocks;

public class AssemblerRecipeCategory implements IRecipeCategory<AssemblerRecipeWrapper> {
	public static final String UID = OedldoedlTechnology.MODID + "." + "factory.assembler";
	public static final String TITLE = I18n
			.format("jei." + OedldoedlTechnology.MODID + "." + "factory.assembler.category");
	public static final String MOD = OedldoedlTechnology.NAME;
	public static final ResourceLocation BACKGROUND = new ResourceLocation(OedldoedlTechnology.MODID,
			"textures/gui/jei/factory_2_inputs.png");

	public IDrawable background;
	public IDrawable icon;
	public IDrawableAnimated progress;

	public AssemblerRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.drawableBuilder(BACKGROUND, 0, 0, 176, 95).build();
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.ASSEMBLER, 1, 0));
		progress = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(BACKGROUND, 176, 0, 20, 5), 100,
				IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getModName() {
		return MOD;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		this.progress.draw(minecraft, 78, 35);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AssemblerRecipeWrapper recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		// outputs
		guiItemStacks.init(0, false, 113, 38);
		// inputs
		guiItemStacks.init(1, true, 9, 29);
		guiItemStacks.init(2, true, 9, 48);
		guiItemStacks.set(ingredients);
	}
}