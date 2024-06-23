package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ItemHandlerAutomation implements IItemHandlerModifiable {
	private final IItemHandlerModifiable base;

	public ItemHandlerAutomation(IItemHandlerModifiable base) {
		this.base = base;
	}

	public void setStackInSlot(int slot, ItemStack stack) {
		this.base.setStackInSlot(slot, stack);
	}

	public int getSlots() {
		return this.base.getSlots();
	}

	public ItemStack getStackInSlot(int slot) {
		return this.base.getStackInSlot(slot);
	}

	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return !this.canInsertItem(slot, stack) ? stack : this.base.insertItem(slot, stack, simulate);
	}

	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return !this.canExtractItem(slot, amount) ? ItemStack.EMPTY : this.base.extractItem(slot, amount, simulate);
	}

	public boolean canExtractItem(int slot, int amount) {
		return true;
	}

	public boolean canInsertItem(int slot, ItemStack itemStack) {
		return true;
	}

	public int getSlotLimit(int slot) {
		return 64;
	}
}