package net.thep2wking.oedldoedltechnology.util.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPowerSlug  extends ModelBase {
	private ModelRenderer layer1;
	private ModelRenderer layer2;
	private ModelRenderer layer3;
	private ModelRenderer layer4;
	private ModelRenderer layer5;
	private ModelRenderer layer6;
	private ModelRenderer cube_r1;
	private ModelRenderer cube_r2;
	private ModelRenderer cube_r3;
	private ModelRenderer cube_r4;
	private ModelRenderer cube_r5;
	private ModelRenderer layer7;
	private ModelRenderer cube_r6;
	private ModelRenderer cube_r7;
	private ModelRenderer cube_r8;

	public ModelPowerSlug() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		layer1 = new ModelRenderer(this);
		layer1.setRotationPoint(0, 16, 0);
		layer1.setTextureOffset(20, 13).addBox(-3, 7, 3, 6, 1, 3);
		layer1.setTextureOffset(0, 9).addBox(-3, 7, -5, 6, 1, 8);
		layer1.setTextureOffset(24, 36).addBox(-2, 7, 6, 4, 1, 2);
		layer1.setTextureOffset(30, 26).addBox(-4, 7, -3, 1, 1, 6);
		layer1.setTextureOffset(22, 25).addBox(3, 7, -3, 1, 1, 6);
		layer1.setTextureOffset(36, 36).addBox(-2, 7, -7, 4, 1, 2);

		layer2 = new ModelRenderer(this);
		layer2.setRotationPoint(0, 16, 0);
		layer2.setTextureOffset(38, 21).addBox(-1.5F, 6, 5.5F, 3, 1, 2);
		layer2.setTextureOffset(16, 32).addBox(-2.5F, 6, 2.5F, 5, 1, 3);
		layer2.setTextureOffset(35, 14).addBox(-1.5F, 6, -6.5F, 3, 1, 3);
		layer2.setTextureOffset(19, 19).addBox(-3.5F, 6, -2.5F, 7, 1, 5);
		layer2.setTextureOffset(35, 0).addBox(-2.5F, 6, -4.5F, 5, 1, 2);

		layer3 = new ModelRenderer(this);
		layer3.setRotationPoint(0, 16, 0);
		layer3.setTextureOffset(20, 9).addBox(-3, 5.5F, 3, 6, 1, 3);
		layer3.setTextureOffset(0, 0).addBox(-3, 5.5F, -5, 6, 1, 8);
		layer3.setTextureOffset(12, 36).addBox(-2, 5.5F, 6, 4, 1, 2);
		layer3.setTextureOffset(8, 25).addBox(-4, 5.5F, -3, 1, 1, 6);
		layer3.setTextureOffset(0, 24).addBox(3, 5.5F, -3, 1, 1, 6);
		layer3.setTextureOffset(0, 36).addBox(-2, 5.5F, -7, 4, 1, 2);

		layer4 = new ModelRenderer(this);
		layer4.setRotationPoint(0, 16, 0);
		layer4.setTextureOffset(38, 18).addBox(-1.5F, 5, 5.5F, 3, 1, 2);
		layer4.setTextureOffset(0, 32).addBox(-2.5F, 5, 2.5F, 5, 1, 3);
		layer4.setTextureOffset(35, 10).addBox(-1.5F, 5, -6.5F, 3, 1, 3);
		layer4.setTextureOffset(0, 18).addBox(-3.5F, 5, -2.5F, 7, 1, 5);
		layer4.setTextureOffset(32, 33).addBox(-2.5F, 5, -4.5F, 5, 1, 2);

		layer5 = new ModelRenderer(this);
		layer5.setRotationPoint(0, 16, 0);
		layer5.setTextureOffset(0, 21).addBox(-0.5F, 4.5F, 5.5F, 1, 1, 1);
		layer5.setTextureOffset(16, 25).addBox(-1.5F, 4, 2.5F, 3, 1, 3);
		layer5.setTextureOffset(0, 15).addBox(-1.5F, 4, -5.5F, 3, 1, 1);
		layer5.setTextureOffset(20, 0).addBox(-2.5F, 4, -2.5F, 5, 1, 5);
		layer5.setTextureOffset(28, 6).addBox(-2.5F, 4, -4.5F, 5, 1, 2);

		layer6 = new ModelRenderer(this);
		layer6.setRotationPoint(0, 16, 0);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(2, 6, -1);
		layer6.addChild(cube_r1);
		setRotationAngle(cube_r1, 0, -0.3927F, 0);
		cube_r1.setTextureOffset(38, 5).addBox(-2.5F, 0, -6.4F, 1, 1, 4);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-1, 7, -1);
		layer6.addChild(cube_r2);
		setRotationAngle(cube_r2, 0, 0.3927F, 0);
		cube_r2.setTextureOffset(20, 3).addBox(2, 0, -6, 1, 1, 1);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(1, 7, -1);
		layer6.addChild(cube_r3);
		setRotationAngle(cube_r3, 0, -0.3927F, 0);
		cube_r3.setTextureOffset(20, 6).addBox(-3, 0, -6, 1, 1, 1);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(4, 9, -4);
		layer6.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.3927F, 0, 0);
		cube_r4.setTextureOffset(0, 9).addBox(-3.2F, -7.5F, -0.6F, 1, 5, 1);
		cube_r4.setTextureOffset(4, 9).addBox(-5.8F, -7.5F, -0.6F, 1, 5, 1);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-2, 6, -1);
		layer6.addChild(cube_r5);
		setRotationAngle(cube_r5, 0, 0.3927F, 0);
		cube_r5.setTextureOffset(38, 27).addBox(1.5F, 0, -6.4F, 1, 1, 4);

		layer7 = new ModelRenderer(this);
		layer7.setRotationPoint(0, 16, 0);
		

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0, 4, 1);
		layer7.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3927F, 0, 0);
		cube_r6.setTextureOffset(0, 24).addBox(-1.5F, -1.3F, 0, 1, 2, 1);
		cube_r6.setTextureOffset(0, 18).addBox(0.5F, -1.3F, 0, 1, 2, 1);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0, 4, -1);
		layer7.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.3927F, 0, 0);
		cube_r7.setTextureOffset(8, 24).addBox(-1.5F, -1.5F, 0, 1, 2, 1);
		cube_r7.setTextureOffset(19, 18).addBox(0.5F, -1.5F, 0, 1, 2, 1);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(2, 4, -3);
		layer7.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.3927F, 0, 0);
		cube_r8.setTextureOffset(20, 0).addBox(-1.5F, -2, 0, 1, 2, 1);
		cube_r8.setTextureOffset(0, 27).addBox(-3.5F, -2, 0, 1, 2, 1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.layer1.render(f5);
		this.layer2.render(f5);
		this.layer3.render(f5);
		this.layer4.render(f5);
		this.layer5.render(f5);
		this.layer6.render(f5);
		this.layer7.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}