package net.thep2wking.oedldoedltechnology.util.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import matteroverdrive.util.WeaponHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IntHashMap;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;
import net.thep2wking.oedldoedltechnology.util.network.ModPacketWeaponTick;

@SideOnly(Side.CLIENT)
public class ModClientWeaponHandler extends ModCommonWeaponHandler {
	private static final float RECOIL_RESET_SPEED = 0.03f;
	private static final float CAMERA_RECOIL_RESET_SPEED = 0.03f;
	public static float ZOOM_TIME;
	public static float RECOIL_TIME;
	public static float RECOIL_AMOUNT;
	public static float CAMERA_RECOIL_TIME;
	public static float CAMERA_RECOIL_AMOUNT;
	private final Map<IWeapon, Integer> shotTracker;
	private final IntHashMap<ModEntityPlasmaShotBase> plasmaBolts;
	private final Random cameraRecoilRandom = new Random();
	private float lastMouseSensitivity;
	private int nextShotID;
	private boolean hasChangedSensitivity = false;

	public ModClientWeaponHandler() {
		shotTracker = new HashMap<>();
		plasmaBolts = new IntHashMap<>();
	}

	public void registerWeapon(IWeapon weapon) {
		shotTracker.put(weapon, 0);
	}

	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().world != null
				&& Minecraft.getMinecraft().player != null) {
			for (IWeapon item : shotTracker.keySet()) {
				int oldTime = shotTracker.get(item);
				if (oldTime > 0) {
					shotTracker.put(item, oldTime - 1);
				}
			}
			manageWeaponView();
		}
	}

	@SideOnly(Side.CLIENT)
	public void onTick(TickEvent.RenderTickEvent event) {
		if (Minecraft.getMinecraft().player != null && event.phase.equals(TickEvent.Phase.END)) {
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			if (entityPlayer.getHeldItem(EnumHand.MAIN_HAND) != null
					&& entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IWeapon) {
				if (((IWeapon) entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem()).isWeaponZoomed(entityPlayer,
						entityPlayer.getHeldItem(EnumHand.MAIN_HAND))) {
					ZOOM_TIME = Math.min(ZOOM_TIME + (event.renderTickTime * 0.1f), 1);
				} else {
					ZOOM_TIME = Math.max(ZOOM_TIME - (event.renderTickTime * 0.1f), 0);
				}
			} else {
				ZOOM_TIME = Math.max(ZOOM_TIME - (event.renderTickTime * 0.2f), 0);
			}
			if (ZOOM_TIME == 0) {
				if (hasChangedSensitivity) {
					hasChangedSensitivity = false;
					Minecraft.getMinecraft().gameSettings.mouseSensitivity = lastMouseSensitivity;
				} else {
					lastMouseSensitivity = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
				}
			} else if (ZOOM_TIME != 0) {
				if (entityPlayer.getHeldItem(EnumHand.MAIN_HAND) != null
						&& entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IWeapon) {
					hasChangedSensitivity = true;
					Minecraft.getMinecraft().gameSettings.mouseSensitivity = lastMouseSensitivity
							* (1f - (ZOOM_TIME * ((IWeapon) entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem())
									.getZoomMultiply(entityPlayer, entityPlayer.getHeldItem(EnumHand.MAIN_HAND))));
				} else {
					hasChangedSensitivity = true;
					Minecraft.getMinecraft().gameSettings.mouseSensitivity = lastMouseSensitivity;
				}
			}
			if (RECOIL_TIME > 0) {
				RECOIL_TIME = Math.max(0, RECOIL_TIME - RECOIL_RESET_SPEED);
			}

			if (CAMERA_RECOIL_TIME > 0) {
				CAMERA_RECOIL_TIME = Math.max(0, CAMERA_RECOIL_TIME - CAMERA_RECOIL_RESET_SPEED);
			}
		}
	}

	@SubscribeEvent
	public void onFovUpdate(FOVUpdateEvent event) {
		if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) != null
				&& Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IWeapon) {
			event.setNewfov(event.getNewfov() - event.getFov() * ZOOM_TIME
					* ((IWeapon) Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem())
							.getZoomMultiply(Minecraft.getMinecraft().player,
									Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND)));
		}
	}

	private void manageWeaponView() {
		for (Object playerObj : Minecraft.getMinecraft().world.playerEntities) {
			EntityPlayer player = (EntityPlayer) playerObj;
			ItemStack currentitem = player.getHeldItem(EnumHand.MAIN_HAND);
			if (currentitem != null && currentitem.getItem() instanceof IWeapon
					&& ((IWeapon) currentitem.getItem()).isAlwaysEquipped(currentitem)) {
				if (player == Minecraft.getMinecraft().player
						&& Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
					currentitem.setItemDamage(0);
				} else {
					currentitem.setItemDamage(1);
					player.setActiveHand(EnumHand.MAIN_HAND);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void sendWeaponTickToServer(World world, PacketFirePlasmaShot firePlasmaShot) {
		OedldoedlTechnology.NETWORK.sendToServer(new ModPacketWeaponTick(world.getWorldTime(), firePlasmaShot));
	}

	public boolean shootDelayPassed(IWeapon item) {
		return shotTracker.get(item) <= 0;
	}

	public void addShootDelay(IWeapon item, ItemStack weaponStack) {
		if (shotTracker.containsKey(item)) {
			shotTracker.put(item, shotTracker.get(item) + item.getShootCooldown(weaponStack));
		}
	}

	public void addReloadDelay(IWeapon weapon, int delay) {
		if (shotTracker.containsKey(weapon)) {
			shotTracker.put(weapon, shotTracker.get(weapon) + delay);
		}
	}

	public void setRecoil(float amount, float time, float viewRecoilMultiply) {
		RECOIL_AMOUNT = amount;
		RECOIL_TIME = time;
		Minecraft.getMinecraft().player.rotationPitch -= amount * viewRecoilMultiply;
	}

	public void setCameraRecoil(float amount, float time) {
		CAMERA_RECOIL_AMOUNT = amount * (cameraRecoilRandom.nextBoolean() ? -1 : 1);
		CAMERA_RECOIL_TIME = time;
	}

	public float getEquippedWeaponAccuracyPercent(EntityPlayer entityPlayer) {
		if (entityPlayer.getHeldItem(EnumHand.MAIN_HAND) != null
				&& entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IWeapon) {
			return ((IWeapon) entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem()).getAccuracy(
					entityPlayer.getHeldItem(EnumHand.MAIN_HAND), entityPlayer,
					((IWeapon) entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem()).isWeaponZoomed(entityPlayer,
							entityPlayer.getHeldItem(EnumHand.MAIN_HAND)))
					/ ((IWeapon) entityPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem())
							.getMaxHeat(entityPlayer.getHeldItem(EnumHand.MAIN_HAND));
		}
		return 0;
	}

	public void addPlasmaBolt(ModEntityPlasmaShotBase plasmaBolt) {
		plasmaBolts.addKey(plasmaBolt.getEntityId(), plasmaBolt);
	}

	public void removePlasmaBolt(ModEntityPlasmaShotBase plasmaBolt) {
		plasmaBolts.removeObject(plasmaBolt.getEntityId());
	}

	public ModEntityPlasmaShotBase getPlasmaBolt(int id) {
		return plasmaBolts.lookup(id);
	}

	public int getNextShotID() {
		return nextShotID++;
	}

	public WeaponShot getNextShot(ItemStack weaponStack, EnergyWeapon energyWeapon, EntityLivingBase shooter,
			boolean zoomed) {
		return new WeaponShot(getNextShotID(), energyWeapon.getWeaponScaledDamage(weaponStack, shooter),
				energyWeapon.getAccuracy(weaponStack, shooter, zoomed), WeaponHelper.getColor(weaponStack),
				energyWeapon.getRange(weaponStack));
	}
}