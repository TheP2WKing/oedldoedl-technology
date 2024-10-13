package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.thep2wking.oedldoedlcore.util.ModRenderHelper;
import net.thep2wking.oedldoedltechnology.content.block.TilePowerSlug;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityAlienEgg;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityClusterExplosion;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityClusterNobelisk;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityNormalNobelisk;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRenderer {
	public static void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePowerSlug.class, new RenderTilePowerSlug());
	}
	
	public static void registerEntityRender() {
		ModRenderHelper.addEntityRender(EntityRepublicanSpaceRanger.class, RenderRepublicanSpaceRanger.FACTORY);
		ModRenderHelper.addEntityRender(EntityClusterExplosion.class, RenderClusterExplosion.FACTORY);
		RenderEntityNobelisk.register(EntityNormalNobelisk.class);
		RenderEntityNobelisk.register(EntityClusterNobelisk.class);
	}

	public static void registerRenderer() {
		ModRenderHelper.addThrowableRender(EntityAlienEgg.class, ModItems.ALIEN_EGG);
		// ModRenderHelper.addThrowableRender(EntityClusterExplosion.class, ModItems.IRON_ROD);
	}
}