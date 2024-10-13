package net.thep2wking.oedldoedltechnology.integration.crafttweaker.assembler;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipe;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipeRegistry;

abstract class OedldoedlTechnologyAssemblerAction implements IAction {
	final ItemStack output1;
	final ItemStack input1;
	final ItemStack input2;
	final int time;

	public OedldoedlTechnologyAssemblerAction(ItemStack output1, ItemStack input1, ItemStack input2, int time) {
		this.output1 = output1;
		this.input1 = input1;
		this.input2 = input2;
		this.time = time;
	}

	public OedldoedlTechnologyAssemblerAction(IItemStack output1, IItemStack input1, IItemStack input2, int time) {
		this(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(input1),
				CraftTweakerMC.getItemStack(input2), time);
	}

	static class Add extends OedldoedlTechnologyAssemblerAction {
		Add(IItemStack output1, IItemStack input1, IItemStack input2, int time) {
			super(output1, input1, input2, time);
		}

		@Override
		public void apply() {
			AssemblerRecipeRegistry
					.registerRecipe(new AssemblerRecipe(this.input1, this.input2, this.output1, this.time));
		}

		@Override
		public String describe() {
			return "Adding assembler recipe for " + "<" + output1.getItem().getRegistryName() + ">";
		}
	}

	static class Remove extends OedldoedlTechnologyAssemblerAction {
		Remove(IItemStack output1) {
			super(output1, null, null, 0);
		}

		@Override
		public void apply() {
			AssemblerRecipeRegistry.getRecipeList().removeIf(recipe -> recipe.getOutput1().isItemEqual(this.output1));
		}

		@Override
		public String describe() {
			return "Removing assembler recipe for output " + "<" + output1.getItem().getRegistryName() + ">";
		}
	}

	static class RemoveAll implements IAction {
        @Override
        public void apply() {
            AssemblerRecipeRegistry.getRecipeList().clear();
        }

        @Override
        public String describe() {
            return "Removing all assembler recipes";
        }
    }
}