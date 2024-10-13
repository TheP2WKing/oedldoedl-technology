package net.thep2wking.oedldoedltechnology.api.factory;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.thep2wking.oedldoedlcore.integration.top.ITOPInfoProvider;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModBlockContainerBase;

public abstract class BlockFactoryBase extends ModBlockContainerBase implements ITOPInfoProvider {
	public BlockFactoryBase(String modid, String name, CreativeTabs tab, Material material, SoundType sound,
			MapColor mapColor, int harvestLevel, ModToolTypes toolType, float hardness, float resistance,
			int lightLevel) {
		super(modid, name, tab, material, sound, mapColor, harvestLevel, toolType, hardness, resistance, lightLevel);
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			IItemHandler itemHandler = ((TileFactoryBase) tileEntity).getItemHandler();
			for (int i = 0; i < (((TileFactoryBase) tileEntity).getNumberOfSlots()); i++) {
				ItemStack itemStack = itemHandler.getStackInSlot(i);
				if (!itemStack.isEmpty()) {
					EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
					double motion = 0.05;
					entityItem.motionX = world.rand.nextGaussian() * motion;
					entityItem.motionY = 0.2;
					entityItem.motionZ = world.rand.nextGaussian() * motion;
					world.spawnEntity(entityItem);
				}
			}
			if (((TileFactoryBase) tileEntity).getNumberOfInputs() == 1) {
				ItemStack currentStack1 = ((TileFactoryBase) tileEntity).getCurrentStack1();
				spawnInputItem(currentStack1, world, pos);
			} else if (((TileFactoryBase) tileEntity).getNumberOfInputs() == 2) {
				ItemStack currentStack1 = ((TileFactoryBase) tileEntity).getCurrentStack1();
				spawnInputItem(currentStack1, world, pos);
				ItemStack currentStack2 = ((TileFactoryBase) tileEntity).getCurrentStack2();
				spawnInputItem(currentStack2, world, pos);
			} else if (((TileFactoryBase) tileEntity).getNumberOfInputs() == 3) {
				ItemStack currentStack1 = ((TileFactoryBase) tileEntity).getCurrentStack1();
				spawnInputItem(currentStack1, world, pos);
				ItemStack currentStack2 = ((TileFactoryBase) tileEntity).getCurrentStack2();
				spawnInputItem(currentStack2, world, pos);
				ItemStack currentStack3 = ((TileFactoryBase) tileEntity).getCurrentStack3();
				spawnInputItem(currentStack3, world, pos);
			} else if (((TileFactoryBase) tileEntity).getNumberOfInputs() == 4) {
				ItemStack currentStack1 = ((TileFactoryBase) tileEntity).getCurrentStack1();
				spawnInputItem(currentStack1, world, pos);
				ItemStack currentStack2 = ((TileFactoryBase) tileEntity).getCurrentStack2();
				spawnInputItem(currentStack2, world, pos);
				ItemStack currentStack3 = ((TileFactoryBase) tileEntity).getCurrentStack3();
				spawnInputItem(currentStack3, world, pos);
				ItemStack currentStack4 = ((TileFactoryBase) tileEntity).getCurrentStack4();
				spawnInputItem(currentStack4, world, pos);
			}
		}
		super.breakBlock(world, pos, state);
	}

	public void spawnInputItem(ItemStack stack, World world, BlockPos pos) {
		if (!stack.isEmpty()) {
			EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			double motion = 0.05;
			entityItem.motionX = world.rand.nextGaussian() * motion;
			entityItem.motionY = 0.2;
			entityItem.motionZ = world.rand.nextGaussian() * motion;
			world.spawnEntity(entityItem);
		}
	}

	public abstract TileEntity getTileEntity();

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return getTileEntity();
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		updateRedstoneState(world, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		updateRedstoneState(world, pos);
	}

	private void updateRedstoneState(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileFactoryBase) {
			((TileFactoryBase) tileEntity).setDisabledByRedstone(world.isBlockPowered(pos));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		TileFactoryBase tileEntity = (TileFactoryBase) world.getTileEntity(pos);
		if (tileEntity instanceof TileFactoryBase) {
			NBTTagCompound tagCompound = stack.getTagCompound();
			if (tagCompound != null) {
				if (tagCompound.hasKey("EnergyStored")) {
					tileEntity.getEnergyStorage().setEnergyStored(tagCompound.getInteger("EnergyStored"));
				}
				tileEntity.readRestorableFromNBT(tagCompound);
			}
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> result, IBlockAccess world, BlockPos pos, IBlockState metadata,
			int fortune) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileFactoryBase) {
			ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
			NBTTagCompound tagCompound = new NBTTagCompound();
			((TileFactoryBase) tileEntity).writeRestorableToNBT(tagCompound);
			stack.setTagCompound(tagCompound);
			result.add(stack);
		} else {
			super.getDrops(result, world, pos, metadata, fortune);
		}
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {
		TileFactoryBase tile = (TileFactoryBase) world.getTileEntity(data.getPos());
		if (tile == null)
			return;

		if (tile.isDisabledByRedstone()) {
			probeInfo.text(TextFormatting.RED + "{*top." + OedldoedlTechnology.MODID + ".disabled*}");
			return;
		}

		if (!tile.isDisabledByRedstone()) {
			probeInfo.progress(Math.round(tile.getProgress() * 100), 100,
					probeInfo.defaultProgressStyle()
							.suffix("%")
							.filledColor(0xffda943b)
							.alternateFilledColor(0xffda943b)); // 0xffba7026

			if (player.isSneaking()) {
				probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffda943b).spacing(2))
						.text(TextFormatting.WHITE + "{*top." + OedldoedlTechnology.MODID + ".energy*}" + " "
								+ TextFormatting.RED + tile.getEffectiveEnergy() + " FE")
						.text(TextFormatting.WHITE + "{*top." + OedldoedlTechnology.MODID + ".speed*}" + " "
								+ TextFormatting.YELLOW + String.format("%.1f", tile.getShardPercentage() * 100) + "%")
						.text(TextFormatting.WHITE + "{*top." + OedldoedlTechnology.MODID + ".upgrades*}" + " "
								+ TextFormatting.GOLD + tile.getShardCount() + " / 3");
			} else {
				probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffda943b).spacing(-1))
						.text(TextFormatting.GOLD + " ... ");
			}
		}
	}
}