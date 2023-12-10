package net.thep2wking.oedldoedltechnology.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRegistryHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.init.ModSounds;

@Mod.EventBusSubscriber
public class ModRegistry {
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		ModLogger.registeredBlocksLogger(OedldoedlTechnology.MODID);
		
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		ModLogger.registeredItemsLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerItem(event, ModItems.IPHONE_14_PRO_MAX);
		ModRegistryHelper.registerItem(event, ModItems.RAILGUN);
	}

	@SubscribeEvent
	public static void onSoundEventRegister(RegistryEvent.Register<SoundEvent> event) {
		ModLogger.registeredSoundEventsLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.RAILGUN);
	}
}