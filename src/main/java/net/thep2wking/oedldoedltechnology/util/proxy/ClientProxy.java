package net.thep2wking.oedldoedltechnology.util.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.handler.ClientWeaponHandlerV2;
import net.thep2wking.oedldoedltechnology.handler.CommonWeaponHandlerV2;
import net.thep2wking.oedldoedltechnology.handler.TickHandlerV2;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.render.RenderHandlerV2;

public class ClientProxy extends CommonProxy {
	public static RenderHandlerV2 renderHandler;
	private ClientWeaponHandlerV2 weaponHandler;

	public ClientProxy() {
		weaponHandler = new ClientWeaponHandlerV2();
	}

	public static ClientProxy instance() {
		if (OedldoedlTechnology.PROXY instanceof ClientProxy)
			return (ClientProxy) OedldoedlTechnology.PROXY;
		else if (OedldoedlTechnology.PROXY == null)
			throw new UnsupportedOperationException("Attempted to access ClientProxy without it being initialized");
		throw new UnsupportedOperationException("Attempted to access ClientProxy on server side");
	}

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		renderHandler = new RenderHandlerV2();
		renderHandler.createItemRenderers();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		renderHandler.init(Minecraft.getMinecraft().world, Minecraft.getMinecraft().getTextureManager());
		MinecraftForge.EVENT_BUS.register(renderHandler);
		MinecraftForge.EVENT_BUS.register(new TickHandlerV2());
		renderHandler.registerWeaponLayers();
		renderHandler.createItemRenderers();
		weaponHandler = new ClientWeaponHandlerV2();
		renderHandler = new RenderHandlerV2();
		weaponHandler.registerWeapon(ModItems.RAILGUN);
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public ClientWeaponHandlerV2 getClientWeaponHandlerV2() {
		return weaponHandler;
	}

	@Override
	public CommonWeaponHandlerV2 getWeaponHandlerV2() {
		return weaponHandler;
	}
}