package net.thep2wking.oedldoedltechnology.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedlcore.util.ModRegistryHelper;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModTechnologyRegistryHelper;
import net.thep2wking.oedldoedltechnology.content.block.TilePowerSlug;
import net.thep2wking.oedldoedltechnology.init.ModBlocks;
import net.thep2wking.oedldoedltechnology.init.ModItems;
import net.thep2wking.oedldoedltechnology.init.ModLootTables;
import net.thep2wking.oedldoedltechnology.init.ModSounds;
import net.thep2wking.oedldoedltechnology.util.render.ModRenderer;

@Mod.EventBusSubscriber
public class ModRegistry {
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		ModTechnologyRegistryHelper.registerModels(event, OedldoedlTechnology.MODID);
		ModRenderer.registerEntityRender();
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		ModLogger.registeredBlocksLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerBlock(event, ModBlocks.CONSTRUCTOR);
		ModRegistryHelper.registerBlock(event, ModBlocks.ASSEMBLER);

		ModRegistryHelper.registerBlock(event, ModBlocks.BLUE_POWER_SLUG);
		ModRegistryHelper.registerBlock(event, ModBlocks.YELLOW_POWER_SLUG);
		ModRegistryHelper.registerBlock(event, ModBlocks.PURPLE_POWER_SLUG);

		ModRegistryHelper.registerBlock(event, ModBlocks.MODULAR_FRAME);
		ModRegistryHelper.registerBlock(event, ModBlocks.HEAVY_MODULAR_FRAME);
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		ModLogger.registeredItemsLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerItemBlock(event, ModItems.CONSTRUCTOR);
		ModRegistryHelper.registerItemBlock(event, ModItems.ASSEMBLER);

		ModRegistryHelper.registerItemBlock(event, ModItems.BLUE_POWER_SLUG);
		ModRegistryHelper.registerItemBlock(event, ModItems.YELLOW_POWER_SLUG);
		ModRegistryHelper.registerItemBlock(event, ModItems.PURPLE_POWER_SLUG);

		ModRegistryHelper.registerItem(event, ModItems.RAILGUN);
		ModRegistryHelper.registerItem(event, ModItems.UP_N_ATOMIZER);

		ModRegistryHelper.registerItem(event, ModItems.OEDLDOEDL_ISOLINEAR_CIRCUIT);
		ModRegistryHelper.registerItem(event, ModItems.RAILGUN_HANDLE);
		ModRegistryHelper.registerItem(event, ModItems.RAILGUN_RECEIVER);
		ModRegistryHelper.registerItem(event, ModItems.TESLA_CORE);

		ModRegistryHelper.registerItem(event, ModItems.UP_N_ATOMIZER_HANDLE);
		ModRegistryHelper.registerItem(event, ModItems.UP_N_ATOMIZER_RECEIVER);
		ModRegistryHelper.registerItem(event, ModItems.SOCKWAVE_EMITTER);

		ModRegistryHelper.registerItem(event, ModItems.ALIEN_EGG);

		ModRegistryHelper.registerItem(event, ModItems.IPHONE_14_PRO_MAX);

		ModRegistryHelper.registerItem(event, ModItems.POWER_SHARD);
		
		ModRegistryHelper.registerItem(event, ModItems.IRON_PLATE);
		ModRegistryHelper.registerItem(event, ModItems.IRON_ROD);
		ModRegistryHelper.registerItem(event, ModItems.SCREW);
		ModRegistryHelper.registerItem(event, ModItems.REINFORCED_IRON_PLATE);

		ModRegistryHelper.registerItem(event, ModItems.WIRE);
		ModRegistryHelper.registerItem(event, ModItems.CABLE);
		ModRegistryHelper.registerItem(event, ModItems.COPPER_SHEET);
		ModRegistryHelper.registerItem(event, ModItems.MODULAR_FRAME);
		ModRegistryHelper.registerItem(event, ModItems.ROTOR);
		ModRegistryHelper.registerItem(event, ModItems.SMART_PLATING);

		ModRegistryHelper.registerItem(event, ModItems.STEEL_BEAM);
		ModRegistryHelper.registerItem(event, ModItems.STEEL_PIPE);
		ModRegistryHelper.registerItem(event, ModItems.VERSATILE_FRAMEWORK);
		
		ModRegistryHelper.registerItem(event, ModItems.AUTOMATED_WIRING);
		ModRegistryHelper.registerItem(event, ModItems.ENCASED_INDUSTRIAL_BEAM);
		ModRegistryHelper.registerItem(event, ModItems.HEAVY_MODULAR_FRAME);
		ModRegistryHelper.registerItem(event, ModItems.MOTOR);
		ModRegistryHelper.registerItem(event, ModItems.STATOR);

		ModRegistryHelper.registerItem(event, ModItems.NOBELISK_DETONATOR);
		ModRegistryHelper.registerItem(event, ModItems.NORMAL_NOBELISK);
		ModRegistryHelper.registerItem(event, ModItems.CLUSTER_NOBELISK);
	}

	@SubscribeEvent
	public static void onSoundEventRegister(RegistryEvent.Register<SoundEvent> event) {
		ModLogger.registeredSoundEventsLogger(OedldoedlTechnology.MODID);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.RAILGUN_SHOT);
		ModRegistryHelper.registerSoundEvent(event, ModSounds.RAILGUN_IMPACT);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.UP_N_ATOMIZER_SHOT);
		ModRegistryHelper.registerSoundEvent(event, ModSounds.UP_N_ATOMIZER_IMPACT);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.REPUBLICAN_SPACE_RANGER_IDLE);
		ModRegistryHelper.registerSoundEvent(event, ModSounds.REPUBLICAN_SPACE_RANGER_DEATH);

		ModRegistryHelper.registerSoundEvent(event, ModSounds.DETONATE);
		ModRegistryHelper.registerSoundEvent(event, ModSounds.NOBELISK_IMPACT);
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TilePowerSlug.class, new ResourceLocation(OedldoedlTechnology.MODID, "power_slug"));
	}

	public static void registerLootTables() {
		ModRegistryHelper.registerLootTable(ModLootTables.CRASH_SITE);
	}
}