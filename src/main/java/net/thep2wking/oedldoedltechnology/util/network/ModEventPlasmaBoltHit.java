package net.thep2wking.oedldoedltechnology.util.network;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;

public class ModEventPlasmaBoltHit extends Event {
	public final ItemStack weapon;
	public final RayTraceResult hit;
	public final ModEntityPlasmaShotBase plasmaBolt;
	public final Side side;

	public ModEventPlasmaBoltHit(ItemStack weapon, RayTraceResult hit, ModEntityPlasmaShotBase plasmaBolt, Side side) {
		this.weapon = weapon;
		this.hit = hit;
		this.plasmaBolt = plasmaBolt;
		this.side = side;
	}
}