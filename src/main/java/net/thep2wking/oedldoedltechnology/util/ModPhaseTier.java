package net.thep2wking.oedldoedltechnology.util;

import net.minecraft.item.EnumRarity;
import net.thep2wking.oedldoedlcore.util.ModRarities;

/**
 * @author TheP2WKing
 */
public enum ModPhaseTier {
    TIER_0("tier0", ModRarities.WHITE),
    TIER_1("tier1", ModRarities.WHITE),
    TIER_2("tier2", ModRarities.GREEN),
    TIER_3("tier3", ModRarities.GREEN),
    TIER_4("tier4", ModRarities.YELLOW),
    TIER_5("tier5", ModRarities.GOLD),
    TIER_6("tier6", ModRarities.AQUA),
	TIER_7("tier7", ModRarities.LIGHT_PURPLE),
	TIER_8("tier8", ModRarities.LIGHT_PURPLE),
	TIER_9("tier9", ModRarities.RED),
    ;

    private String tier;
	private EnumRarity color;

    ModPhaseTier(String tier, EnumRarity color) {
        this.tier = tier;
		this.color = color;
    }

    public String getTier() {
        return tier;
    }

	public EnumRarity getRarityColor() {
		return color;
	}
}