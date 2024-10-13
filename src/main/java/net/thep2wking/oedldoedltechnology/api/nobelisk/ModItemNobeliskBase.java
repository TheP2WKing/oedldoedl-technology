package net.thep2wking.oedldoedltechnology.api.nobelisk;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;

public abstract class ModItemNobeliskBase extends ModItemBase {
	public ModItemNobeliskBase(String modid, String name, CreativeTabs tab, EnumRarity rarity, boolean hasEffect,
			int tooltipLines, int annotationLines) {
		super(modid, name, tab, rarity, hasEffect, tooltipLines, annotationLines);
		setMaxStackSize(16);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!playerIn.capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
				SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote) {
			ModEntityNobeliskBase entity = this.setNobeliskEntity(worldIn, playerIn);
			entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.8F, 1.0F);
			worldIn.spawnEntity(entity);
		}
		playerIn.swingArm(handIn);
		playerIn.addStat(StatList.getObjectUseStats(this));
		playerIn.getCooldownTracker().setCooldown(this, 30);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	public abstract ModEntityNobeliskBase setNobeliskEntity(World world, EntityPlayer player);
}