package net.thep2wking.oedldoedltechnology.api.factory;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;

public class GuiFactoryBase extends GuiContainer {
	public TileFactoryBase tileEntity;

	public GuiFactoryBase(Container container, InventoryPlayer inventoryPlayer, TileFactoryBase tileEntity) {
		super(container);
		this.tileEntity = tileEntity;
		xSize = 176;
		ySize = 221;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		mc.getTextureManager().bindTexture(getBackgroundTexture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (tileEntity.isProcessing()) {
			int progress = Math.round(tileEntity.getProgress() * 20f);
			drawTexturedModalRect(guiLeft + 78, guiTop + 35, 176, 0, progress, 5);
		}
	}

	public void drawSmallText(String text, int x, int y) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRenderer.drawString(text, x * 2, y * 2, 0xffffff);
		GL11.glPopMatrix();
	}

	public void drawSmallText(String text, int x, int y, int color) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRenderer.drawString(text, x * 2, y * 2, color);
		GL11.glPopMatrix();
	}

	public void drawMediumText(String text, int x, int y) {
		GL11.glPushMatrix();
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		fontRenderer.drawString(text, (int) (x * 1.5f), (int) (y * 1.5f), 0xffffff);
		GL11.glPopMatrix();
	}

	public void drawMediumText(String text, int x, int y, int color) {
		GL11.glPushMatrix();
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		fontRenderer.drawString(text, (int) (x * 1.5f), (int) (y * 1.5f), color);
		GL11.glPopMatrix();
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		// energy
		String energy = String.valueOf(tileEntity.getEffectiveEnergy() + " FE");
		int textWidth = fontRenderer.getStringWidth(energy) / 2;
		int centerX = xSize / 2;
		drawSmallText(energy, (centerX - textWidth / 2), 60, 0xda943b);
		// clock speed header
		String clockSpeedHeader = String.valueOf("Clock Speed:");
		drawSmallText(clockSpeedHeader, 26, 101, 0x000);
		// clock speed
		String clockSpeed = String.valueOf(tileEntity.getShardPercentage() * 100 + "%");
		drawMediumText(clockSpeed, 23, 97, 0xda943b);
		// if (tileEntity.isProcessing()) {
			String progress = String.valueOf((int) (tileEntity.getProgress() * 100) + "%");
			int progressWidth = fontRenderer.getStringWidth(progress) / 2;
			drawSmallText(progress, (centerX - progressWidth / 2), 45);
		// } else {
		// 	String idle = String.valueOf("Idle");
		// 	int idleWidth = fontRenderer.getStringWidth(idle) / 2;
		// 	drawSmallText(idle, (centerX - idleWidth / 2), 45);
		// }
	}

	public void drawItemStack(ItemStack stack, int x, int y) {
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();
	}

	public ResourceLocation getBackgroundTexture() {
		return new ResourceLocation(OedldoedlTechnology.MODID,
				"textures/gui/factory/factory_" + tileEntity.getNumberOfInputs() + "_inputs.png");
	}
}