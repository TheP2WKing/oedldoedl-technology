package net.thep2wking.oedldoedltechnology.util;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.client.render.weapons.WeaponItemRenderer;
import matteroverdrive.client.render.weapons.WeaponRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class RenderRailgun extends WeaponItemRenderer {
	public static final String MODEL = Reference.PATH_MODEL + "item/ion_sniper.obj";


	public RenderRailgun() {
		super(new ResourceLocation(MODEL));
	}
}
