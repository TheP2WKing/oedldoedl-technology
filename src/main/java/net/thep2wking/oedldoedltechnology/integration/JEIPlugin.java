package net.thep2wking.oedldoedltechnology.integration;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.content.assembler.GuiAssembler;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipe;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipeRegistry;
import net.thep2wking.oedldoedltechnology.content.constructor.GuiConstructor;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipe;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipeRegistry;
import net.thep2wking.oedldoedltechnology.init.ModBlocks;
import net.thep2wking.oedldoedltechnology.integration.assembler.AssemblerRecipeCategory;
import net.thep2wking.oedldoedltechnology.integration.assembler.AssemblerRecipeWrapper;
import net.thep2wking.oedldoedltechnology.integration.constructor.ConstructorRecipeCategory;
import net.thep2wking.oedldoedltechnology.integration.constructor.ConstructorRecipeWrapper;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		if (Loader.isModLoaded("jei")) {
			ModLogger.registeredIntegration("JEI", OedldoedlTechnology.MODID);
		}

		// constructor
		List<ConstructorRecipeWrapper> factoryConstructor = new ArrayList<>();
		for (ConstructorRecipe recipeConstructor : ConstructorRecipeRegistry.getRecipeList()) {
			factoryConstructor
					.add(new ConstructorRecipeWrapper(recipeConstructor.getInput1(), recipeConstructor.getOutput1(), recipeConstructor.getTime()));
			registry.addRecipes(factoryConstructor, ConstructorRecipeCategory.UID);
			factoryConstructor.clear();
		}
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.CONSTRUCTOR, 1, 0), ConstructorRecipeCategory.UID);
		registry.addRecipeClickArea(GuiConstructor.class, 78, 34, 20, 5, ConstructorRecipeCategory.UID);
		
		// assembler
		List<AssemblerRecipeWrapper> factoryAssembler = new ArrayList<>();
		for (AssemblerRecipe recipeAssembler : AssemblerRecipeRegistry.getRecipeList()) {
			factoryAssembler
					.add(new AssemblerRecipeWrapper(recipeAssembler.getInput1(), recipeAssembler.getInput2(), recipeAssembler.getOutput1(),
							recipeAssembler.getTime()));
			registry.addRecipes(factoryAssembler, AssemblerRecipeCategory.UID);
			factoryAssembler.clear();
		}
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.ASSEMBLER, 1, 0), AssemblerRecipeCategory.UID);
		registry.addRecipeClickArea(GuiAssembler.class, 78, 34, 20, 5, AssemblerRecipeCategory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new ConstructorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new AssemblerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}
}