package net.thep2wking.oedldoedltechnology;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.api.tab.ModOedldoedlTabBase;
import net.thep2wking.oedldoedlcore.init.ModItems;
import net.thep2wking.oedldoedlcore.util.ModLogInUtil;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.content.assembler.recipe.ModAssemblerRecipes;
import net.thep2wking.oedldoedltechnology.content.constructor.recipe.ModConstructorRecipes;
import net.thep2wking.oedldoedltechnology.init.ModEntities;
import net.thep2wking.oedldoedltechnology.registry.ModRecipes;
import net.thep2wking.oedldoedltechnology.registry.ModRegistry;
import net.thep2wking.oedldoedltechnology.util.handler.GuiHandler;
import net.thep2wking.oedldoedltechnology.util.handler.ModWeaponFactory;
import net.thep2wking.oedldoedltechnology.util.network.ModPacketPipeline;
import net.thep2wking.oedldoedltechnology.util.proxy.CommonProxy;
import net.thep2wking.oedldoedltechnology.util.world.ModStructureGen;

@Mod(modid = OedldoedlTechnology.MODID, name = OedldoedlTechnology.NAME, version = OedldoedlTechnology.VERSION, dependencies = OedldoedlTechnology.DEPENDENCIES)
public class OedldoedlTechnology {
    public static final String MODID = "oedldoedltechnology";
    public static final String PREFIX = MODID + ":";
    public static final String MC_VERSION = "1.12.2";
    public static final String NAME = "Oedldoedl Technology";
    public static final String VERSION = MC_VERSION + "-" + "4.2.0";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.5.2847,);required-after:oedldoedlcore@[1.12.2-4.2.0,);required-after:oedldoedlresources@[1.12.2-4.2.0,);required-after:matteroverdrive@[0.7,);";
    public static final String CLIENT_PROXY_CLASS = "net.thep2wking.oedldoedltechnology.util.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "net.thep2wking.oedldoedltechnology.util.proxy.ServerProxy";
    public static final ModPacketPipeline NETWORK = new ModPacketPipeline();
    public static final ModWeaponFactory WEAPON_FACTORY = new ModWeaponFactory();

    @Instance
    public static OedldoedlTechnology INSTANCE;

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy PROXY;

    public static final CreativeTabs TAB = new ModOedldoedlTabBase(MODID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.TECHNOLOGY_ICON);
        };
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModLogger.preInitLogger(MODID);
        ModEntities.registerEntities();
        ModRegistry.registerTiles();
        NetworkRegistry.INSTANCE.registerGuiHandler(OedldoedlTechnology.INSTANCE, new GuiHandler());
        ModConstructorRecipes.register();
        ModAssemblerRecipes.register();
        NETWORK.registerPackets();
        PROXY.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModLogger.initLogger(MODID);
        ModRecipes.registerOreDict();
        ModRecipes.registerRecipes();
        ModStructureGen.registerWorldGenerators();
        ModRegistry.registerLootTables();
        WEAPON_FACTORY.initModules();
        WEAPON_FACTORY.initWeapons();
        PROXY.render();
        PROXY.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModLogger.postInitLogger(MODID);
        PROXY.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        ModLogger.loadCompleteLogger(MODID, VERSION);
    }

    @Mod.EventBusSubscriber
    public static class ModJoinMessage {
        @SubscribeEvent
        public static void addJoinMessage(PlayerLoggedInEvent event) {
            ModLogInUtil.addJoinMessage(event, NAME, MODID, VERSION, true);
        }
    }
}