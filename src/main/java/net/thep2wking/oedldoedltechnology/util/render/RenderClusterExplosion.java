package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityClusterExplosion;
import net.thep2wking.oedldoedltechnology.util.model.ModelClusterExplosion;

@SideOnly(Side.CLIENT)
public class RenderClusterExplosion extends Render<EntityClusterExplosion> {
	public RenderClusterExplosion(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityClusterExplosion entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.translate(0.0F, 0.15F, 0.0F);
		this.bindEntityTexture(entity);
		ModelClusterExplosion MODEL = new ModelClusterExplosion();
		MODEL.render(entity, 0.0F, 0.0F, 0.0F, entityYaw, partialTicks, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(EntityClusterExplosion entity) {
		return new ResourceLocation(OedldoedlTechnology.MODID, "textures/entity/cluster_explosion.png");
	}

	public static final RenderClusterExplosion.Factory FACTORY = new RenderClusterExplosion.Factory();

	public static class Factory implements IRenderFactory<EntityClusterExplosion> {
		@Override
		public Render<? super EntityClusterExplosion> createRenderFor(RenderManager manager) {
			return new RenderClusterExplosion(manager);
		}
	}
}