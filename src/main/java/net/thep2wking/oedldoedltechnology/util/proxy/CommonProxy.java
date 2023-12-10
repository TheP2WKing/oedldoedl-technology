package net.thep2wking.oedldoedltechnology.util.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.thep2wking.oedldoedltechnology.handler.CommonWeaponHandlerV2;

public class CommonProxy {
	private final CommonWeaponHandlerV2 commonWeaponHandler;

	public CommonProxy() {
		commonWeaponHandler = new CommonWeaponHandlerV2();
	}

	public void preInit(FMLPreInitializationEvent event) {
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(getWeaponHandlerV2());
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	public void render() {
	}

	public CommonWeaponHandlerV2 getWeaponHandlerV2() {
		return commonWeaponHandler;
	}
}