package net.thep2wking.oedldoedltechnology.api;

import matteroverdrive.client.render.weapons.WeaponItemRenderer;
import net.minecraft.util.ResourceLocation;

public class ModItemOBJRenderBase extends WeaponItemRenderer {
	public ModItemOBJRenderBase(String modid, String obj) {
		super(new ResourceLocation(modid, "models/item/" + obj));
	}
}