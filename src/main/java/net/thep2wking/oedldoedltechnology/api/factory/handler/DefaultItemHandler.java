package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class DefaultItemHandler extends ItemStackHandler {
	public final TileEntity tileEntity;
 
	public DefaultItemHandler(TileEntity tileEntity, int size) {
	   super(size);
	   this.tileEntity = tileEntity;
	}
 
	protected void onContentsChanged(int slot) {
	   this.tileEntity.markDirty();
	}
 
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
	   return !this.isItemValid(slot, stack) ? stack : super.insertItem(slot, stack, simulate);
	}
 
	public boolean isItemValid(int slot, ItemStack itemStack) {
	   return true;
	}
 
	public void deserializeNBT(NBTTagCompound nbt) {
	   nbt.setInteger("Size", this.getSlots());
	   super.deserializeNBT(nbt);
	}
 }
 