package net.thep2wking.oedldoedltechnology.util.network;

import io.netty.buffer.ByteBuf;
import matteroverdrive.network.packet.client.AbstractClientPacketHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.entity.EntityRailgunBolt;
import net.thep2wking.oedldoedltechnology.util.handler.ModClientWeaponHandler;

public class ModPacketUpdatePlasmaBolt implements IMessage {
	int boltID;
	double posX;
	float posY;
	double posZ;

	public ModPacketUpdatePlasmaBolt() {
	}

	public ModPacketUpdatePlasmaBolt(int boltID, double posX, double posY, double posZ) {
		this.boltID = boltID;
		this.posX = posX;
		this.posY = (float) posY;
		this.posZ = posZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		boltID = buf.readInt();
		posX = buf.readDouble();
		posY = buf.readFloat();
		posZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(boltID);
		buf.writeDouble(posX);
		buf.writeFloat(posY);
		buf.writeDouble(posZ);
	}

	public static class ClientHandler extends AbstractClientPacketHandler<ModPacketUpdatePlasmaBolt> {
		@SideOnly(Side.CLIENT)
		@Override
		public void handleClientMessage(EntityPlayerSP player, ModPacketUpdatePlasmaBolt message, MessageContext ctx) {
			Entity bolt = ((ModClientWeaponHandler) OedldoedlTechnology.PROXY.getModWeaponHandler())
					.getPlasmaBolt(message.boltID);
			if (bolt instanceof EntityRailgunBolt) {
				bolt.setPosition(message.posX, message.posY, message.posZ);
			}
		}
	}
}
