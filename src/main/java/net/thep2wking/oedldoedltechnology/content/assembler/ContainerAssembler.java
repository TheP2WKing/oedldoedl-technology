package net.thep2wking.oedldoedltechnology.content.assembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.thep2wking.oedldoedltechnology.api.factory.ContainerFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.TileFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.slot.SlotOutput;
import net.thep2wking.oedldoedltechnology.api.factory.slot.SlotPowerShard;
import net.thep2wking.oedldoedltechnology.api.factory.slot.SlotSomersloop;

public class ContainerAssembler extends ContainerFactoryBase {
	public ContainerAssembler(InventoryPlayer inventoryPlayer, TileFactoryBase tileEntity) {
		super(inventoryPlayer, tileEntity);
		ItemStackHandler itemHandler = tileEntity.getItemHandler();

		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 10, 30));
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 10, 49));

		addSlotToContainer(new SlotOutput(itemHandler, 2, 114, 39));

		addSlotToContainer(new SlotPowerShard(itemHandler, 3, 60, 103));
		addSlotToContainer(new SlotPowerShard(itemHandler, 4, 85, 103));
		addSlotToContainer(new SlotPowerShard(itemHandler, 5, 110, 103));

		addSlotToContainer(new SlotSomersloop(itemHandler, 6, 149, 103) {
			@Override
			public int getItemStackLimit(ItemStack stack) {
				return tileEntity.getRequiredSomersloops();
			}
		});

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
			if (slotNumber <= 6) {
				if (!mergeItemStack(slotStack, 7, 43, true)) {
					return ItemStack.EMPTY;
				}
			} else if (tileEntity.getItemHandler().isItemValid(0, slotStack)) {
				if (!mergeItemStack(slotStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (tileEntity.getItemHandler().isItemValid(1, slotStack)) {
				if (!mergeItemStack(slotStack, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
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