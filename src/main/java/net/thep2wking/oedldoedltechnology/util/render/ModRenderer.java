package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.util.ModRenderHelper;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityAlienEgg;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRenderer {
	public static void registerRenderer() {
		ModRenderHelper.addThrowableRender(EntityAlienEgg.class, ModItems.ALIEN_EGG);
	}

	@SideOnly(Side.CLIENT)
	public static void registerEntityRender() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRepublicanSpaceRanger.class, RenderRepublicanSpaceRanger.FACTORY);
	}
}