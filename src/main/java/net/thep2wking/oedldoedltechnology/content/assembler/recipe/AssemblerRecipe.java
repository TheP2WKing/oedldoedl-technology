package net.thep2wking.oedldoedltechnology.content.assembler.recipe;

import net.minecraft.item.ItemStack;

public class AssemblerRecipe {
	private final ItemStack input1;
	private final ItemStack input2;
	private final ItemStack output1;
	private final int time;

	public AssemblerRecipe(ItemStack input, ItemStack input2, ItemStack output1, int time) {
		this.input1 = input;
		this.input2 = input2;
		this.output1 = output1;
		this.time = time;
	}

	public ItemStack getInput1() {
		return input1;
	}

	public ItemStack getInput2() {
		return input2;
	}

	public ItemStack getOutput1() {
		return output1;
	}

	public int getTime() {
		return time * 20;
	}

	public boolean matches(ItemStack item1, ItemStack item2) {
		if (item1 != null || item2 == null) {
			return ItemStack.areItemsEqual(item1, this.input1) && ItemStack.areItemsEqual(item2, this.input2);
		} else {
			return false;
		}
	}
}