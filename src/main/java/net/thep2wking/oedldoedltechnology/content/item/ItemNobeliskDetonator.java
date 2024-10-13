package net.thep2wking.oedldoedltechnology.content.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.util.ModTooltips;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModEntityNobeliskBase;
import net.thep2wking.oedldoedltechnology.init.ModSounds;

public class ItemNobeliskDetonator extends ModItemBase {
    public ItemNobeliskDetonator(String modid, String name, CreativeTabs tab, EnumRarity rarity, boolean hasEffect,
            int tooltipLines, int annotationLines) {
        super(modid, name, tab, rarity, hasEffect, tooltipLines, annotationLines);
        setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("active"), (stack, world, entity) -> {
            return stack.hasTagCompound() && stack.getTagCompound().getBoolean("active") ? 1.0F : 0.0F;
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem() || oldStack.hasEffect() != newStack.hasEffect();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            List<ModEntityNobeliskBase> nobelisks = worldIn.getEntities(ModEntityNobeliskBase.class,
                    entity -> entity.getThrowerUUID() != null
                            && entity.getThrowerUUID().equals(playerIn.getUniqueID().toString()));
            int detonatedCount = 0;
            for (ModEntityNobeliskBase nobelisk : nobelisks) {
                nobelisk.setDead();
                nobelisk.getDetonationEffect(worldIn, nobelisk.getPosition());
                detonatedCount++;
            }
            ModTooltips.sendItemInfoChatComponent(playerIn, stack, detonatedCount + " Detonated", TextFormatting.RED);
        }
        worldIn.playSound(null, playerIn.getPosition(), ModSounds.DETONATE, SoundCategory.PLAYERS, 1.5f, 1.0f);
        playerIn.swingArm(handIn);
        playerIn.addStat(StatList.getObjectUseStats(this));
        playerIn.getCooldownTracker().setCooldown(this, 10);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            if (player.getCooldownTracker().hasCooldown(this)) {
                nbt.setBoolean("active", true);
            } else {
                nbt.setBoolean("active", false);
            }
        }
    }
}