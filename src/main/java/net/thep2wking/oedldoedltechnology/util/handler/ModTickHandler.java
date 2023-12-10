package net.thep2wking.oedldoedltechnology.util.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.thep2wking.oedldoedltechnology.util.proxy.ClientProxy;

public class ModTickHandler {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
			return;
		}
		if (ClientProxy.instance().getModClientWeaponHandler() != null) {
			ClientProxy.instance().getModClientWeaponHandler().onClientTick(event);
		}
	}
}
