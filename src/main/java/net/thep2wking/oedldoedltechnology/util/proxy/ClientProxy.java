package net.thep2wking.oedldoedltechnology.util.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.util.handler.ModClientWeaponHandler;
import net.thep2wking.oedldoedltechnology.util.handler.ModCommonWeaponHandler;
import net.thep2wking.oedldoedltechnology.util.handler.ModTickHandler;
import net.thep2wking.oedldoedltechnology.util.render.ModRenderHandler;

public class ClientProxy extends CommonProxy {
	public static ModRenderHandler renderHandler;
	private ModClientWeaponHandler weaponHandler;

	public ClientProxy() {
		weaponHandler = new ModClientWeaponHandler();
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
		renderHandler = new ModRenderHandler();
		renderHandler.createItemRenderers();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		renderHandler.init(Minecraft.getMinecraft().world, Minecraft.getMinecraft().getTextureManager());
		MinecraftForge.EVENT_BUS.register(renderHandler);
		MinecraftForge.EVENT_BUS.register(new ModTickHandler());
		renderHandler.registerWeaponLayers();
		renderHandler.createItemRenderers();
		weaponHandler = new ModClientWeaponHandler();
		renderHandler = new ModRenderHandler();
		weaponHandler.registerWeapon(ModItems.RAILGUN);
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public ModClientWeaponHandler getModClientWeaponHandler() {
		return weaponHandler;
	}

	@Override
	public ModCommonWeaponHandler getModWeaponHandler() {
		return weaponHandler;
	}
}