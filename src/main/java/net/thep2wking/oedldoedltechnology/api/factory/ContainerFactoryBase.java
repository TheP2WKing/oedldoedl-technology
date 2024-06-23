package net.thep2wking.oedldoedltechnology.api.factory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;

public class ContainerFactoryBase extends Container {
	public final TileFactoryBase tileEntity;

	private float lastProgress;
	private int lastEnergy;
	private boolean lastDisabledByRedstone;

	public ContainerFactoryBase(InventoryPlayer inventoryPlayer, TileFactoryBase tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (lastProgress != tileEntity.getProgress() || lastEnergy != tileEntity.getEnergyStored(null)
				|| lastDisabledByRedstone != tileEntity.isDisabledByRedstone()) {
			for (IContainerListener listener : listeners) {
				listener.sendWindowProperty(this, 0, (int) (100 * tileEntity.getProgress()));
				listener.sendWindowProperty(this, 1, tileEntity.getEnergyStored(null));
				listener.sendWindowProperty(this, 2, tileEntity.isDisabledByRedstone() ? 1 : 0);
			}
		}
		lastProgress = tileEntity.getProgress();
		lastEnergy = tileEntity.getEnergyStored(null);
		lastDisabledByRedstone = tileEntity.isDisabledByRedstone();
	}

	@Override
	public void updateProgressBar(int var, int val) {
		switch (var) {
			case 0:
				tileEntity.setProgress((float) val / 100f);
				break;
			case 1:
				tileEntity.setEnergyStored(val);
				break;
			case 2:
				tileEntity.setDisabledByRedstone(val == 1);
				break;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return tileEntity.isUseableByPlayer(entityPlayer);
	}
}