package net.thep2wking.oedldoedltechnology.util.network;

import io.netty.channel.ChannelHandler;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.client.AbstractClientPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;

@ChannelHandler.Sharable
public class ModPacketPipeline {
	public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(OedldoedlTechnology.MODID + "_channel");
	public static  int packetID = 10000;

	public ModPacketPipeline() {

	}

	public void registerPackets() {
		CHANNEL.registerMessage(ModPacketUpdatePlasmaBolt.ClientHandler.class, ModPacketUpdatePlasmaBolt.class, 10000, Side.CLIENT);
		CHANNEL.registerMessage(ModPacketWeaponTick.ServerHandler.class, ModPacketWeaponTick.class, 10001, Side.SERVER);
	}

	public static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(
			Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType) {
		try {
			Side side = AbstractClientPacketHandler.class.isAssignableFrom(messageHandler) ? Side.CLIENT : Side.SERVER;
			CHANNEL.registerMessage(messageHandler, requestMessageType, packetID++, side);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerBiPacket(
			Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType) {
		if (AbstractBiPacketHandler.class.isAssignableFrom(messageHandler)) {
			CHANNEL.registerMessage(messageHandler, requestMessageType, packetID, Side.CLIENT);
			CHANNEL.registerMessage(messageHandler, requestMessageType, packetID++, Side.SERVER);
		} else {
			throw new IllegalArgumentException("Cannot register " + messageHandler.getName()
					+ " on both sides - must extend AbstractBiMessageHandler!");
		}
	}

	public void sendToServer(IMessage message) {
		CHANNEL.sendToServer(message);
	}

	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		CHANNEL.sendToAllAround(message, point);
	}

	public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		CHANNEL.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	public void sendToAllAround(IMessage message, EntityPlayer player, double range) {
		CHANNEL.sendToAllAround(message, new NetworkRegistry.TargetPoint(player.world.provider.getDimension(),
				player.posX, player.posY, player.posZ, range));
	}

	public void sendToAllAround(IMessage message, TileEntity tileEntity, double range) {
		CHANNEL.sendToAllAround(message,
				new NetworkRegistry.TargetPoint(tileEntity.getWorld().provider.getDimension(),
						tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), range));
	}

	public void sendTo(IMessage message, EntityPlayerMP player) {
		CHANNEL.sendTo(message, player);
	}

	public void sendToDimention(IMessage message, int dimention) {
		CHANNEL.sendToDimension(message, dimention);
	}

	public void sendToDimention(IMessage message, World world) {
		sendToDimention(message, world.provider);
	}

	public void sendToDimention(IMessage message, WorldProvider worldProvider) {
		CHANNEL.sendToDimension(message, worldProvider.getDimension());
	}

}
