package net.thep2wking.oedldoedltechnology.content.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.thep2wking.oedldoedltechnology.util.ModPowerSlugColor;

public class TilePowerSlug extends TileEntity {
    private float rotation;
    private ModPowerSlugColor color = ModPowerSlugColor.BLUE; // Default color

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        if (world != null) {
            markDirty();
            if (!world.isRemote) {
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    public ModPowerSlugColor getColor() {
        return color;
    }

    public void setColor(ModPowerSlugColor color) {
        this.color = color;
        if (world != null) {
            markDirty();
            if (!world.isRemote) {
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setFloat("Rotation", rotation);
        compound.setString("Color", color.getName());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.rotation = compound.getFloat("Rotation");
        this.color = ModPowerSlugColor.fromName(compound.getString("Color"));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setFloat("Rotation", rotation);
        tag.setString("Color", color.getName());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        this.rotation = tag.getFloat("Rotation");
        this.color = ModPowerSlugColor.fromName(tag.getString("Color"));
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }
}