package net.thep2wking.oedldoedltechnology.util.world;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.thep2wking.oedldoedltechnology.content.block.BlockPowerSlug;
import net.thep2wking.oedldoedltechnology.content.block.TilePowerSlug;
import net.thep2wking.oedldoedltechnology.init.ModBlocks;
import net.thep2wking.oedldoedltechnology.util.ModPowerSlugColor;

public class ModPowerSlugGen implements IWorldGenerator {
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0: // Overworld
                generatePowerSlugs(world, rand, chunkX * 16, chunkZ * 16);
                break;
            case -1: // Nether
                break;
            case 1: // End
                break;
        }
    }

    private void generatePowerSlugs(World world, Random rand, int x, int z) {
        for (int i = 0; i < 80; i++) { // Number of attempts to place slugs per chunk
            int posX = x + rand.nextInt(16);
            int posY = rand.nextInt(100); // Height range
            int posZ = z + rand.nextInt(16);

            BlockPos pos = new BlockPos(posX, posY, posZ);
            IBlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof BlockLog) {
                EnumFacing facing = EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)];
                BlockPos offsetPos = pos.offset(facing);

                if (world.isAirBlock(offsetPos)) {
                    ModPowerSlugColor color = ModPowerSlugColor.getRandomColor();
                    IBlockState slugState;

                    switch (color) {
                        case BLUE:
                            slugState = ModBlocks.BLUE_POWER_SLUG.getDefaultState().withProperty(BlockPowerSlug.FACING, facing);
                            break;
                        case YELLOW:
                            slugState = ModBlocks.YELLOW_POWER_SLUG.getDefaultState().withProperty(BlockPowerSlug.FACING, facing);
                            break;
                        case PURPLE:
                            slugState = ModBlocks.PURPLE_POWER_SLUG.getDefaultState().withProperty(BlockPowerSlug.FACING, facing);
                            break;
                        default:
                            slugState = ModBlocks.BLUE_POWER_SLUG.getDefaultState().withProperty(BlockPowerSlug.FACING, facing);
                            break;
                    }

                    world.setBlockState(offsetPos, slugState, 2);
					Random random = new Random();
					float randomRotation = random.nextFloat() * 360.0f;
                    TileEntity tileentity = world.getTileEntity(offsetPos);
                    if (tileentity instanceof TilePowerSlug) {
						TilePowerSlug tilePowerSlug = (TilePowerSlug) tileentity;
                        tilePowerSlug.setColor(color);
						tilePowerSlug.setRotation(randomRotation);
						// ModLogger.LOGGER.info("Power Slug placed at: " + offsetPos + " with color: " + color.getName() + " and rotation: " + randomRotation);
					}
                    
                }
            }
        }
    }
}