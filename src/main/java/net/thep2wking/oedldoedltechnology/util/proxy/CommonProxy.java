package net.thep2wking.oedldoedltechnology.util.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thep2wking.oedldoedltechnology.util.handler.ModCommonWeaponHandler;

public class CommonProxy {
	private final ModCommonWeaponHandler commonWeaponHandler;

	public CommonProxy() {
		commonWeaponHandler = new ModCommonWeaponHandler();
	}

	public void preInit(FMLPreInitializationEvent event) {
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(getModWeaponHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	public void render() {
	}

	public ModCommonWeaponHandler getModWeaponHandler() {
		return commonWeaponHandler;
	}
}