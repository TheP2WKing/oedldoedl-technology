package net.thep2wking.oedldoedltechnology.util.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.thep2wking.oedldoedltechnology.content.assembler.ContainerAssembler;
import net.thep2wking.oedldoedltechnology.content.assembler.GuiAssembler;
import net.thep2wking.oedldoedltechnology.content.assembler.TileAssembler;
import net.thep2wking.oedldoedltechnology.content.constructor.ContainerConstructor;
import net.thep2wking.oedldoedltechnology.content.constructor.GuiConstructor;
import net.thep2wking.oedldoedltechnology.content.constructor.TileConstructor;

public class GuiHandler implements IGuiHandler {
    public static final int CONSTRUCTOR = 0;
    public static final int ASSEMBLER = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (id) {
            case CONSTRUCTOR:
                if (tileEntity instanceof TileConstructor) { // Assuming TileConstructor exists
                    return new ContainerConstructor(player.inventory, (TileConstructor) tileEntity);
                }
                break;
            case ASSEMBLER:
                if (tileEntity instanceof TileAssembler) {
                    return new ContainerAssembler(player.inventory, (TileAssembler) tileEntity);
                }
                break;
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (id) {
            case CONSTRUCTOR:
                if (tileEntity instanceof TileConstructor) { // Assuming TileConstructor exists
                    return new GuiConstructor(player.inventory, (TileConstructor) tileEntity);
                }
                break;
            case ASSEMBLER:
                if (tileEntity instanceof TileAssembler) {
                    return new GuiAssembler(player.inventory, (TileAssembler) tileEntity);
                }
                break;
        }
        return null;
    }
}