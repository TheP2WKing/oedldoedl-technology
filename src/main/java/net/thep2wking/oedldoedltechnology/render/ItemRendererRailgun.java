package net.thep2wking.oedldoedltechnology.render;

import matteroverdrive.Reference;
import matteroverdrive.client.render.weapons.WeaponItemRenderer;
import net.minecraft.util.ResourceLocation;

public class ItemRendererRailgun extends WeaponItemRenderer {
	public static final String MODEL = "oedldoedltechnology:models/"+ "item/railgun.obj";
	public static final String FLASH_TEXTURE = Reference.PATH_FX + "phaser_rifle_flash.png";

	public static ResourceLocation flashTexture;

	public ItemRendererRailgun() {
		super(new ResourceLocation(MODEL));
		flashTexture = new ResourceLocation(FLASH_TEXTURE);
	}
}
