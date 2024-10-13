package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModEntityNobeliskBase;
import net.thep2wking.oedldoedltechnology.util.model.ModelNobelisk;

@SideOnly(Side.CLIENT)
public class RenderEntityNobelisk extends Render<ModEntityNobeliskBase> {
	public RenderEntityNobelisk(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.15F;
	}

	@Override
	public void doRender(ModEntityNobeliskBase entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.translate(0.0F, 0.15F, 0.0F);
		GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		if (!entity.hasCollided()) {
			int speedMultiplier = 5;
			entity.setRotX((entity.ticksExisted * speedMultiplier) % 360);
			entity.setRotY((entity.ticksExisted * speedMultiplier) % 360);
			entity.setRotZ((entity.ticksExisted * speedMultiplier) % 360);
		}
		GlStateManager.rotate(entity.getRotX(), 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(entity.getRotY(), 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(entity.getRotZ(), 0.0F, 0.0F, 1.0F);
		this.bindEntityTexture(entity);
		ModelNobelisk MODEL = new ModelNobelisk();
		MODEL.render(entity, 0.0F, 0.0F, 0.0F, entityYaw, partialTicks, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(ModEntityNobeliskBase entity) {
		return new ResourceLocation(OedldoedlTechnology.MODID,
				"textures/entity/" + entity.getEntityTextureName() + ".png");
	}

	public static final RenderEntityNobelisk.Factory FACTORY = new RenderEntityNobelisk.Factory();

	public static class Factory implements IRenderFactory<ModEntityNobeliskBase> {
		@Override
		public Render<? super ModEntityNobeliskBase> createRenderFor(RenderManager manager) {
			return new RenderEntityNobelisk(manager);
		}
	}

	public static void register(Class<? extends ModEntityNobeliskBase> nobeliskEntityClas) {
		RenderingRegistry.registerEntityRenderingHandler(nobeliskEntityClas, FACTORY);
	}
}