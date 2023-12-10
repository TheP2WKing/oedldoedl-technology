package net.thep2wking.oedldoedltechnology.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.client.render.weapons.WeaponItemRenderer;
import matteroverdrive.client.render.weapons.layers.IWeaponLayer;
import matteroverdrive.client.resources.data.WeaponMetadataSection;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.util.MOInventoryHelper;
import matteroverdrive.util.RenderUtils;
import matteroverdrive.util.WeaponHelper;
import matteroverdrive.util.animation.MOEasing.Quad;
import matteroverdrive.util.animation.MOEasing.Sine;
import matteroverdrive.util.math.MOMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.util.ModLogger;
import net.thep2wking.oedldoedltechnology.handler.ClientWeaponHandlerV2;

import org.lwjgl.util.glu.Project;

@SideOnly(Side.CLIENT)
@SuppressWarnings("all")
public class WeaponRenderHandlerV2 {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Map<Class<? extends IWeaponModule>, IModuleRenderV2> moduleRenders = new HashMap();
	private final List<IWeaponLayer> weaponLayers = new ArrayList();

	public WeaponRenderHandlerV2() {
	}

	@SubscribeEvent
	public void onHandRender(RenderSpecificHandEvent event) {
		ItemStack weapon = event.getItemStack();
		if (event.getHand() == EnumHand.MAIN_HAND && !weapon.isEmpty() && weapon.getItem() instanceof EnergyWeapon) {
			event.setCanceled(true);
			GlStateManager.clear(256);
			EntityRenderer entityRenderer = this.mc.entityRenderer;
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			float f = 0.07F;
			Project.gluPerspective(35.0F, (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F,
					(float) (this.mc.gameSettings.renderDistanceChunks * 32));
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			GlStateManager.pushMatrix();
			this.hurtCameraEffect(event.getPartialTicks());
			if (this.mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(event.getPartialTicks());
			}
			boolean flag = this.mc.getRenderViewEntity() instanceof EntityLivingBase
					&& ((EntityLivingBase) this.mc.getRenderViewEntity()).isPlayerSleeping();
			if (this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI
					&& !this.mc.playerController.isSpectator()) {
				float zoomValue = Sine.easeInOut(ClientWeaponHandlerV2.ZOOM_TIME, 0.0F, 1.0F, 1.0F);
				float recoilValue = Quad.easeInOut(ClientWeaponHandlerV2.RECOIL_TIME, 0.0F, 1.0F, 1.0F)
						* MathHelper.clamp(ClientWeaponHandlerV2.RECOIL_AMOUNT, 0.0F, 20.0F);
				this.transformFirstPerson(zoomValue);
				WeaponItemRenderer model = this.getWeaponModel(weapon);
				if (model != null) {
					EntityPlayerSP player = this.mc.player;
					player.getSwingProgress(event.getPartialTicks());
					float f2 = player.prevRotationPitch
							+ (player.rotationPitch - player.prevRotationPitch) * event.getPartialTicks();
					float f3 = player.prevRotationYaw
							+ (player.rotationYaw - player.prevRotationYaw) * event.getPartialTicks();
					this.rotateAroundXAndY(f2, f3);
					this.setLightmap(player);
					this.rotateArm(model, player, event.getPartialTicks());
					GlStateManager.enableRescaleNormal();
					entityRenderer.enableLightmap();
					Render<AbstractClientPlayer> render = Minecraft.getMinecraft().getRenderManager()
							.getEntityRenderObject(Minecraft.getMinecraft().player);
					if (render instanceof RenderPlayer) {
						GlStateManager.pushMatrix();
						RenderUtils.bindTexture(player.getLocationSkin());
						model.transformHand(recoilValue, zoomValue);
						model.renderHand((RenderPlayer) render);
						GlStateManager.popMatrix();
					}
					List<ItemStack> modules = MOInventoryHelper.getStacks(weapon);
					this.transformFromModules(modules, model.getWeaponMetadata(), weapon, event.getPartialTicks(),
							zoomValue);
					model.transformFirstPersonWeapon((EnergyWeapon) weapon.getItem(), weapon, zoomValue, recoilValue);
					this.renderWeaponAndModules(modules, model, weapon, event.getPartialTicks());
					this.renderLayers(model.getWeaponMetadata(), weapon, event.getPartialTicks());
					entityRenderer.disableLightmap();
				}
				RenderHelper.disableStandardItemLighting();
			}
			GlStateManager.popMatrix();
			if (this.mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(event.getPartialTicks());
			}
		} else if (event.getHand() == EnumHand.OFF_HAND && !weapon.isEmpty()
				&& weapon.getItem() instanceof EnergyWeapon) {
			event.setCanceled(true);
		}
	}

	public WeaponItemRenderer getWeaponModel(ItemStack weaponStack) {
		IBakedModel bakedModel = this.mc.getRenderItem().getItemModelMesher().getItemModel(weaponStack);
		return bakedModel instanceof WeaponItemRenderer ? (WeaponItemRenderer) bakedModel : null;
	}

	public void renderWeaponAndModules(List<ItemStack> modules, WeaponItemRenderer model, ItemStack weapon,
			float partialTicks) {
		this.renderWeapon(model, weapon);
		this.renderModules(modules, model.getWeaponMetadata(), weapon, partialTicks);
	}

	public void onModelBake(TextureMap textureMap, RenderHandlerV2 renderHandler) {
		Iterator var3 = this.moduleRenders.values().iterator();
		while (var3.hasNext()) {
			IModuleRenderV2 render = (IModuleRenderV2) var3.next();
			render.onModelBake(textureMap, renderHandler);
		}
	}

	public void onTextureStich(TextureMap textureMap, RenderHandlerV2 renderHandler) {
		Iterator var3 = this.moduleRenders.values().iterator();
		while (var3.hasNext()) {
			IModuleRenderV2 render = (IModuleRenderV2) var3.next();
			render.onTextureStich(textureMap, renderHandler);
		}
	}

	private void rotateAroundXAndY(float angleX, float angleY) {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(angleX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	private void setLightmap(EntityPlayerSP player) {
		int i = this.mc.world.getCombinedLight(
				new BlockPos(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ), 0);
		float f = (float) (i & '\uffff');
		float f1 = (float) (i >> 16);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
	}

	private void rotateArm(WeaponItemRenderer weaponItemRenderer, EntityPlayerSP player, float partialTicks) {
		float f = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partialTicks;
		float f1 = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partialTicks;
		GlStateManager.rotate((player.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((player.rotationYaw - f1) * weaponItemRenderer.getHorizontalSpeed(), 0.0F, 1.0F, 0.0F);
	}

	private void renderWeapon(WeaponItemRenderer model, ItemStack weapon) {
		this.renderModel(model, weapon);
	}

	private void transformFromModules(List<ItemStack> modules, WeaponMetadataSection weaponMeta, ItemStack weapon,
			float ticks, float zoomValue) {
		if (modules != null) {
			Iterator var6 = modules.iterator();
			while (var6.hasNext()) {
				ItemStack module = (ItemStack) var6.next();
				IModuleRenderV2 render = (IModuleRenderV2) this.moduleRenders.get(module.getItem().getClass());
				if (render != null) {
					render.transformWeapon(weaponMeta, weapon, module, ticks, zoomValue);
				}
			}
		}

	}

	private void renderModules(List<ItemStack> modules, WeaponMetadataSection weaponMeta, ItemStack weapon,
			float ticks) {
		if (modules != null) {
			Iterator var5 = modules.iterator();
			while (var5.hasNext()) {
				ItemStack module = (ItemStack) var5.next();
				IModuleRenderV2 render = (IModuleRenderV2) this.moduleRenders.get(module.getItem().getClass());
				if (render != null) {
					GlStateManager.pushMatrix();
					render.renderModule(weaponMeta, weapon, module, ticks);
					GlStateManager.popMatrix();
				}
			}
		}
	}

	private void renderLayers(WeaponMetadataSection weaponMeta, ItemStack weapon, float ticks) {
		Iterator var4 = this.weaponLayers.iterator();
		while (var4.hasNext()) {
			IWeaponLayer layer = (IWeaponLayer) var4.next();
			layer.renderLayer(weaponMeta, weapon, ticks);
		}
	}

	public void renderModel(IBakedModel model, ItemStack weapon) {
		if (model != null) {
			RenderUtils.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			worldrenderer.begin(7, DefaultVertexFormats.ITEM);
			EnumFacing[] var5 = EnumFacing.values();
			int var6 = var5.length;
			for (int var7 = 0; var7 < var6; ++var7) {
				EnumFacing enumfacing = var5[var7];
				this.renderQuads(worldrenderer, model.getQuads((IBlockState) null, enumfacing, 0L), -1, weapon);
			}
			this.renderQuads(worldrenderer, model.getQuads((IBlockState) null, (EnumFacing) null, 0L),
					WeaponHelper.getColor(weapon), weapon);
			tessellator.draw();
		}
	}

	public IBakedModel getItemModel(ItemStack weapon) {
		return this.mc.getRenderItem().getItemModelMesher().getItemModel(weapon);
	}

	private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack) {
		boolean flag = color == -1 && !stack.isEmpty();
		int i = 0;
		for (int j = quads.size(); i < j; ++i) {
			BakedQuad bakedquad = (BakedQuad) quads.get(i);
			int k = color;
			if (flag && bakedquad.hasTintIndex()) {
				k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());
				if (EntityRenderer.anaglyphEnable) {
					k = TextureUtil.anaglyphColor(k);
				}

				k |= -16777216;
			}
			LightUtil.renderQuadColorSlow(renderer, bakedquad, k);
		}
	}

	@SubscribeEvent
	public void handleCameraRecoil(EntityViewRenderEvent.CameraSetup event) {
		event.setRoll(event.getRoll()
				+ ClientWeaponHandlerV2.CAMERA_RECOIL_AMOUNT * ClientWeaponHandlerV2.CAMERA_RECOIL_TIME);
		event.setPitch(event.getPitch() + Math.abs(ClientWeaponHandlerV2.CAMERA_RECOIL_AMOUNT)
				* ClientWeaponHandlerV2.CAMERA_RECOIL_TIME * 0.5F);
	}

	private void transformFirstPerson(float zoomValue) {
		GlStateManager.translate((double) MOMathHelper.Lerp(0.13F, 0.0F, zoomValue), -0.18, -0.55);
		GlStateManager.rotate(MOMathHelper.Lerp(185.0F, 180.0F, zoomValue), 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(MOMathHelper.Lerp(3.0F, 0.0F, zoomValue), -1.0F, 0.0F, 0.0F);
		GlStateManager.scale(1.0, 1.0, 0.8);
	}

	private void setupViewBobbing(float partialTicks) {
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
			float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
			float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
			float f2 = entityplayer.prevCameraYaw
					+ (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
			float f3 = entityplayer.prevCameraPitch
					+ (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
			GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.05F,
					-Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2) * 0.1F, 0.0F);
			GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 1.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
		}

	}

	private void hurtCameraEffect(float partialTicks) {
		if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.mc.getRenderViewEntity();
			float f = (float) entitylivingbase.hurtTime - partialTicks;
			float f2;
			if (entitylivingbase.getHealth() <= 0.0F) {
				f2 = (float) entitylivingbase.deathTime + partialTicks;
				GlStateManager.rotate(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
			}
			if (f < 0.0F) {
				return;
			}
			f /= (float) entitylivingbase.maxHurtTime;
			f = MathHelper.sin(f * f * f * f * 3.1415927F);
			f2 = entitylivingbase.attackedAtYaw;
			GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
		}
	}

	public void addModuleRender(Class<? extends IWeaponModule> moduleClass, IModuleRenderV2 render) {
		this.moduleRenders.put(moduleClass, render);
	}

	public void addWeaponLayer(IWeaponLayer layer) {
		this.weaponLayers.add(layer);
	}
}