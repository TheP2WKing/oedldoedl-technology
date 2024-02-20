package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.thep2wking.oedldoedlcore.api.entity.ModEntityRenderBase;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;

public class RenderRepublicanSpaceRanger extends ModEntityRenderBase<EntityRepublicanSpaceRanger> {
	public RenderRepublicanSpaceRanger(String modid, String name, RenderManager renderManager, ModelBase model,
			float shadowSize) {
		super(modid, name, renderManager, model, shadowSize);
		this.addLayer(new LayerRepublicanSpaceRangerWeapon(this));
	}

	@Override
	protected boolean canRenderName(EntityRepublicanSpaceRanger entity) {
		return entity.getTeam() != null || Minecraft.getMinecraft().player.getDistance(entity) < 18;
	}

	@Override
	protected void preRenderCallback(EntityRepublicanSpaceRanger entity, float partialTicks) {
		if (entity.getIsLegendary()) {
			GlStateManager.scale(1.5, 1.5, 1.5);
		}
	}

	@Override
	public void doRender(EntityRepublicanSpaceRanger entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		((ModelBiped) mainModel).rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public static final RenderRepublicanSpaceRanger.Factory FACTORY = new RenderRepublicanSpaceRanger.Factory();

	public static class Factory implements IRenderFactory<EntityRepublicanSpaceRanger> {
		@Override
		public Render<? super EntityRepublicanSpaceRanger> createRenderFor(RenderManager manager) {
			return new RenderRepublicanSpaceRanger(OedldoedlTechnology.MODID, "republican_space_ranger", manager,
					new ModelPlayer(0f, false), 0.6F);
		}
	}
}