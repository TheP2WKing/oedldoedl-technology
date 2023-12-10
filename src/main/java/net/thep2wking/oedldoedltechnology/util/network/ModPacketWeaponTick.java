package net.thep2wking.oedldoedltechnology.util.network;

import io.netty.buffer.ByteBuf;
import matteroverdrive.network.packet.PacketAbstract;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import matteroverdrive.network.packet.server.AbstractServerPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;

public class ModPacketWeaponTick extends PacketAbstract {
	long timestamp;
	PacketFirePlasmaShot plasmaShot;

	public ModPacketWeaponTick() {
	}

	public ModPacketWeaponTick(long timestamp) {
		this.timestamp = timestamp;
	}

	public ModPacketWeaponTick(long timestamp, PacketFirePlasmaShot plasmaShot) {
		this(timestamp);
		this.plasmaShot = plasmaShot;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		timestamp = buf.readLong();
		if (buf.readBoolean()) {
			this.plasmaShot = new PacketFirePlasmaShot();
			this.plasmaShot.fromBytes(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(timestamp);
		buf.writeBoolean(plasmaShot != null);
		if (plasmaShot != null) {
			plasmaShot.toBytes(buf);
		}
	}

	public static class ServerHandler extends AbstractServerPacketHandler<ModPacketWeaponTick> {
		@Override
		public void handleServerMessage(EntityPlayerMP player, ModPacketWeaponTick message, MessageContext ctx) {
			if (message.plasmaShot != null) {
				OedldoedlTechnology.PROXY.getModWeaponHandler().handlePlasmaShotFire(player, message.plasmaShot,
						message.timestamp);
			}
			OedldoedlTechnology.PROXY.getModWeaponHandler().addTimestamp(player, message.timestamp);
		}
	}
}
