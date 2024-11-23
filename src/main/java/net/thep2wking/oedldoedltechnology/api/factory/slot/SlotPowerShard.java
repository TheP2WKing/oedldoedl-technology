package net.thep2wking.oedldoedltechnology.api.factory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.thep2wking.oedldoedltechnology.content.item.ItemPowerShard;

public class SlotPowerShard extends SlotItemHandler {
	public SlotPowerShard(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
    public boolean isItemValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemPowerShard;
    }

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}