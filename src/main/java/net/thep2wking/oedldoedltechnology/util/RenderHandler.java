package net.thep2wking.oedldoedltechnology.util;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class RenderHandler {
	private static RenderRailgun renderer;

	public void createItemRenderers() {
		renderer = new RenderRailgun();
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void activateItemRenderers() {
		renderer.init();
	}

	public void bakeItemModels() {
		renderer.bakeModel();
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		event.getModelRegistry().putObject( new ModelResourceLocation("oedldoedltechnology:railgun", "inventory"), renderer);

		activateItemRenderers();
		bakeItemModels();

		ModLogger.LOGGER.info("RENDER OR SOMETHING");
	}
}
