package net.thep2wking.oedldoedltechnology.util.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import matteroverdrive.Reference;
import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.inventory.IBionicPart;
import matteroverdrive.api.renderer.IBionicPartRenderer;
import matteroverdrive.client.model.ModelTritaniumArmor;
import matteroverdrive.client.render.AndroidBionicPartRenderRegistry;
import matteroverdrive.client.render.AndroidStatRenderRegistry;
import matteroverdrive.client.render.IWorldLastRenderer;
import matteroverdrive.client.render.RenderParticlesHandler;
import matteroverdrive.client.render.WeaponModuleModelRegistry;
import matteroverdrive.client.render.entity.EntityRendererRougeAndroid;
import matteroverdrive.client.render.weapons.layers.WeaponLayerAmmoRender;
import matteroverdrive.entity.android_player.AndroidPlayer;
import matteroverdrive.entity.monster.EntityRougeAndroidMob;
import matteroverdrive.entity.player.MOPlayerCapabilityProvider;
import matteroverdrive.util.MOLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModRenderHandler {
	public static final Function<ResourceLocation, TextureAtlasSprite> modelTextureBakeFunc = new Function<ResourceLocation, TextureAtlasSprite>() {
		@Override
		@SuppressWarnings("null")
		public TextureAtlasSprite apply(ResourceLocation input) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(input.toString());
		}
	};
	private static ItemRendererRailgun rendererRailgun;
	private static ItemRendererUpNAtomizer rendererUpNAtomizer;
	public static int stencilBuffer;
	private final Random random = new Random();
	private final WeaponLayerAmmoRender weaponLayerAmmoRender = new WeaponLayerAmmoRender();
	public EntityRendererRougeAndroid<EntityRougeAndroidMob> rendererRougeAndroidHologram;
	public ModelTritaniumArmor modelTritaniumArmor;
	public ModelTritaniumArmor modelTritaniumArmorFeet;
	public ModelBiped modelMeleeRogueAndroidParts;
	public ModelBiped modelRangedRogueAndroidParts;
	public IBakedModel doubleHelixModel;
	private RenderParticlesHandler renderParticlesHandler;
	private List<IWorldLastRenderer> customRenderers;
	private AndroidStatRenderRegistry statRenderRegistry;
	private AndroidBionicPartRenderRegistry bionicPartRenderRegistry;
	private WeaponModuleModelRegistry weaponModuleModelRegistry;
	private ModWeaponRenderHandler weaponRenderHandler;

	public ModRenderHandler() {
		customRenderers = new ArrayList<>();
		MinecraftForge.EVENT_BUS.register(this);
		weaponRenderHandler = new ModWeaponRenderHandler();
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (renderParticlesHandler != null)
			renderParticlesHandler.onClientTick(event);
	}

	@SubscribeEvent
	public void onRenderPlayerPostEvent(RenderPlayerEvent.Post event) {
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
	}

	public void registerWeaponLayers() {
		weaponRenderHandler.addWeaponLayer(weaponLayerAmmoRender);
	}

	public void createItemRenderers() {
		rendererRailgun = new ItemRendererRailgun();
		rendererUpNAtomizer = new ItemRendererUpNAtomizer();
	}

	public void activateItemRenderers() {
		rendererRailgun.init();
		rendererUpNAtomizer.init();
	}

	public void bakeItemModels() {
		weaponRenderHandler.onModelBake(Minecraft.getMinecraft().getTextureMapBlocks(), this);
		rendererRailgun.bakeModel();
		rendererUpNAtomizer.bakeModel();
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
		event.getModelRegistry().putObject(new ModelResourceLocation(ModItems.RAILGUN.getRegistryName(), "inventory"), rendererRailgun);
		event.getModelRegistry().putObject(new ModelResourceLocation(ModItems.UP_N_ATOMIZER.getRegistryName(), "inventory"), rendererUpNAtomizer);
		activateItemRenderers();
		bakeItemModels();
	}

	@SubscribeEvent
	public void onTextureStich(TextureStitchEvent.Pre event) {
		if (event.getMap() == Minecraft.getMinecraft().getTextureMapBlocks()) {
			weaponRenderHandler.onTextureStich(Minecraft.getMinecraft().getTextureMapBlocks(), this);
		}
	}

	public void createModels() {
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

	public void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(ModEntityPlasmaShotBase.class, ModEntityRenderPhaserFire::new);
	}

	public RenderParticlesHandler getRenderParticlesHandler() {
		return renderParticlesHandler;
	}

	public IAndroidStatRenderRegistry getStatRenderRegistry() {
		return statRenderRegistry;
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

	public ModWeaponRenderHandler getWeaponRenderHandler() {
		return weaponRenderHandler;
	}
}
