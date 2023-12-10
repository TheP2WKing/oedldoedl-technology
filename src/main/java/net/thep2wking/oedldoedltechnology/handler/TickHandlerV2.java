package net.thep2wking.oedldoedltechnology.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.thep2wking.oedldoedltechnology.util.proxy.ClientProxy;

public class TickHandlerV2 {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
			return;
		}
		if (ClientProxy.instance().getClientWeaponHandlerV2() != null) {
			ClientProxy.instance().getClientWeaponHandlerV2().onClientTick(event);
		}
	}
}
