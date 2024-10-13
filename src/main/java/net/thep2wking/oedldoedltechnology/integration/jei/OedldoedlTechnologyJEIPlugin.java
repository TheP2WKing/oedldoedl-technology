package net.thep2wking.oedldoedltechnology.integration.jei;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedlcore.api.integration.ModJEIPluginBase;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.content.assembler.GuiAssembler;
import net.thep2wking.oedldoedltechnology.content.constructor.GuiConstructor;
import net.thep2wking.oedldoedltechnology.init.ModBlocks;
import net.thep2wking.oedldoedltechnology.integration.jei.assembler.AssemblerRecipeCategory;
import net.thep2wking.oedldoedltechnology.integration.jei.assembler.AssemblerRecipeWrapper;
import net.thep2wking.oedldoedltechnology.integration.jei.constructor.ConstructorRecipeCategory;
import net.thep2wking.oedldoedltechnology.integration.jei.constructor.ConstructorRecipeWrapper;

@JEIPlugin
public class OedldoedlTechnologyJEIPlugin extends ModJEIPluginBase {
	@Override
	public String getModId() {
		return OedldoedlTechnology.MODID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		addRecipeCategory(registry, new ConstructorRecipeCategory(getGuiHelper(registry)));
		addRecipeCategory(registry, new AssemblerRecipeCategory(getGuiHelper(registry)));
	}


	@Override
	public void register(IModRegistry registry) {
		super.register(registry);

		addRecipeCatalyst(registry, new ItemStack(ModBlocks.CONSTRUCTOR, 1, 0), ConstructorRecipeCategory.UID);
		addRecipes(registry, ConstructorRecipeWrapper.getRecipes(), ConstructorRecipeCategory.UID);
		registry.addRecipeClickArea(GuiConstructor.class, 78, 34, 20, 5, ConstructorRecipeCategory.UID);

		addRecipeCatalyst(registry, new ItemStack(ModBlocks.ASSEMBLER, 1, 0), AssemblerRecipeCategory.UID);
		addRecipes(registry, AssemblerRecipeWrapper.getRecipes(), AssemblerRecipeCategory.UID);
		registry.addRecipeClickArea(GuiAssembler.class, 78, 34, 20, 5, AssemblerRecipeCategory.UID);
	}
}