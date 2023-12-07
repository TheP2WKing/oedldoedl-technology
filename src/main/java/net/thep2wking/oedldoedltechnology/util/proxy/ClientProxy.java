package net.thep2wking.oedldoedltechnology.util.proxy;

import matteroverdrive.client.RenderHandler;
import matteroverdrive.handler.weapon.ClientWeaponHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.render.RenderHandlerV2;


public class ClientProxy extends CommonProxy {
	public static RenderHandlerV2 renderHandler;
	private ClientWeaponHandler weaponHandler;

	public ClientProxy() {
		weaponHandler = new ClientWeaponHandler();
	}

	private void registerSubscribtions() {
		MinecraftForge.EVENT_BUS.register(weaponHandler);
		ModLogger.LOGGER.info("SOMETHING0");
		//MinecraftForge.EVENT_BUS.register(HandleSkinClient.INSTANCE);
	}

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		renderHandler = new RenderHandlerV2();
		renderHandler.createItemRenderers();
		// renderHandler.registerWeaponModuleRenders();
		ModLogger.LOGGER.info("SOMETHING1");
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		renderHandler = new RenderHandlerV2();
		renderHandler.createItemRenderers();
		renderHandler.init(Minecraft.getMinecraft().world, Minecraft.getMinecraft().getTextureManager());
		// renderHandler.createEntityRenderers(Minecraft.getMinecraft().getRenderManager());
		registerSubscribtions();
		
		renderHandler.registerWeaponLayers();
		weaponHandler.registerWeapon(ModItems.RAILGUN);
		ModLogger.LOGGER.info("SOMETHING2");
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}