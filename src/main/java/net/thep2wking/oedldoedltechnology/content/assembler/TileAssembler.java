package net.thep2wking.oedldoedltechnology.content.assembler;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.thep2wking.oedldoedltechnology.api.factory.TileFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.handler.VanillaPacketHandler;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipe;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.AssemblerRecipeRegistry;

public class TileAssembler extends TileFactoryBase {
	public ItemStack validRecipeOutput;
	public ItemStack validInputStack1;
	public ItemStack validInputStack2;

	@Override
	public void update() {
		if (!world.isRemote) {
			ticksSinceUpdate++;
			if (ticksSinceUpdate > getInterval()) {
				if (isDirty) {
					VanillaPacketHandler.sendTileEntityUpdate(this);
					isDirty = false;
				}
				ticksSinceUpdate = 0;
			}
		}
		int effectiveEnergy = getEffectiveEnergy();
		if (!isDisabledByRedstone() && getEnergyStored(null) >= effectiveEnergy) {
			if (currentStack1.isEmpty() && currentStack2.isEmpty()) {
				ItemStack inputStack1 = inputSlots.getStackInSlot(0);
				ItemStack inputStack2 = inputSlots.getStackInSlot(1);
				if (!inputStack1.isEmpty() && !inputStack2.isEmpty()
						&& AssemblerRecipeRegistry.isValidRecipe(inputStack1, inputStack2)) {
					boolean foundSpace = false;
					AssemblerRecipe recipe = AssemblerRecipeRegistry.getRecipe(inputStack1, inputStack2);
					if (recipe != null) {
						int requiredAmount1 = recipe.getInput1().getCount();
						int requiredAmount2 = recipe.getInput2().getCount();
						if (inputStack1.getCount() < requiredAmount1 || inputStack2.getCount() < requiredAmount2) {
							validRecipeOutput = ItemStack.EMPTY;
							isDirty = true;
						} else {
							validRecipeOutput = getRecipeOutput(inputStack1, inputStack2);
							validInputStack1 = getInputItem1(inputStack1);
							validInputStack2 = getInputItem2(inputStack2);
							isDirty = true;
						}
					}
					for (int i = 0; i < outputSlots.getSlots(); i++) {
						ItemStack outputStack = outputSlots.getStackInSlot(i);
						ItemStack recipeOutput = getRecipeOutput(inputStack1, inputStack2);
						if (outputStack.isEmpty() || (outputStack.isItemEqual(recipeOutput)
								&& outputStack.getCount() < outputStack.getMaxStackSize()
								&& outputStack.getMaxStackSize() - outputStack.getCount() >= recipeOutput.getCount())) {
							foundSpace = true;
						}
					}
					if (!foundSpace) {
						return;
					}
					int requiredAmount1 = recipe.getInput1().getCount();
					int requiredAmount2 = recipe.getInput2().getCount();
					if (inputStack1.getCount() >= requiredAmount1 && inputStack2.getCount() >= requiredAmount2) {
						currentStack1 = inputStack1.splitStack(requiredAmount1);
						currentStack2 = inputStack2.splitStack(requiredAmount2);
					}
					if (inputStack1.isEmpty()) {
						inputSlots.setStackInSlot(0, ItemStack.EMPTY);
					}
					if (inputStack2.isEmpty()) {
						inputSlots.setStackInSlot(1, ItemStack.EMPTY);
					}
					extractEnergy(effectiveEnergy, false);
					VanillaPacketHandler.sendTileEntityUpdate(this);
					if (inputStack1.getCount() < requiredAmount1 || inputStack2.getCount() < requiredAmount2) {
						progress = 0f;
						return;
					}
				}
			} else {
				extractEnergy(effectiveEnergy, false);
				if (validInputStack1 != null && validInputStack2 != null) {
					progress += getEffectiveSpeed(validInputStack1, validInputStack2);
				}
				isDirty = true;
				if (progress >= 1) {
					if (!world.isRemote) {
						ItemStack output = work(currentStack1, currentStack2);
						addItemToOutput(output);
					}
					progress = 0f;
					currentStack1 = ItemStack.EMPTY;
					currentStack2 = ItemStack.EMPTY;
				}
			}
		}
	}

	public String getInputCount1() {
		if (validInputStack1 == null || validInputStack1.isEmpty() || validInputStack1 == ItemStack.EMPTY) {
			return "";
		}
		return validInputStack1.getCount() + "x";
	}

	public String getInputCount2() {
		if (validInputStack2 == null || validInputStack2.isEmpty() || validInputStack1 == ItemStack.EMPTY) {
			return "";
		}
		return validInputStack2.getCount() + "x";
	}

	public String getOutputCount() {
		if (validRecipeOutput == null || validRecipeOutput.isEmpty() || validRecipeOutput == ItemStack.EMPTY) {
			return "";
		}
		return validRecipeOutput.getCount() + "x";
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("validRecipeOutput")) {
			validRecipeOutput = new ItemStack(compound.getCompoundTag("validRecipeOutput"));
		} else {
			validRecipeOutput = ItemStack.EMPTY;
		}

