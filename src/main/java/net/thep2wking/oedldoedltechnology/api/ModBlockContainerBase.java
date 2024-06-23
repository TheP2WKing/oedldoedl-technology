package net.thep2wking.oedldoedltechnology.api;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;

public abstract class ModBlockContainerBase extends BlockContainer {
	public final String modid;
	public final String name;
	public final CreativeTabs tab;
	public final SoundType sound;
	public final MapColor mapColor;
	public final int harvestLevel;
	public final ModToolTypes toolType;
	public final float hardness;
	public final float resistance;
	public final int lightLevel;

	public ModBlockContainerBase(String modid, String name, CreativeTabs tab, Material material, SoundType sound,
			MapColor mapColor, int harvestLevel, ModToolTypes toolType, float hardness, float resistance,
			int lightLevel) {
		super(material);
		this.modid = modid;
		this.name = name;
		this.tab = tab;
		this.sound = sound;
		this.mapColor = mapColor;
		this.harvestLevel = harvestLevel;
		this.toolType = toolType;
		this.hardness = hardness;
		this.resistance = resistance;
		this.lightLevel = lightLevel;
		setUnlocalizedName(this.modid + "." + this.name);
		setRegistryName(this.modid + ":" + this.name);
		setCreativeTab(this.tab);
		setSoundType(this.sound);
		setHarvestLevel(this.toolType.getToolType(), this.harvestLevel);
		setHardness(this.hardness);
		setResistance(this.resistance);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return mapColor;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (CoreConfig.PROPERTIES.BLOCKS_EMIT_LIGHT) {
			return lightLevel;
		}
		return 0;
	}

	private boolean isBeaconBase;

	public Block setBeaconBase() {
		isBeaconBase = CoreConfig.PROPERTIES.BEACONS.BEACON_BASES;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return isBeaconBase;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		return tileEntity != null ? ItemHandlerHelper.calcRedstoneFromInventory(
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) : 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (!player.isSneaking()) {
				player.openGui(OedldoedlTechnology.INSTANCE, getGuiId(), world, pos.getX(),
						pos.getY(),
						pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te,
			ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockToAir(pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest) {
			return true;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	protected abstract int getGuiId();
}