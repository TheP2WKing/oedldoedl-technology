package net.thep2wking.oedldoedltechnology.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModTileEntityBase extends TileEntity implements ITickable {
	public ModTileEntityBase() {
	}

	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return this.world.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq((double) this.pos.getX() + 0.5,
				(double) this.pos.getY() + 0.5, (double) this.pos.getZ() + 0.5) <= 64.0;
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.readFromNBTSynced(tagCompound, false);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		this.writeToNBTSynced(tagCompound, false);
		return tagCompound;
	}

	public final NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	public final void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public final SPacketUpdateTileEntity getUpdatePacket() {
		if (!this.hasUpdatePacket()) {
			return null;
		} else {
			NBTTagCompound tagCompound = new NBTTagCompound();
			this.writeToNBTSynced(tagCompound, true);
			return new SPacketUpdateTileEntity(this.pos, this.getBlockMetadata(), tagCompound);
		}
	}

	public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBTSynced(pkt.getNbtCompound(), true);
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	public boolean hasUpdatePacket() {
		return true;
	}

	protected abstract void writeToNBTSynced(NBTTagCompound var1, boolean var2);

	protected abstract void readFromNBTSynced(NBTTagCompound var1, boolean var2);
}