		if (compound.hasKey("validInputStack1")) {
			validInputStack1 = new ItemStack(compound.getCompoundTag("validInputStack1"));
		} else {
			validInputStack1 = ItemStack.EMPTY;
		}

		if (compound.hasKey("validInputStack2")) {
			validInputStack2 = new ItemStack(compound.getCompoundTag("validInputStack2"));
		} else {
			validInputStack2 = ItemStack.EMPTY;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (validRecipeOutput != null && !validRecipeOutput.isEmpty()) {
			compound.setTag("validRecipeOutput", validRecipeOutput.writeToNBT(new NBTTagCompound()));
		}
		if (validInputStack1 != null && !validInputStack1.isEmpty()) {
			compound.setTag("validInputStack1", validInputStack1.writeToNBT(new NBTTagCompound()));
		}
		if (validInputStack2 != null && !validInputStack2.isEmpty()) {
			compound.setTag("validInputStack2", validInputStack2.writeToNBT(new NBTTagCompound()));
		}
		return compound;
	}

	@Override
	public int getNumberOfInputs() {
		return 2;
	}

	@Override
	public int getNumberOfOutputs() {
		return 1;
	}

	@Override
	public boolean isInput1(ItemStack itemStack) {
		return AssemblerRecipeRegistry.isInput1(itemStack);
	}

	@Override
	public boolean isInput2(ItemStack itemStack) {
		return AssemblerRecipeRegistry.isInput2(itemStack);
	}

	@Override
	public boolean isInput3(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isInput4(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isOutput1(ItemStack itemStack) {
		return AssemblerRecipeRegistry.isOutput1(itemStack);
	}

	@Override
	public boolean isOutput2(ItemStack itemStack) {
		return false;
	}

	@Override
	public int getEnergyUsage() {
		return 150;
	}

	public ItemStack work(ItemStack itemStack, ItemStack itemStack2) {
		for (AssemblerRecipe recipe : AssemblerRecipeRegistry.getRecipeList()) {
			if (ItemStack.areItemsEqual(itemStack, recipe.getInput1())
					&& ItemStack.areItemsEqual(itemStack2, recipe.getInput2())) {
				return new ItemStack(recipe.getOutput1().getItem(), recipe.getOutput1().getCount());
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getRecipeOutput(ItemStack input1, ItemStack input2) {
		for (AssemblerRecipe recipe : AssemblerRecipeRegistry.getRecipeList()) {
			if (recipe.matches(input1, input2)) {
				return recipe.getOutput1();
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getInputItem1(ItemStack input1) {
		for (AssemblerRecipe recipe : AssemblerRecipeRegistry.getRecipeList()) {
			if (ItemStack.areItemsEqual(input1, recipe.getInput1())) {
				return recipe.getInput1();
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getInputItem2(ItemStack input2) {
		for (AssemblerRecipe recipe : AssemblerRecipeRegistry.getRecipeList()) {
			if (ItemStack.areItemsEqual(input2, recipe.getInput2())) {
				return recipe.getInput2();
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getOutputItemForDisplay() {
		return validRecipeOutput;
	}

	public float getEffectiveSpeed(ItemStack itemStack1, ItemStack itemStack2) {
		if (itemStack1 == null || itemStack2 == null) {
			return 0.005f;
		}
		// we love floating point precision errors
		float speed = (float) (getProcessingSpeed(itemStack1, itemStack2) + getUpgradeSpeed(itemStack1, itemStack2));
		BigDecimal bdSpeed = new BigDecimal(Float.toString(speed));
		bdSpeed = bdSpeed.setScale(6, RoundingMode.HALF_UP);
		return bdSpeed.floatValue();
	}

	public float getUpgradeSpeed(ItemStack itemStack1, ItemStack itemStack2) {
		ItemStack stack = powerShardSlots.getStackInSlot(0);
		ItemStack stack1 = powerShardSlots.getStackInSlot(1);
		ItemStack stack2 = powerShardSlots.getStackInSlot(2);
		int count = stack.getCount() + stack1.getCount() + stack2.getCount();
		double speedIncresePerUpgrade = (getProcessingSpeed(itemStack1, itemStack2)
				* TechnologyConfig.CONTENT.FACTORY.POWER_SHARD_SPEED_AND_POWER_INCREASE);
		float speed = (float) (count * speedIncresePerUpgrade);
		return speed;
	}

	public float getProcessingSpeed(ItemStack itemStack, ItemStack itemStack2) {
		for (AssemblerRecipe recipe : AssemblerRecipeRegistry.getRecipeList()) {
			if (recipe.matches(itemStack, itemStack2)) {
				float speed = 100f / (recipe.getTime() * 100);
				return speed;
			}
		}
		return 0.005f;
	}
}