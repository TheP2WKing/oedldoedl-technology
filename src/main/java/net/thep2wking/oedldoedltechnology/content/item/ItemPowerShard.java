package net.thep2wking.oedldoedltechnology.content.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.api.item.ModItemBase;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedlcore.util.ModTooltips;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;

public class ItemPowerShard extends ModItemBase {
    public ItemPowerShard(String modid, String name, CreativeTabs tab, EnumRarity rarity, boolean hasEffect,
            int tooltipLines, int annotationLines) {
        super(modid, name, tab, rarity, hasEffect, tooltipLines, annotationLines);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (ModTooltips.showAnnotationTip()) {
            tooltip.add(CoreConfig.TOOLTIPS.COLORS.INFORMATION_ANNOTATION_FORMATTING.getColor()
                    + I18n.format(this.getUnlocalizedName() + ".annotation1") + " " + TextFormatting.YELLOW
                    + String.format("%.1f%%",
                            TechnologyConfig.CONTENT.FACTORY.POWER_SHARD_SPEED_AND_POWER_INCREASE * 100));
        }
        if (ModTooltips.showInfoTip()) {
            for (int i = 1; i <= tooltipLines; ++i) {
                ModTooltips.addInformation(tooltip, this.getUnlocalizedName(), i);
            }
        } else if (ModTooltips.showInfoTipKey() && !(tooltipLines == 0)) {
            ModTooltips.addKey(tooltip, ModTooltips.KEY_INFO);
        }
    }
}