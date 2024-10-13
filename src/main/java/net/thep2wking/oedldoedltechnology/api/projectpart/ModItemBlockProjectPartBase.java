package net.thep2wking.oedldoedltechnology.api.projectpart;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.api.item.ModItemBlockBase;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedlcore.util.ModTooltips;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.util.ModPhaseTier;

public class ModItemBlockProjectPartBase extends ModItemBlockBase {
	public final ModPhaseTier tier;
	
	public ModItemBlockProjectPartBase(Block block, ModPhaseTier tier, boolean hasEffect, int tooltipLines) {
		super(block, tier.getRarityColor(), hasEffect, tooltipLines, 1);
		this.tier = tier;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (ModTooltips.showAnnotationTip()) {
			tooltip.add(CoreConfig.TOOLTIPS.COLORS.INFORMATION_ANNOTATION_FORMATTING.getColor()
					+ I18n.format("item." + OedldoedlTechnology.MODID + ".project_part" + ".annotation1") + " "
					+ this.tier.getRarityColor().getColor()
					+ I18n.format("item." + OedldoedlTechnology.MODID + ".project_part." + this.tier.getTier()));
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