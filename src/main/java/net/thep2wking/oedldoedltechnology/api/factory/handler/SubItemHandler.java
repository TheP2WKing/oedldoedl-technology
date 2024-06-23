package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class SubItemHandler extends RangedWrapper {
	private final int minSlot;
	private final int maxSlot;

	public SubItemHandler(IItemHandlerModifiable compose, int minSlot, int maxSlotExclusive) {
		super(compose, minSlot, maxSlotExclusive);
		this.minSlot = minSlot;
		this.maxSlot = maxSlotExclusive;
	}

	public boolean isInside(int slot) {
		return slot >= this.minSlot && slot < this.maxSlot;
	}

	public ItemStack decrStackSize(int slot, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slot);
		if (!itemStackInSlot.isEmpty()) {
			if (itemStackInSlot.getCount() <= count) {
				setStackInSlot(slot, ItemStack.EMPTY);
				return itemStackInSlot;
			} else {
				ItemStack itemStackToDecrement = itemStackInSlot.splitStack(count);
				if (itemStackInSlot.getCount() == 0) {
					setStackInSlot(slot, ItemStack.EMPTY);
				}
				return itemStackToDecrement;
			}
		}
		return ItemStack.EMPTY;
	}
}