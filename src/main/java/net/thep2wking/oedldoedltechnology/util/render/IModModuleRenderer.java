package net.thep2wking.oedldoedltechnology.util.render;

import matteroverdrive.client.resources.data.WeaponMetadataSection;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;

public interface IModModuleRenderer {
	void renderModule(WeaponMetadataSection var1, ItemStack var2, ItemStack var3, float var4);

	void transformWeapon(WeaponMetadataSection var1, ItemStack var2, ItemStack var3, float var4, float var5);

	void onModelBake(TextureMap var1, ModRenderHandler var2);

	void onTextureStich(TextureMap var1, ModRenderHandler var2);
}