package net.thep2wking.oedldoedltechnology.integration;

import jeresources.api.IJERAPI;
import jeresources.api.conditionals.LightLevel;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.entity.living.EntityRepublicanSpaceRanger;

public class JERPlugin {
    @jeresources.api.JERPlugin
    public static IJERAPI api;

    public static void register() {
        if (api == null) {
            return;
        }

        if (TechnologyConfig.INTEGRATION.JER.REPUBLICAN_SPACE_RANGER_MOB_DROPS) {
            api.getMobRegistry().register(new EntityRepublicanSpaceRanger(api.getWorld()), LightLevel.hostile, 20,
                    EntityRepublicanSpaceRanger.LOOTTABLE);
        }
    }
}