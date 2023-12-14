package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModItemOBJRenderBase;

public class ItemRendererUpNAtomizer extends ModItemOBJRenderBase {
	public ItemRendererUpNAtomizer() {
		super(OedldoedlTechnology.MODID, "up_n_atomizer.obj");
	}

	@Override
	public void renderHand(RenderPlayer renderPlayer) {
		renderPlayer.renderRightArm(Minecraft.getMinecraft().player);
	}

	@Override
	public void transformHand(float recoilValue, float zoomValue) {
		transformRecoil(recoilValue, zoomValue);
		GlStateManager.translate(0.145, -0.02, -0.15);
		GlStateManager.rotate(3, 0, 1, 0);
		GlStateManager.rotate(105, 1, 0, 0);
		GlStateManager.scale(0.4, 0.4, 0.4);
	}

	@Override
	public float getHorizontalSpeed() {
		return 0.1f;
	}
}