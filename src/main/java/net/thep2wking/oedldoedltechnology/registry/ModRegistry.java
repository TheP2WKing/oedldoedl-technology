package net.thep2wking.oedldoedltechnology.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRegistryHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.init.ModSounds;
import net.thep2wking.oedldoedltechnology.util.ModModules;

@Mod.EventBusSubscriber
public class ModRegistry {
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		ModRegistryHelper.registerModels(event, OedldoedlTechnology.MODID);
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		ModLogger.registeredBlocksLogger(OedldoedlTechnology.MODID);
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		ModLogger.registeredItemsLogger(OedldoedlTechnology.MODID);

		if (ModModules.isMatterOverdriveLoaded()) {
			ModRegistryHelper.registerItem(event, ModItems.RAILGUN);
			ModRegistryHelper.registerItem(event, ModItems.UP_N_ATOMIZER);
	
			ModRegistryHelper.registerItem(event, ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT);
			ModRegistryHelper.registerItem(event, ModItems.RAILGUN_HANDLE);
			ModRegistryHelper.registerItem(event, ModItems.RAILGUN_RECEIVER);
			ModRegistryHelper.registerItem(event, ModItems.TESLA_CORE);
		}

		ModRegistryHelper.registerItem(event, ModItems.IPHONE_14_PRO_MAX);
	}

	@SubscribeEvent
	public static void onSoundEventRegister(RegistryEvent.Register<SoundEvent> event) {
		ModLogger.registeredSoundEventsLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.RAILGUN_SHOT);
		ModRegistryHelper.registerSoundEvent(event, ModSounds.UP_N_ATOMIZER_SHOT);
	}
}