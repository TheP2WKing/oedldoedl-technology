package net.thep2wking.oedldoedltechnology.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thep2wking.oedldoedlcore.util.ModReferences;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.categories.Content;
import net.thep2wking.oedldoedltechnology.config.categories.Integration;
import net.thep2wking.oedldoedltechnology.config.categories.Properties;
import net.thep2wking.oedldoedltechnology.config.categories.Recipes;
import net.thep2wking.oedldoedltechnology.config.categories.Tooltips;
import net.thep2wking.oedldoedltechnology.config.categories.World;

@Config(modid = OedldoedlTechnology.MODID, name = ModReferences.BASE_MODID + "/"
        + OedldoedlTechnology.MODID, category = OedldoedlTechnology.MODID)
public class TechnologyConfig {
    @Config.Name("content")
    public static final Content CONTENT = new Content();

    @Config.Name("recipes")
    public static final Recipes RECIPES = new Recipes();
    
    @Config.Name("tooltips")
    public static final Tooltips TOOLTIPS = new Tooltips();

    @Config.Name("integration")
    public static final Integration INTEGRATION = new Integration();

    @Config.Name("properties")
    public static final Properties PROPERTIES = new Properties();

    @Config.Name("world")
    public static final World WORLD = new World();

    @Mod.EventBusSubscriber
    public static class ConfigHolder {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(OedldoedlTechnology.MODID)) {
                ConfigManager.sync(OedldoedlTechnology.MODID, Config.Type.INSTANCE);
            }
        }
    }
}