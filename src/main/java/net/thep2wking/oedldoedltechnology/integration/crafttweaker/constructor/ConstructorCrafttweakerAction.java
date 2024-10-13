package net.thep2wking.oedldoedltechnology.integration.crafttweaker.constructor;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipe;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipeRegistry;

abstract class ConstructorCrafttweakerAction implements IAction {
    final ItemStack output1;
    final ItemStack input1;
    final int time;

    public ConstructorCrafttweakerAction(ItemStack output1, ItemStack input1, int time) {
        this.output1 = output1;
        this.input1 = input1;
        this.time = time;
    }

    public ConstructorCrafttweakerAction(IItemStack output1, IItemStack input1, int time) {
        this(CraftTweakerMC.getItemStack(output1), CraftTweakerMC.getItemStack(input1), time);
    }

    static class Add extends ConstructorCrafttweakerAction {
        Add(IItemStack output1, IItemStack input1, int time) {
            super(output1, input1, time);
        }

        @Override
        public void apply() {
            ConstructorRecipeRegistry.registerRecipe(new ConstructorRecipe(this.input1, this.output1, this.time));
        }

        @Override
        public String describe() {
            return "Adding constructor recipe for " + "<" + output1.getItem().getRegistryName() + ">";
        }
    }

    static class Remove extends ConstructorCrafttweakerAction {
		Remove(IItemStack output1) {
			super(output1, null, 0);
		}

		@Override
		public void apply() {
			ConstructorRecipeRegistry.getRecipeList().removeIf(recipe -> recipe.getOutput1().isItemEqual(this.output1));
		}

		@Override
		public String describe() {
			return "Removing constructor recipe for output " + "<" + output1.getItem().getRegistryName() + ">";
		}
	}

    static class RemoveAll implements IAction {
        @Override
        public void apply() {
            ConstructorRecipeRegistry.getRecipeList().clear();
        }

        @Override
        public String describe() {
            return "Removing all constructor recipes";
        }
    }
}