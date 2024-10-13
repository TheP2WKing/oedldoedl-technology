package net.thep2wking.oedldoedltechnology.util.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelClusterExplosion extends ModelBase {
	private final ModelRenderer cluster;

	public ModelClusterExplosion() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.cluster = new ModelRenderer(this, 0, 0);
		this.cluster.setRotationPoint(0, 8f, 0);
		this.cluster.setTextureOffset(0, 0).addBox(-1, -9, -1, 2, 2, 2, false);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		this.cluster.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}