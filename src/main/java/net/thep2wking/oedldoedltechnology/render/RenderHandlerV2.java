package net.thep2wking.oedldoedltechnology.render;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.android.IBioticStat;
import matteroverdrive.api.internal.ItemModelProvider;
import matteroverdrive.api.inventory.IBionicPart;
import matteroverdrive.api.renderer.IBionicPartRenderer;
import matteroverdrive.api.renderer.IBioticStatRenderer;
import matteroverdrive.api.starmap.IStarmapRenderRegistry;
import matteroverdrive.blocks.BlockDecorativeColored;
import matteroverdrive.client.ClientUtil;
import matteroverdrive.client.model.ModelTritaniumArmor;
import matteroverdrive.client.render.AndroidBionicPartRenderRegistry;
import matteroverdrive.client.render.AndroidStatRenderRegistry;
import matteroverdrive.client.render.DimensionalRiftsRender;
import matteroverdrive.client.render.IWorldLastRenderer;
import matteroverdrive.client.render.PipeRenderManager;
import matteroverdrive.client.render.RenderDialogSystem;
import matteroverdrive.client.render.RenderMatterScannerInfoHandler;
import matteroverdrive.client.render.RenderParticlesHandler;
import matteroverdrive.client.render.RenderWeaponsBeam;
import matteroverdrive.client.render.StarmapRenderRegistry;
import matteroverdrive.client.render.WeaponModuleModelRegistry;
import matteroverdrive.client.render.biostat.BioticStatRendererShield;
import matteroverdrive.client.render.biostat.BioticStatRendererTeleporter;
import matteroverdrive.client.render.entity.EntityRendererDrone;
import matteroverdrive.client.render.entity.EntityRendererFailedChicken;
import matteroverdrive.client.render.entity.EntityRendererFailedCow;
import matteroverdrive.client.render.entity.EntityRendererFailedPig;
import matteroverdrive.client.render.entity.EntityRendererFailedSheep;
import matteroverdrive.client.render.entity.EntityRendererMadScientist;
import matteroverdrive.client.render.entity.EntityRendererMutantScientist;
import matteroverdrive.client.render.entity.EntityRendererPhaserFire;
import matteroverdrive.client.render.entity.EntityRendererRangedRougeAndroid;
import matteroverdrive.client.render.entity.EntityRendererRougeAndroid;
import matteroverdrive.client.render.tileentity.TileEntityRendererAndroidStation;
import matteroverdrive.client.render.tileentity.TileEntityRendererContractMarket;
import matteroverdrive.client.render.tileentity.TileEntityRendererFusionReactorController;
import matteroverdrive.client.render.tileentity.TileEntityRendererGravitationalAnomaly;
import matteroverdrive.client.render.tileentity.TileEntityRendererGravitationalStabilizer;
import matteroverdrive.client.render.tileentity.TileEntityRendererHoloSign;
import matteroverdrive.client.render.tileentity.TileEntityRendererInscriber;
import matteroverdrive.client.render.tileentity.TileEntityRendererMatterPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererNetworkPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererPacketQueue;
import matteroverdrive.client.render.tileentity.TileEntityRendererPatterStorage;
import matteroverdrive.client.render.tileentity.TileEntityRendererPatternMonitor;
import matteroverdrive.client.render.tileentity.TileEntityRendererPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererReplicator;
import matteroverdrive.client.render.tileentity.TileEntityRendererStarMap;
import matteroverdrive.client.render.tileentity.TileEntityRendererWeaponStation;
import matteroverdrive.client.render.tileentity.starmap.StarMapRenderGalaxy;
import matteroverdrive.client.render.tileentity.starmap.StarMapRenderPlanetStats;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererPlanet;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererQuadrant;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererStar;
import matteroverdrive.client.render.weapons.ItemRenderPlasmaShotgun;
import matteroverdrive.client.render.weapons.ItemRendererIonSniper;
import matteroverdrive.client.render.weapons.ItemRendererOmniTool;
import matteroverdrive.client.render.weapons.ItemRendererPhaser;
import matteroverdrive.client.render.weapons.ItemRendererPhaserRifle;
import matteroverdrive.client.render.weapons.WeaponRenderHandler;
import matteroverdrive.client.render.weapons.layers.WeaponLayerAmmoRender;
import matteroverdrive.client.render.weapons.modules.ModuleHoloSightsRender;
import matteroverdrive.client.render.weapons.modules.ModuleSniperScopeRender;
import matteroverdrive.entity.EntityDrone;
import matteroverdrive.entity.EntityFailedChicken;
import matteroverdrive.entity.EntityFailedCow;
import matteroverdrive.entity.EntityFailedPig;
import matteroverdrive.entity.EntityFailedSheep;
import matteroverdrive.entity.EntityVillagerMadScientist;
import matteroverdrive.entity.android_player.AndroidPlayer;
import matteroverdrive.entity.monster.EntityMeleeRougeAndroidMob;
import matteroverdrive.entity.monster.EntityMutantScientist;
import matteroverdrive.entity.monster.EntityRangedRogueAndroidMob;
import matteroverdrive.entity.monster.EntityRougeAndroidMob;
import matteroverdrive.entity.player.MOPlayerCapabilityProvider;
import matteroverdrive.entity.weapon.PlasmaBolt;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveBlocks;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.init.OverdriveBioticStats;
import matteroverdrive.items.weapon.module.WeaponModuleColor;
import matteroverdrive.items.weapon.module.WeaponModuleHoloSights;
import matteroverdrive.items.weapon.module.WeaponModuleSniperScope;
import matteroverdrive.machines.fusionReactorController.TileEntityMachineFusionReactorController;
import matteroverdrive.machines.pattern_monitor.TileEntityMachinePatternMonitor;
import matteroverdrive.machines.pattern_storage.TileEntityMachinePatternStorage;
import matteroverdrive.machines.replicator.TileEntityMachineReplicator;
import matteroverdrive.starmap.data.Galaxy;
import matteroverdrive.starmap.data.Planet;
import matteroverdrive.starmap.data.Quadrant;
import matteroverdrive.starmap.data.Star;
import matteroverdrive.tile.TileEntityAndroidStation;
import matteroverdrive.tile.TileEntityGravitationalAnomaly;
import matteroverdrive.tile.TileEntityHoloSign;
import matteroverdrive.tile.TileEntityInscriber;
import matteroverdrive.tile.TileEntityMachineContractMarket;
import matteroverdrive.tile.TileEntityMachineGravitationalStabilizer;
import matteroverdrive.tile.TileEntityMachinePacketQueue;
import matteroverdrive.tile.TileEntityMachineStarMap;
import matteroverdrive.tile.TileEntityWeaponStation;
import matteroverdrive.util.MOLog;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.thep2wking.oedldoedltechnology.init.ModItems;
public class RenderHandlerV2 {
	public static final Function<ResourceLocation, TextureAtlasSprite> modelTextureBakeFunc = new Function<ResourceLocation, TextureAtlasSprite>() {
		@Nullable
		@Override
		public TextureAtlasSprite apply(@Nullable ResourceLocation input) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(input.toString());
		}
	};
	public static int stencilBuffer;
	private static ItemRendererPhaser rendererPhaser;
	private static ItemRendererRailgun rendererPhaserRifle;
	private static ItemRendererOmniTool rendererOmniTool;
	private static ItemRenderPlasmaShotgun renderPlasmaShotgun;
	private static ItemRendererIonSniper rendererIonSniper;
	private final Random random = new Random();
	private final WeaponLayerAmmoRender weaponLayerAmmoRender = new WeaponLayerAmmoRender();
	public EntityRendererRougeAndroid<EntityRougeAndroidMob> rendererRougeAndroidHologram;
	public ModelTritaniumArmor modelTritaniumArmor;
	public ModelTritaniumArmor modelTritaniumArmorFeet;
	public ModelBiped modelMeleeRogueAndroidParts;
	public ModelBiped modelRangedRogueAndroidParts;
	public IBakedModel doubleHelixModel;
	private RenderMatterScannerInfoHandler matterScannerInfoHandler;
	private RenderParticlesHandler renderParticlesHandler;
	private RenderWeaponsBeam renderWeaponsBeam;
	private List<IWorldLastRenderer> customRenderers;
	private AndroidStatRenderRegistry statRenderRegistry;
	private StarmapRenderRegistry starmapRenderRegistry;
	private RenderDialogSystem renderDialogSystem;
	private AndroidBionicPartRenderRegistry bionicPartRenderRegistry;
	private WeaponModuleModelRegistry weaponModuleModelRegistry;
	private PipeRenderManager pipeRenderManager;
	private DimensionalRiftsRender dimensionalRiftsRender;
	private WeaponRenderHandlerV2 weaponRenderHandler;

	private ModuleSniperScopeRender moduleSniperScopeRender;
	private ModuleHoloSightsRender moduleHoloSightsRender;

	private BioticStatRendererTeleporter rendererTeleporter;
	private BioticStatRendererShield biostatRendererShield;
	private StarMapRenderPlanetStats starMapRenderPlanetStats;
	private StarMapRenderGalaxy starMapRenderGalaxy;
	private StarMapRendererStar starMapRendererStar;
	private StarMapRendererQuadrant starMapRendererQuadrant;
	private StarMapRendererPlanet starMapRendererPlanet;
	private TileEntityRendererReplicator tileEntityRendererReplicator;
	private TileEntityRendererPipe tileEntityRendererPipe;
	private TileEntityRendererMatterPipe tileEntityRendererMatterPipe;
	private TileEntityRendererNetworkPipe tileEntityRendererNetworkPipe;
	private TileEntityRendererPatterStorage tileEntityRendererPatterStorage;
	private TileEntityRendererWeaponStation tileEntityRendererWeaponStation;
	private TileEntityRendererPatternMonitor tileEntityRendererPatternMonitor;
	private TileEntityRendererGravitationalAnomaly tileEntityRendererGravitationalAnomaly;
	private TileEntityRendererGravitationalStabilizer tileEntityRendererGravitationalStabilizer;
	private TileEntityRendererFusionReactorController tileEntityRendererFusionReactorController;
	private TileEntityRendererAndroidStation tileEntityRendererAndroidStation;
	private TileEntityRendererStarMap tileEntityRendererStarMap;
	private TileEntityRendererHoloSign tileEntityRendererHoloSign;
	private TileEntityRendererPacketQueue tileEntityRendererPacketQueue;
	private TileEntityRendererInscriber tileEntityRendererInscriber;
	private TileEntityRendererContractMarket tileEntityRendererContractMarket;

	public RenderHandlerV2() {
		customRenderers = new ArrayList<>();
		MinecraftForge.EVENT_BUS.register(this);
		weaponRenderHandler = new WeaponRenderHandlerV2();
		// moduleSniperScopeRender = new ModuleSniperScopeRender(weaponRenderHandler);
		// moduleHoloSightsRender = new ModuleHoloSightsRender(weaponRenderHandler);
	}

	public void init(World world, TextureManager textureManager) {
		// matterScannerInfoHandler = new RenderMatterScannerInfoHandler();
		// renderParticlesHandler = new RenderParticlesHandler(world, textureManager);
		// renderWeaponsBeam = new RenderWeaponsBeam();
		// statRenderRegistry = new AndroidStatRenderRegistry();
		// starmapRenderRegistry = new StarmapRenderRegistry();
		// renderDialogSystem = new RenderDialogSystem();
		// bionicPartRenderRegistry = new AndroidBionicPartRenderRegistry();
		// weaponModuleModelRegistry = new WeaponModuleModelRegistry();
		// pipeRenderManager = new PipeRenderManager();
		// dimensionalRiftsRender = new DimensionalRiftsRender();
		// addCustomRenderer(matterScannerInfoHandler);
		// addCustomRenderer(renderParticlesHandler);
		// addCustomRenderer(renderWeaponsBeam);
		// addCustomRenderer(renderDialogSystem);
		// addCustomRenderer(dimensionalRiftsRender);

		// MinecraftForge.EVENT_BUS.register(pipeRenderManager);
		// MinecraftForge.EVENT_BUS.register(weaponRenderHandler);
		// if (Minecraft.getMinecraft().getFramebuffer().enableStencil()) {
		// 	stencilBuffer = MinecraftForgeClient.reserveStencilBit();
		// }
	}

	@SubscribeEvent
	public void modelLoadEvent(ModelRegistryEvent event) {
		for (Item item : MatterOverdriveItems.items) {
			if (item instanceof ItemModelProvider)
				((ItemModelProvider) item).initItemModel();
		}
		for (Block block : MatterOverdriveBlocks.blocks) {
			if (block instanceof ItemModelProvider)
				((ItemModelProvider) block).initItemModel();
			else
				ClientUtil.registerWithMapper(block);
		}
	}

	// @SubscribeEvent
	// public void onRenderWorldLast(RenderWorldLastEvent event) {
	// 	// for (IWorldLastRenderer renderer : customRenderers) {
	// 	// 	renderer.onRenderWorldLast(this, event);
	// 	// }
	// 	for (IBioticStat stat : MatterOverdrive.STAT_REGISTRY.getStats()) {
	// 		Collection<IBioticStatRenderer> statRendererCollection = statRenderRegistry
	// 				.getRendererCollection(stat.getClass());
	// 		if (statRendererCollection != null) {
	// 			for (IBioticStatRenderer renderer : statRendererCollection) {
	// 				renderer.onWorldRender(stat, MOPlayerCapabilityProvider
	// 						.GetAndroidCapability(Minecraft.getMinecraft().player).getUnlockedLevel(stat), event);
	// 			}
	// 		}
	// 	}
	// }

	// Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (renderParticlesHandler != null)
			renderParticlesHandler.onClientTick(event);
	}

	// public void createTileEntityRenderers(ConfigurationHandler configHandler) {
	// 	tileEntityRendererReplicator = new TileEntityRendererReplicator();
	// 	tileEntityRendererPipe = new TileEntityRendererPipe();
	// 	tileEntityRendererMatterPipe = new TileEntityRendererMatterPipe();
	// 	tileEntityRendererNetworkPipe = new TileEntityRendererNetworkPipe();
	// 	tileEntityRendererPatterStorage = new TileEntityRendererPatterStorage();
	// 	tileEntityRendererWeaponStation = new TileEntityRendererWeaponStation();
	// 	tileEntityRendererPatternMonitor = new TileEntityRendererPatternMonitor();
	// 	tileEntityRendererGravitationalAnomaly = new TileEntityRendererGravitationalAnomaly();
	// 	tileEntityRendererGravitationalStabilizer = new TileEntityRendererGravitationalStabilizer();
	// 	tileEntityRendererFusionReactorController = new TileEntityRendererFusionReactorController();
	// 	tileEntityRendererAndroidStation = new TileEntityRendererAndroidStation();
	// 	tileEntityRendererStarMap = new TileEntityRendererStarMap();
	// 	tileEntityRendererHoloSign = new TileEntityRendererHoloSign();
	// 	tileEntityRendererPacketQueue = new TileEntityRendererPacketQueue();
	// 	tileEntityRendererInscriber = new TileEntityRendererInscriber();
	// 	tileEntityRendererContractMarket = new TileEntityRendererContractMarket();

	// 	configHandler.subscribe(tileEntityRendererAndroidStation);
	// 	configHandler.subscribe(tileEntityRendererWeaponStation);
	// }

	@SubscribeEvent
	public void onRenderPlayerPostEvent(RenderPlayerEvent.Post event) {
		// GL11.glEnable(GL11.GL_LIGHTING);
		// GL11.glColor3f(1, 1, 1);

		AndroidPlayer androidPlayer = MOPlayerCapabilityProvider.GetAndroidCapability(event.getEntity());
		if (androidPlayer != null && androidPlayer.isAndroid() && !event.getEntity().isInvisible()) {
			for (int i = 0; i < 5; i++) {
				ItemStack part = androidPlayer.getStackInSlot(i);
				if (part != null && part.getItem() instanceof IBionicPart) {
					IBionicPartRenderer renderer = bionicPartRenderRegistry
							.getRenderer(((IBionicPart) part.getItem()).getClass());
					if (renderer != null) {
						try {
							GlStateManager.pushMatrix();
							GlStateManager.enableBlend();
							renderer.renderPart(part, androidPlayer, event.getRenderer(), event.getPartialRenderTick());
							GlStateManager.popMatrix();
						} catch (Exception e) {
							MOLog.log(Level.ERROR, e, "An Error occurred while rendering bionic part");
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPreEvent(RenderPlayerEvent.Pre event) {
		// GL11.glEnable(GL11.GL_LIGHTING);
		// GL11.glColor3f(1, 1, 1);

		AndroidPlayer androidPlayer = MOPlayerCapabilityProvider.GetAndroidCapability(event.getEntity());
		if (androidPlayer != null && androidPlayer.isAndroid() && !event.getEntity().isInvisible()) {
			for (int i = 0; i < 5; i++) {
				ItemStack part = androidPlayer.getStackInSlot(i);
				if (part != null && part.getItem() instanceof IBionicPart) {
					IBionicPartRenderer renderer = bionicPartRenderRegistry
							.getRenderer(((IBionicPart) part.getItem()).getClass());
					if (renderer != null) {
						renderer.affectPlayerRenderer(part, androidPlayer, event.getRenderer(),
								event.getPartialRenderTick());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event) {
		if (event.getEntity() != null && AndroidPlayer.DISABLE_ANDROID_FOV) {
			AndroidPlayer androidPlayer = MOPlayerCapabilityProvider.GetAndroidCapability(event.getEntity());
			if (androidPlayer != null && androidPlayer.isAndroid()) {
				double attributeValue = (1 - androidPlayer.getSpeedMultiply()) / 2;
				event.setNewfov(event.getFov() + (float) attributeValue);
			}
		}
	}

	public void registerWeaponModuleRenders() {
		// weaponRenderHandler.addModuleRender(WeaponModuleSniperScope.class, moduleSniperScopeRender);
		// weaponRenderHandler.addModuleRender(WeaponModuleHoloSights.class, moduleHoloSightsRender);
	}

	public void registerWeaponLayers() {
		weaponRenderHandler.addWeaponLayer(weaponLayerAmmoRender);
	}

	// public void registerTileEntitySpecialRenderers() {
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReplicator.class, tileEntityRendererReplicator);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePatternStorage.class,
	// 			tileEntityRendererPatterStorage);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeaponStation.class, tileEntityRendererWeaponStation);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePatternMonitor.class,
	// 			tileEntityRendererPatternMonitor);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravitationalAnomaly.class,
	// 			tileEntityRendererGravitationalAnomaly);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGravitationalStabilizer.class,
	// 			tileEntityRendererGravitationalStabilizer);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFusionReactorController.class,
	// 			tileEntityRendererFusionReactorController);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAndroidStation.class, tileEntityRendererAndroidStation);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineStarMap.class, tileEntityRendererStarMap);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHoloSign.class, tileEntityRendererHoloSign);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePacketQueue.class, tileEntityRendererPacketQueue);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInscriber.class, tileEntityRendererInscriber);
	// 	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineContractMarket.class,
	// 			tileEntityRendererContractMarket);
	// }

	// public void registerBlockColors() {
	// 	Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, p_186720_2_, pos, tintIndex) -> {
	// 		EnumDyeColor color = state.getValue(BlockDecorativeColored.COLOR);
	// 		return ItemDye.DYE_COLORS[MathHelper.clamp(color.getMetadata(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 	}, MatterOverdrive.BLOCKS.decorative_tritanium_plate_colored);
	// 	Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, p_186720_2_, pos, tintIndex) -> {
	// 		EnumDyeColor color = state.getValue(BlockDecorativeColored.COLOR);
	// 		return ItemDye.DYE_COLORS[MathHelper.clamp(color.getMetadata(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 	}, MatterOverdrive.BLOCKS.decorative_floor_tile);
	// 	Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, p_186720_2_, pos, tintIndex) -> {
	// 		EnumDyeColor color = state.getValue(BlockDecorativeColored.COLOR);
	// 		return ItemDye.DYE_COLORS[MathHelper.clamp(color.getMetadata(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 	}, MatterOverdrive.BLOCKS.decorative_floor_tiles);
	// }

	public void createItemRenderers() {
		// rendererPhaser = new ItemRendererPhaser();
		rendererPhaserRifle = new ItemRendererRailgun();
		// rendererOmniTool = new ItemRendererOmniTool();
		// renderPlasmaShotgun = new ItemRenderPlasmaShotgun();
		// rendererIonSniper = new ItemRendererIonSniper();
	}

	public void activateItemRenderers() {
		// rendererPhaser.init();
		rendererPhaserRifle.init();
		// rendererOmniTool.init();
		// renderPlasmaShotgun.init();
		// rendererIonSniper.init();
	}

	public void bakeItemModels() {
		weaponRenderHandler.onModelBake(Minecraft.getMinecraft().getTextureMapBlocks(), this);
		// rendererPhaser.bakeModel();
		rendererPhaserRifle.bakeModel();
		// rendererOmniTool.bakeModel();
		// rendererIonSniper.bakeModel();
		// renderPlasmaShotgun.bakeModel();
	}

	public void registerModelTextures(TextureMap textureMap, OBJModel model) {
		model.getTextures().forEach(textureMap::registerSprite);
	}

	public OBJModel getObjModel(ResourceLocation location, ImmutableMap<String, String> customOptions) {
		try {
			OBJModel model = (OBJModel) OBJLoader.INSTANCE.loadModel(location);
			model = (OBJModel) model.process(customOptions);
			return model;
		} catch (Exception e) {
			MOLog.log(Level.ERROR, e, "There was a problem while baking %s model", location.getResourcePath());
		}
		return null;
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		// event.getModelRegistry().putObject(
		// 		new ModelResourceLocation(MatterOverdrive.ITEMS.phaser.getRegistryName(), "inventory"), rendererPhaser);
		event.getModelRegistry().putObject(
				new ModelResourceLocation(ModItems.RAILGUN.getRegistryName(), "inventory"),
				rendererPhaserRifle);
		// event.getModelRegistry().putObject(
		// 		new ModelResourceLocation(MatterOverdrive.ITEMS.omniTool.getRegistryName(), "inventory"),
		// 		rendererOmniTool);
		// event.getModelRegistry().putObject(
		// 		new ModelResourceLocation(MatterOverdrive.ITEMS.ionSniper.getRegistryName(), "inventory"),
		// 		rendererIonSniper);
		// event.getModelRegistry().putObject(
		// 		new ModelResourceLocation(MatterOverdrive.ITEMS.plasmaShotgun.getRegistryName(), "inventory"),
		// 		renderPlasmaShotgun);

		activateItemRenderers();
		bakeItemModels();
	}

	@SubscribeEvent
	public void onTextureStich(TextureStitchEvent.Pre event) {
		if (event.getMap() == Minecraft.getMinecraft().getTextureMapBlocks()) {
			weaponRenderHandler.onTextureStich(Minecraft.getMinecraft().getTextureMapBlocks(), this);
		}
	}

	// public void registerItemColors() {
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_HOLO_RED.getColor() : -1,
	// 			MatterOverdrive.ITEMS.energyPack);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_MATTER.getColor() : -1,
	// 			MatterOverdrive.ITEMS.battery);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_YELLOW_STRIPES.getColor() : -1,
	// 			MatterOverdrive.ITEMS.hc_battery);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_HOLO_RED.getColor() : -1,
	// 			MatterOverdrive.ITEMS.creative_battery);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_YELLOW_STRIPES.getColor() : -1,
	// 			MatterOverdrive.ITEMS.networkFlashDrive);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(
	// 			(stack, tintIndex) -> tintIndex == 1 ? Reference.COLOR_HOLO_GREEN.getColor() : -1,
	// 			MatterOverdrive.ITEMS.transportFlashDrive);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		switch (tintIndex) {
	// 		case 1:
	// 			return Reference.COLOR_YELLOW_STRIPES.getColor();
	// 		case 2:
	// 		case 3:
	// 			return Reference.COLOR_MATTER.getColor();
	// 		default:
	// 			return -1;
	// 		}
	// 	}, MatterOverdrive.ITEMS.matterContainer);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		if (tintIndex == 1 && !stack.isEmpty() && stack.getItem() != null) {
	// 			return WeaponModuleColor.colors[stack.getItemDamage()].getColor();
	// 		} else {
	// 			return 16777215;
	// 		}
	// 	}, MatterOverdrive.ITEMS.weapon_module_color);
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		if (tintIndex == 0 && !stack.isEmpty() && stack.getItem() != null) {
	// 			return ItemDye.DYE_COLORS[MathHelper.clamp(stack.getItemDamage(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 		} else {
	// 			return -1;
	// 		}
	// 	}, Item.getItemFromBlock(MatterOverdrive.BLOCKS.decorative_tritanium_plate_colored));
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		if (tintIndex == 0 && !stack.isEmpty() && stack.getItem() != null) {
	// 			return ItemDye.DYE_COLORS[MathHelper.clamp(stack.getItemDamage(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 		} else {
	// 			return -1;
	// 		}
	// 	}, Item.getItemFromBlock(MatterOverdrive.BLOCKS.decorative_floor_tile));
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		if (tintIndex == 0 && !stack.isEmpty() && stack.getItem() != null) {
	// 			return ItemDye.DYE_COLORS[MathHelper.clamp(stack.getItemDamage(), 0, ItemDye.DYE_COLORS.length - 1)];
	// 		} else {
	// 			return -1;
	// 		}
	// 	}, Item.getItemFromBlock(MatterOverdrive.BLOCKS.decorative_floor_tiles));
	// 	FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
	// 		if (tintIndex == 1 && stack.getItemDamage() == 0)
	// 			return 0xd00000;
	// 		else if (tintIndex == 1 && stack.getItemDamage() == 1)
	// 			return 0x019fea;
	// 		else if (tintIndex == 1 && stack.getItemDamage() == 2)
	// 			return 0xffe400;
	// 		return 0xffffff;
	// 	}, MatterOverdrive.ITEMS.androidPill);
	// }

	// public void createEntityRenderers(RenderManager renderManager) {
	// 	rendererRougeAndroidHologram = new EntityRendererRougeAndroid<>(renderManager, true);
	// }

	// public void registerEntityRenderers() {
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityMeleeRougeAndroidMob.class,
	// 			renderManager -> new EntityRendererRougeAndroid<>(renderManager, false));
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityVillagerMadScientist.class,
	// 			EntityRendererMadScientist::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityFailedPig.class, EntityRendererFailedPig::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityFailedCow.class, EntityRendererFailedCow::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityFailedChicken.class, EntityRendererFailedChicken::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityFailedSheep.class, EntityRendererFailedSheep::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(PlasmaBolt.class, EntityRendererPhaserFire::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityRangedRogueAndroidMob.class,
	// 			EntityRendererRangedRougeAndroid::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityMutantScientist.class,
	// 			EntityRendererMutantScientist::new);
	// 	RenderingRegistry.registerEntityRenderingHandler(EntityDrone.class, EntityRendererDrone::new);
	// }

	// public void createBioticStatRenderers() {
	// 	rendererTeleporter = new BioticStatRendererTeleporter();
	// 	biostatRendererShield = new BioticStatRendererShield();
	// }

	// public void registerBioticStatRenderers() {
	// 	statRenderRegistry.registerRenderer(OverdriveBioticStats.shield.getClass(), biostatRendererShield);
	// 	statRenderRegistry.registerRenderer(OverdriveBioticStats.teleport.getClass(), rendererTeleporter);
	// }

	// public void createStarmapRenderers() {
	// 	starMapRendererPlanet = new StarMapRendererPlanet();
	// 	starMapRendererQuadrant = new StarMapRendererQuadrant();
	// 	starMapRendererStar = new StarMapRendererStar();
	// 	starMapRenderGalaxy = new StarMapRenderGalaxy();
	// 	starMapRenderPlanetStats = new StarMapRenderPlanetStats();
	// }

	// public void registerStarmapRenderers() {
	// 	starmapRenderRegistry.registerRenderer(Planet.class, starMapRendererPlanet);
	// 	starmapRenderRegistry.registerRenderer(Quadrant.class, starMapRendererQuadrant);
	// 	starmapRenderRegistry.registerRenderer(Star.class, starMapRendererStar);
	// 	starmapRenderRegistry.registerRenderer(Galaxy.class, starMapRenderGalaxy);
	// 	starmapRenderRegistry.registerRenderer(Planet.class, starMapRenderPlanetStats);
	// }

	public void createModels() {
		modelTritaniumArmor = new ModelTritaniumArmor(0);
		modelTritaniumArmorFeet = new ModelTritaniumArmor(0.5f);
		modelMeleeRogueAndroidParts = new ModelBiped(0);
		modelRangedRogueAndroidParts = new ModelBiped(0, 0, 96, 64);
		try {
			IModel model = OBJLoader.INSTANCE
					.loadModel(new ResourceLocation(Reference.PATH_MODEL + "gui/double_helix.obj"));
			doubleHelixModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM,
					new Function<ResourceLocation, TextureAtlasSprite>() {
						@Nullable
						@Override
						public TextureAtlasSprite apply(@Nullable ResourceLocation input) {
							return Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(input);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RenderParticlesHandler getRenderParticlesHandler() {
		return renderParticlesHandler;
	}

	public TileEntityRendererStarMap getTileEntityRendererStarMap() {
		return tileEntityRendererStarMap;
	}

	public IAndroidStatRenderRegistry getStatRenderRegistry() {
		return statRenderRegistry;
	}

	public IStarmapRenderRegistry getStarmapRenderRegistry() {
		return starmapRenderRegistry;
	}

	public ItemRendererOmniTool getRendererOmniTool() {
		return rendererOmniTool;
	}

	public AndroidBionicPartRenderRegistry getBionicPartRenderRegistry() {
		return bionicPartRenderRegistry;
	}

	public WeaponModuleModelRegistry getWeaponModuleModelRegistry() {
		return weaponModuleModelRegistry;
	}

	public Random getRandom() {
		return random;
	}

	public void addCustomRenderer(IWorldLastRenderer renderer) {
		customRenderers.add(renderer);
	}

	public WeaponRenderHandlerV2 getWeaponRenderHandler() {
		return weaponRenderHandler;
	}
}
