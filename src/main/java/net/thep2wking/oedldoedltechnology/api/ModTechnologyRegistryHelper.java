package net.thep2wking.oedldoedltechnology.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.util.ModLogger;

public class ModTechnologyRegistryHelper {
    // normal models for obj weapons
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event, String modId) {
        ModLogger.registeredModelsLogger(modId);
        for (Item item : ForgeRegistries.ITEMS.getValuesCollection()) {
            if (item.getRegistryName().getResourceDomain().equals(modId) && item instanceof ModItemEnergyWeaponBase) {
                ModelLoader.setCustomModelResourceLocation(item, 0,
                        new ModelResourceLocation(item.getRegistryName(), "normal"));
            } else if (item.getRegistryName().getResourceDomain().equals(modId)
                    && !(item instanceof ModItemEnergyWeaponBase)) {
                ModelLoader.setCustomModelResourceLocation(item, 0,
                        new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}