package net.thep2wking.oedldoedltechnology.api.factory.handler;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class VanillaPacketHandler {
	public VanillaPacketHandler() {
	}

	public static void sendTileEntityUpdate(TileEntity tileEntity) {
		if (!tileEntity.getWorld().isRemote) {
			List<EntityPlayer> playerList = tileEntity.getWorld().playerEntities;
			SPacketUpdateTileEntity updatePacket = tileEntity.getUpdatePacket();
			if (updatePacket != null) {
				@SuppressWarnings("rawtypes")
				Iterator var3 = playerList.iterator();
				while (var3.hasNext()) {
					Object obj = var3.next();
					EntityPlayerMP entityPlayer = (EntityPlayerMP) obj;
					if (Math.hypot(entityPlayer.posX - (double) tileEntity.getPos().getX() + 0.5,
							entityPlayer.posZ - (double) tileEntity.getPos().getZ() + 0.5) < 64.0) {
						entityPlayer.connection.sendPacket(updatePacket);
					}
				}

			}
		}
	}
}