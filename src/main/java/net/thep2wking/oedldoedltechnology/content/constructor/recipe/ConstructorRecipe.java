package net.thep2wking.oedldoedltechnology.content.constructor.recipe;

import net.minecraft.item.ItemStack;

public class ConstructorRecipe {
	private final ItemStack input1;
	private final ItemStack output1;
	private final int time;

	public ConstructorRecipe(ItemStack input, ItemStack output1, int time) {
		this.input1 = input;
		this.output1 = output1;
		this.time = time;
	}

	public ItemStack getInput1() {
		return input1;
	}


	public ItemStack getOutput1() {
		return output1;
	}

	public int getTime() {
		return time * 20;
	}

	public boolean matches(ItemStack item1) {
		if (item1 != null) {
			return ItemStack.areItemsEqual(item1, this.input1);
		} else {
			return false;
		}
	}
}