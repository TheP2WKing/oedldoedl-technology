package net.thep2wking.oedldoedltechnology.util.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNobelisk extends ModelBase {
	private final ModelRenderer nobelisk;

	public ModelNobelisk() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.nobelisk = new ModelRenderer(this, 0, 0);
		this.nobelisk.setRotationPoint(0.0F, 8F, 0.0F);
		this.nobelisk.setTextureOffset(10, 10).addBox(-1.0F, -5.25F, -1.0F, 2, 1, 2, false);
		this.nobelisk.setTextureOffset(0, 6).addBox(-1.5F, -7.75F, -1.5F, 3, 3, 3, false);
		this.nobelisk.setTextureOffset(0, 0).addBox(-1.5F, -11.25F, -1.5F, 3, 3, 3, false);
		this.nobelisk.setTextureOffset(9, 6).addBox(-1.0F, -8.25F, -1.0F, 2, 1, 2, false);
		this.nobelisk.setTextureOffset(9, 0).addBox(-1.0F, -11.75F, -1.0F, 2, 1, 2, false);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		this.nobelisk.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}