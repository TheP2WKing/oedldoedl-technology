package net.thep2wking.oedldoedltechnology.content.constructor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.thep2wking.oedldoedltechnology.api.factory.ContainerFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.TileFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.slot.SlotOutput;
import net.thep2wking.oedldoedltechnology.api.factory.slot.SlotUpgrade;

public class ContainerConstructor extends ContainerFactoryBase {
	public ContainerConstructor(InventoryPlayer inventoryPlayer, TileFactoryBase tileEntity) {
		super(inventoryPlayer, tileEntity);
		ItemStackHandler itemHandler = tileEntity.getItemHandler();

		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 10, 39));
		// addSlotToContainer(new SlotItemHandler(itemHandler, 1, 10, 49));

		addSlotToContainer(new SlotOutput(itemHandler, 1, 114, 39));

		addSlotToContainer(new SlotUpgrade(itemHandler, 2, 80, 103));
		addSlotToContainer(new SlotUpgrade(itemHandler, 3, 105, 103));
		addSlotToContainer(new SlotUpgrade(itemHandler, 4, 130, 103));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 32 + 9 + 11 + 2));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142 + 32 + 9 + 11 + 2));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotNumber) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(slotNumber);
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemStack = slotStack.copy();
			if (slotNumber <= 4) {
				if (!mergeItemStack(slotStack, 5, 41, true)) {
					return ItemStack.EMPTY;
				}
			} else if (tileEntity.getItemHandler().isItemValid(0, slotStack)) {
				if (!mergeItemStack(slotStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			// } else if (tileEntity.getItemHandler().isItemValid(1, slotStack)) {
			// 	if (!mergeItemStack(slotStack, 1, 2, false)) {
			// 		return ItemStack.EMPTY;
			// 	}
			}
			if (slotStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (slotStack.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(entityPlayer, slotStack);
		}
		return itemStack;
	}
}