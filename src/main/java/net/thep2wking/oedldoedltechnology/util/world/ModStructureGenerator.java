package net.thep2wking.oedldoedltechnology.util.world;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;

public class ModStructureGenerator extends WorldGenerator {
	private static final int VARIATION = 2;
	public final String modid;
	public final String name;

	public ModStructureGenerator(String modid, String name) {
		this.name = name;
		this.modid = modid;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		WorldServer worldServer = (WorldServer) world;
		MinecraftServer minecraftServer = world.getMinecraftServer();
		TemplateManager templateManager = worldServer.getStructureTemplateManager();
		Template template = templateManager.get(minecraftServer,
				new ResourceLocation(this.modid, this.name));
		if (template == null) {
			return false;
		}

		if (canSpawn(template, worldServer, position)) {
			Rotation rotation = Rotation.values()[rand.nextInt(3)];
			PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation)
					.setIgnoreStructureBlock(false);
			template.addBlocksToWorld(world, position, settings);
			Map<BlockPos, String> dataBlocks = template.getDataBlocks(position, settings);
			// fillAirBlocksUnderStructureWithDirt(world, position, template.getSize(), rotation);
			for (Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
				try {
					String[] data = entry.getValue().split(" ");
					if (data.length < 2)
						continue;
					Block block = Block.getBlockFromName(data[0]);
					IBlockState state = null;
					if (data.length == 3)
						state = block.getStateFromMeta(Integer.parseInt(data[2]));
					else
						state = block.getDefaultState();
					for (Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties()
							.entrySet()) {
						if (entry2.getKey().getValueClass().equals(EnumFacing.class)
								&& entry2.getKey().getName().equals("facing")) {
							state = state.withRotation(rotation.add(Rotation.CLOCKWISE_180));
							break;
						}
					}
					world.setBlockState(entry.getKey(), state, 3);
					TileEntity te = world.getTileEntity(entry.getKey());
					if (te == null)
						continue;
					if (te instanceof TileEntityLockableLoot)
						((TileEntityLockableLoot) te).setLootTable(new ResourceLocation(data[1]), rand.nextLong());
				} catch (Exception e) {
					continue;
				}
			}
			return true;
		}
		return false;
	}

	// public static void fillAirBlocksUnderStructureWithDirt(World world, BlockPos pos, BlockPos size, Rotation rotation) {
	// 	for (int x = 0; x < size.getX(); x++) {
	// 		for (int z = 0; z < size.getZ(); z++) {
	// 			BlockPos currentPos = pos.add(x, 0, z);
	// 			BlockPos rotatedPos = rotatePos(currentPos, pos, rotation);
	// 			Block block = world.getBlockState(rotatedPos.down()).getBlock();
	// 			if (block == Blocks.AIR || block.isReplaceable(world, rotatedPos.down())) {
	// 				world.setBlockState(rotatedPos.down(), Blocks.DIRT.getDefaultState(), 3);
	// 			}
	// 		}
	// 	}
	// }
	
	// public static BlockPos rotatePos(BlockPos pos, BlockPos origin, Rotation rotation) {
	// 	int x = pos.getX() - origin.getX();
	// 	int z = pos.getZ() - origin.getZ();
	// 	switch (rotation) {
	// 		case CLOCKWISE_90:
	// 			return new BlockPos(origin.getX() + z, pos.getY(), origin.getZ() - x);
	// 		case CLOCKWISE_180:
	// 			return new BlockPos(origin.getX() - x, pos.getY(), origin.getZ() - z);
	// 		case COUNTERCLOCKWISE_90:
	// 			return new BlockPos(origin.getX() - z, pos.getY(), origin.getZ() + x);
	// 		default:
	// 			return pos;
	// 	}
	// }

	public static boolean canSpawn(Template template, World world, BlockPos pos) {
		return isCornerValid(world, pos) && isCornerValid(world, pos.add(template.getSize().getX(), 0, 0))
				&& isCornerValid(world, pos.add(template.getSize().getX(), 0, template.getSize().getZ()))
				&& isCornerValid(world, pos.add(0, 0, template.getSize().getZ()));
	}

	public static boolean isCornerValid(World world, BlockPos pos) {
		int groundY = getGroundFromAbove(world, pos.getX(), pos.getZ());
		return groundY > pos.getY() - VARIATION && groundY < pos.getY() + VARIATION;
	}

	public static int getGroundFromAbove(World world, int x, int z) {
		int y = 255;
		boolean foundGround = false;
		while (!foundGround && y-- > 0) {
			Block block = world
					.getBlockState(new BlockPos(x, TechnologyConfig.WORLD.CRASH_SITE_NO_FIRE_EDITION ? y - 1 : y + 1, z))
					.getBlock();
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				y = -1;
				break;
			}
			// foundGround = block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.SNOW || block == Blocks.SNOW_LAYER || block == Blocks.MYCELIUM || block == Blocks.STONE || block == Blocks.GRAVEL;
			foundGround = block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRAVEL;
		}
		return y;
	}
}