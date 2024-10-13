package net.thep2wking.oedldoedltechnology.api.factory;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.thep2wking.oedldoedltechnology.api.ModTileEntityBase;
import net.thep2wking.oedldoedltechnology.api.factory.handler.DefaultItemHandler;
import net.thep2wking.oedldoedltechnology.api.factory.handler.EnergyStorageModifiable;
import net.thep2wking.oedldoedltechnology.api.factory.handler.ItemHandlerAutomation;
import net.thep2wking.oedldoedltechnology.api.factory.handler.SubItemHandler;
import net.thep2wking.oedldoedltechnology.api.factory.handler.UpgradeItemHandler;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.content.item.ItemPowerShard;

public abstract class TileFactoryBase extends ModTileEntityBase {
	private static final int UPDATE_INTERVAL = 5;

	public final DefaultItemHandler itemHandler = new DefaultItemHandler(this, getNumberOfSlots() + 3) {
		@Override
		public boolean isItemValid(int slot, ItemStack itemStack) {
			return validteInputSlot(slot, itemStack);
		}

		@Override
		public void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
		}
	};

	private final EnergyStorageModifiable energyStorage = new EnergyStorageModifiable(TechnologyConfig.PROPERTIES.MACHINE_ENERGY_STORAGE) {
		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			if (!simulate) {
				markDirty();
			}
			return super.receiveEnergy(maxReceive, simulate);
		}
	};

	public final SubItemHandler inputSlots = new SubItemHandler(itemHandler, 0, calculateInputSlotDistribution());
	public final SubItemHandler outputSlots = new SubItemHandler(itemHandler, calculateInputSlotDistribution(),
			calculateOutputSlotDistribution());
	public final SubItemHandler powerShardSlots = new UpgradeItemHandler(itemHandler,
			calculateOutputSlotDistribution(), calculateUpgradeSlotDistribution());

	private final ItemHandlerAutomation itemHandlerAutomation = new ItemHandlerAutomation(itemHandler) {
		@Override
		public boolean canExtractItem(int slot, int amount) {
			return super.canExtractItem(slot, amount) && outputSlots.isInside(slot);
		}

		@Override
		public boolean canInsertItem(int slot, ItemStack itemStack) {
			return super.canInsertItem(slot, itemStack) && inputSlots.isInside(slot);
		}
	};

	public int getNumberOfSlots() {
		return getNumberOfInputs() + getNumberOfOutputs();
	}

	public abstract int getNumberOfInputs();

	public abstract int getNumberOfOutputs();

	public int calculateInputSlotDistribution() {
		if (getNumberOfInputs() == 1) {
			return 1;
		} else if (getNumberOfInputs() == 2) {
			return 2;
		} else if (getNumberOfInputs() == 3) {
			return 3;
		} else if (getNumberOfInputs() == 4) {
			return 4;
		} else {
			return 1;
		}
	}

	public int calculateOutputSlotDistribution() {
		if (getNumberOfOutputs() == 1) {
			return 1 + calculateInputSlotDistribution();
		} else if (getNumberOfOutputs() == 2) {
			return 2 + calculateInputSlotDistribution();
		} else {
			return 1 + calculateInputSlotDistribution();
		}
	}

	public int calculateUpgradeSlotDistribution() {
		return 3 + calculateOutputSlotDistribution();
	}

	public boolean validteInputSlot(int slot, ItemStack itemStack) {
		if (calculateInputSlotDistribution() == 1) {
			if (slot == 0) {
				return isInput1(itemStack);
			}
		} else if (calculateInputSlotDistribution() == 2) {
			if (slot == 0) {
				return isInput1(itemStack);
			} else if (slot == 1) {
				return isInput2(itemStack);
			}
		} else if (calculateInputSlotDistribution() == 3) {
			if (slot == 0) {
				return isInput1(itemStack);
			} else if (slot == 1) {
				return isInput2(itemStack);
			} else if (slot == 2) {
				return isInput3(itemStack);
			}
		} else if (calculateInputSlotDistribution() == 4) {
			if (slot == 0) {
				return isInput1(itemStack);
			} else if (slot == 1) {
				return isInput2(itemStack);
			} else if (slot == 2) {
				return isInput3(itemStack);
			} else if (slot == 3) {
				return isInput4(itemStack);
			}
		} else if (getNumberOfOutputs() == 1) {
			if (slot == calculateOutputSlotDistribution() + 1) {
				return isPowerShard(itemStack);
			} else if (slot == calculateOutputSlotDistribution() + 2) {
				return isPowerShard(itemStack);
			} else if (slot == calculateOutputSlotDistribution() + 3) {
				return isPowerShard(itemStack);
			}
		} else if (getNumberOfOutputs() == 2) {
			if (slot == calculateOutputSlotDistribution() + 1) {
				return isPowerShard(itemStack);
			} else if (slot == calculateOutputSlotDistribution() + 2) {
				return isPowerShard(itemStack);
			} else if (slot == calculateOutputSlotDistribution() + 3) {
				return isPowerShard(itemStack);
			}
		}
		return true;
	}

	public abstract boolean isInput1(ItemStack itemStack);

	public abstract boolean isInput2(ItemStack itemStack);

	public abstract boolean isInput3(ItemStack itemStack);

	public abstract boolean isInput4(ItemStack itemStack);

	public abstract boolean isOutput1(ItemStack itemStack);

	public abstract boolean isOutput2(ItemStack itemStack);

	public ItemStack currentStack1 = ItemStack.EMPTY;
	public ItemStack currentStack2 = ItemStack.EMPTY;
	public ItemStack currentStack3 = ItemStack.EMPTY;
	public ItemStack currentStack4 = ItemStack.EMPTY;

	public int ticksSinceUpdate;
	public boolean isDirty;

	public float progress;
	public IBlockState cachedState;
	public boolean isDisabledByRedstone;

	public ItemStack getCurrentStack1() {
		return currentStack1;
	}

	public ItemStack getCurrentStack2() {
		return currentStack2;
	}

	public ItemStack getCurrentStack3() {
		return currentStack3;
	}

	public ItemStack getCurrentStack4() {
		return currentStack4;
	}

	public boolean addItemToOutput(ItemStack itemStack) {
		int firstEmptySlot = -1;
		for (int i = 0; i < outputSlots.getSlots(); i++) {
			ItemStack slotStack = outputSlots.getStackInSlot(i);
			if (slotStack.isEmpty()) {
				if (firstEmptySlot == -1) {
					firstEmptySlot = i;
				}
			} else {
				if (slotStack.getCount() + itemStack.getCount() <= slotStack.getMaxStackSize()
						&& slotStack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(slotStack, itemStack)) {
					slotStack.grow(itemStack.getCount());
					return true;
				}
			}
		}
		if (firstEmptySlot != -1) {
			outputSlots.setStackInSlot(firstEmptySlot, itemStack);
			return true;
		}
		return false;
	}

	public int getEffectiveEnergy() {
		return (int) (getEnergyUsage() + (getEnergyUsage() * getEnergyUpgradeMultiplier()));
	}

	public abstract int getEnergyUsage();

	public abstract ItemStack getOutputItemForDisplay();

	@Override
	public void readFromNBTSynced(NBTTagCompound tagCompound, boolean isSync) {
		if (tagCompound.hasKey("EnergyStorage")) {
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, tagCompound.getTag("EnergyStorage"));
		}
		progress = tagCompound.getFloat("Progress");
		currentStack1 = new ItemStack(tagCompound.getCompoundTag("CurrentStack1"));
		currentStack2 = new ItemStack(tagCompound.getCompoundTag("CurrentStack2"));
		currentStack3 = new ItemStack(tagCompound.getCompoundTag("CurrentStack3"));
		currentStack4 = new ItemStack(tagCompound.getCompoundTag("CurrentStack4"));
		if (isSync) {
			powerShardSlots.setStackInSlot(0, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot1")));
			powerShardSlots.setStackInSlot(1, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot2")));
			powerShardSlots.setStackInSlot(2, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot3")));
		} else {
			itemHandler.deserializeNBT(tagCompound.getCompoundTag("ItemHandler"));
		}
		isDisabledByRedstone = tagCompound.getBoolean("IsDisabledByRedstone");
	}

	public void readRestorableFromNBT(NBTTagCompound tagCompound) {
		if (tagCompound.hasKey("EnergyStorage")) {
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, tagCompound.getTag("EnergyStorage"));
		}
		powerShardSlots.setStackInSlot(0, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot1")));
		powerShardSlots.setStackInSlot(1, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot2")));
		powerShardSlots.setStackInSlot(2, new ItemStack(tagCompound.getCompoundTag("PowerShardSlot3")));
	}

	@Override
	public void writeToNBTSynced(NBTTagCompound tagCompound, boolean isSync) {
		NBTBase energyStorageNBT = CapabilityEnergy.ENERGY.writeNBT(energyStorage, null);
		if (energyStorageNBT != null) {
			tagCompound.setTag("EnergyStorage", energyStorageNBT);
		}
		tagCompound.setTag("CurrentStack1", currentStack1.writeToNBT(new NBTTagCompound()));
		tagCompound.setTag("CurrentStack2", currentStack2.writeToNBT(new NBTTagCompound()));
		tagCompound.setTag("CurrentStack3", currentStack3.writeToNBT(new NBTTagCompound()));
		tagCompound.setTag("CurrentStack4", currentStack4.writeToNBT(new NBTTagCompound()));
		tagCompound.setFloat("Progress", progress);
		if (isSync) {
			ItemStack upgradeSpeedStack = powerShardSlots.getStackInSlot(0);
			tagCompound.setTag("PowerShardSlot1", upgradeSpeedStack.writeToNBT(new NBTTagCompound()));
			ItemStack upgradeSpeedStack2 = powerShardSlots.getStackInSlot(1);
			tagCompound.setTag("PowerShardSlot2", upgradeSpeedStack2.writeToNBT(new NBTTagCompound()));
			ItemStack upgradeSpeedStack3 = powerShardSlots.getStackInSlot(2);
			tagCompound.setTag("PowerShardSlot3", upgradeSpeedStack3.writeToNBT(new NBTTagCompound()));
		} else {
			tagCompound.setTag("ItemHandler", itemHandler.serializeNBT());
		}
		tagCompound.setBoolean("IsDisabledByRedstone", isDisabledByRedstone());
	}

	public void writeRestorableToNBT(NBTTagCompound tagCompound) {
		NBTBase energyStorageNBT = CapabilityEnergy.ENERGY.writeNBT(energyStorage, null);
		if (energyStorageNBT != null) {
			tagCompound.setTag("EnergyStorage", energyStorageNBT);
		}
		ItemStack upgradeSpeedStack = powerShardSlots.getStackInSlot(0);
		tagCompound.setTag("PowerShardSlot1", upgradeSpeedStack.writeToNBT(new NBTTagCompound()));
		ItemStack upgradeSpeedStack2 = powerShardSlots.getStackInSlot(1);
		tagCompound.setTag("PowerShardSlot2", upgradeSpeedStack2.writeToNBT(new NBTTagCompound()));
		ItemStack upgradeSpeedStack3 = powerShardSlots.getStackInSlot(2);
		tagCompound.setTag("PowerShardSlot3", upgradeSpeedStack3.writeToNBT(new NBTTagCompound()));
	}

	public float getEnergyPercentage() {
		return (float) getEnergyStored(null) / (float) getMaxEnergyStored();
	}

	public boolean isProcessing() {
		return progress > 0f;
	}

	public float getProgress() {
		return progress;
	}

	public float getShardPercentage() {
		ItemStack stack = powerShardSlots.getStackInSlot(0);
		ItemStack stack1 = powerShardSlots.getStackInSlot(1);
		ItemStack stack2 = powerShardSlots.getStackInSlot(2);
		int count = stack.getCount() + stack1.getCount() + stack2.getCount();
		return (float) (count * TechnologyConfig.CONTENT.FACTORY.POWER_SHARD_SPEED_AND_POWER_INCREASE + 1);
	}

	public int getShardCount() {
		ItemStack stack = powerShardSlots.getStackInSlot(0);
		ItemStack stack1 = powerShardSlots.getStackInSlot(1);
		ItemStack stack2 = powerShardSlots.getStackInSlot(2);
		int count = stack.getCount() + stack1.getCount() + stack2.getCount();
		return count;
	}

	public float getEnergyUpgradeMultiplier() {
		ItemStack stack = powerShardSlots.getStackInSlot(0);
		ItemStack stack1 = powerShardSlots.getStackInSlot(1);
		ItemStack stack2 = powerShardSlots.getStackInSlot(2);
		int count = stack.getCount() + stack1.getCount() + stack2.getCount();
		float energyMultiplier = (float) (count
				* TechnologyConfig.CONTENT.FACTORY.POWER_SHARD_SPEED_AND_POWER_INCREASE);
		return energyMultiplier;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public int getInterval() {
		return UPDATE_INTERVAL;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) itemHandlerAutomation;
		}
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energyStorage;
		}
		return super.getCapability(capability, facing);
	}

	public DefaultItemHandler getItemHandler() {
		return itemHandler;
	}

	public boolean isPowerShard(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem() instanceof ItemPowerShard;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		cachedState = null;
		return oldState.getBlock() != newState.getBlock();
	}

	public boolean isDisabledByRedstone() {
		return isDisabledByRedstone;
	}

	public void setDisabledByRedstone(boolean disabledByRedstone) {
		isDisabledByRedstone = disabledByRedstone;
		isDirty = true;
		ticksSinceUpdate = UPDATE_INTERVAL;
	}

	public int getEnergyStored(@Nullable EnumFacing from) {
		return energyStorage.getEnergyStored();
	}

	public void setEnergyStored(int energy) {
		energyStorage.setEnergyStored(energy);
	}

	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!simulate) {
			isDirty = true;
		}
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	public EnergyStorageModifiable getEnergyStorage() {
		return energyStorage;
	}

	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	public int getMaxEnergyStored(EnumFacing from) {
		return energyStorage.getMaxEnergyStored();
	}

	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}
}