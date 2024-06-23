package net.thep2wking.oedldoedltechnology.content.constructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.thep2wking.oedldoedltechnology.api.factory.TileFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.handler.VanillaPacketHandler;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipe;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ConstructorRecipeRegistry;

public class TileConstructor extends TileFactoryBase {
	public ItemStack validRecipeOutput;
	public ItemStack validInputStack1;

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
				if (!inputStack1.isEmpty() && ConstructorRecipeRegistry.isValidRecipe(inputStack1)) {
					boolean foundSpace = false;
					ConstructorRecipe recipe = ConstructorRecipeRegistry.getRecipe(inputStack1);
					if (recipe != null) {
						int requiredAmount1 = recipe.getInput1().getCount();
						if (inputStack1.getCount() < requiredAmount1) {
							validRecipeOutput = ItemStack.EMPTY;
							isDirty = true;
						} else {
							validRecipeOutput = getRecipeOutput(inputStack1);
							validInputStack1 = getInputItem1(inputStack1);
							isDirty = true;
						}
					}
					for (int i = 0; i < outputSlots.getSlots(); i++) {
						ItemStack outputStack = outputSlots.getStackInSlot(i);
						ItemStack recipeOutput = getRecipeOutput(inputStack1);
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
					if (inputStack1.getCount() >= requiredAmount1) {
						currentStack1 = inputStack1.splitStack(requiredAmount1);
					}
					if (inputStack1.isEmpty()) {
						inputSlots.setStackInSlot(0, ItemStack.EMPTY);
					}
					extractEnergy(effectiveEnergy, false);
					VanillaPacketHandler.sendTileEntityUpdate(this);
					if (inputStack1.getCount() < requiredAmount1) {
						progress = 0f;
						return;
					}
				}
			} else {
				extractEnergy(effectiveEnergy, false);
				if (validInputStack1 != null) {
					progress += getEffectiveSpeed(validInputStack1);
				}
				isDirty = true;
				if (progress >= 1) {
					if (!world.isRemote) {
						ItemStack output = work(currentStack1);
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
		return compound;
	}

	@Override
	public int getNumberOfInputs() {
		return 1;
	}

	@Override
	public int getNumberOfOutputs() {
		return 1;
	}

	@Override
	public boolean isInput1(ItemStack itemStack) {
		return ConstructorRecipeRegistry.isInput1(itemStack);
	}

	@Override
	public boolean isInput2(ItemStack itemStack) {
		return false;
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
		return ConstructorRecipeRegistry.isOutput1(itemStack);
	}

	@Override
	public boolean isOutput2(ItemStack itemStack) {
		return false;
	}

	@Override
	public int getEnergyUsage() {
		return 40;
	}

	public ItemStack work(ItemStack itemStack) {
		for (ConstructorRecipe recipe : ConstructorRecipeRegistry.getRecipeList()) {
			if (ItemStack.areItemsEqual(itemStack, recipe.getInput1())) {
				return new ItemStack(recipe.getOutput1().getItem(), recipe.getOutput1().getCount());
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getRecipeOutput(ItemStack input1) {
		for (ConstructorRecipe recipe : ConstructorRecipeRegistry.getRecipeList()) {
			if (recipe.matches(input1)) {
				return recipe.getOutput1();
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getInputItem1(ItemStack input1) {
		for (ConstructorRecipe recipe : ConstructorRecipeRegistry.getRecipeList()) {
			if (ItemStack.areItemsEqual(input1, recipe.getInput1())) {
				return recipe.getInput1();
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getOutputItemForDisplay() {
		return validRecipeOutput;
	}

	public float getEffectiveSpeed(ItemStack itemStack1) {
		if (itemStack1 == null) {
			return 0.005f;
		}
		// we love floating point precision errors
		float speed = (float) (getProcessingSpeed(itemStack1) + getUpgradeSpeed(itemStack1));
		BigDecimal bdSpeed = new BigDecimal(Float.toString(speed));
		bdSpeed = bdSpeed.setScale(6, RoundingMode.HALF_UP);
		return bdSpeed.floatValue();
	}

	public float getUpgradeSpeed(ItemStack itemStack1) {
		ItemStack stack = powerShardSlots.getStackInSlot(0);
		ItemStack stack1 = powerShardSlots.getStackInSlot(1);
		ItemStack stack2 = powerShardSlots.getStackInSlot(2);
		int count = stack.getCount() + stack1.getCount() + stack2.getCount();
		double speedIncresePerUpgrade = (getProcessingSpeed(itemStack1)
				* TechnologyConfig.CONTENT.FACTORY.POWER_SHARD_SPEED_AND_POWER_INCREASE);
		float speed = (float) (count * speedIncresePerUpgrade);
		return speed;
	}

	public float getProcessingSpeed(ItemStack itemStack) {
		for (ConstructorRecipe recipe : ConstructorRecipeRegistry.getRecipeList()) {
			if (recipe.matches(itemStack)) {
				float speed = 100f / (recipe.getTime() * 100);
				return speed;
			}
		}
		return 0.005f;
	}
}