package net.thep2wking.oedldoedltechnology.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;

/**
 * @author TheP2WKing
 */
public class ModBlockDirectionalBase extends BlockDirectional {
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

	/**
	 * @author TheP2WKing
	 * @param modid        String
	 * @param name         String
	 * @param tab          {@link CreativeTabs}
	 * @param material     {@link Material}
	 * @param sound        {@link SoundType}
	 * @param mapColor     {@link MapColor}
	 * @param harvestLevel int
	 * @param toolType     {@link ModToolTypes}
	 * @param hardness     float
	 * @param resistance   float
	 * @param lightLevel   int
	 */
	public ModBlockDirectionalBase(String modid, String name, CreativeTabs tab, Material material, SoundType sound,
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
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
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
		isBeaconBase = true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return isBeaconBase;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}
}