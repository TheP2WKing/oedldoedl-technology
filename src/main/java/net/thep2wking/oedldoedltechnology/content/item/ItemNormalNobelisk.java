package net.thep2wking.oedldoedltechnology.content.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModEntityNobeliskBase;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModItemNobeliskBase;
import net.thep2wking.oedldoedltechnology.entity.throwable.EntityNormalNobelisk;

public class ItemNormalNobelisk extends ModItemNobeliskBase {
	public ItemNormalNobelisk(String modid, String name, CreativeTabs tab, EnumRarity rarity, boolean hasEffect,
			int tooltipLines, int annotationLines) {
		super(modid, name, tab, rarity, hasEffect, tooltipLines, annotationLines);
	}

	@Override
	public ModEntityNobeliskBase setNobeliskEntity(World world, EntityPlayer player) {
		return new EntityNormalNobelisk(world, player);
	}
}