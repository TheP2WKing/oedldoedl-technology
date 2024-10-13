package net.thep2wking.oedldoedltechnology.integration.jer;

import jeresources.api.render.IMobRenderHook;
import net.minecraft.client.renderer.GlStateManager;

@SuppressWarnings("rawtypes")
public class ModRenderHooks {
	public static final IMobRenderHook REPUBLICAN_SPACE_RANGER = (renderInfo, entity) -> {
		GlStateManager.translate(0, -0.4, 0);
		return renderInfo;
	};
}