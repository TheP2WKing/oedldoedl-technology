package net.thep2wking.oedldoedltechnology.content.constructor;

import net.minecraft.entity.player.InventoryPlayer;
import net.thep2wking.oedldoedltechnology.api.factory.GuiFactoryBase;
import net.thep2wking.oedldoedltechnology.api.factory.TileFactoryBase;

public class GuiConstructor extends GuiFactoryBase {
	public GuiConstructor(InventoryPlayer inventoryPlayer, TileFactoryBase tileEntity) {
		super(new ContainerConstructor(inventoryPlayer, tileEntity), inventoryPlayer, tileEntity);
	}

	// @Override
	// public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	// 	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	// 	// TileAssembler tileAssembler = (TileAssembler) tileEntity;
	// 	// ItemStack resultStack = tileAssembler.getOutputItemForDisplay();
	// 	// if (resultStack != null) {
	// 	// 	drawItemStack(resultStack, 80, 17);
	// 	// }

	// 	// String firstItem = tileAssembler.getInputCount1();
	// 	// drawMediumText(firstItem, 28, 32, 0x000);

	// 	// String secondItem = tileAssembler.getInputCount2();
	// 	// drawMediumText(secondItem, 28, 49, 0x000);

	// 	// String outputItem = tileAssembler.getOutputCount();
	// 	// drawMediumText(outputItem, 121, 40, 0x000);
	// }
}