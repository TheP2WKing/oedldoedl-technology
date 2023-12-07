package net.thep2wking.oedldoedltechnology.api;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.client.sound.WeaponSound;
import matteroverdrive.items.weapon.EnergyWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemEnergyWeaponBase extends EnergyWeapon {
	public final String modid;
	public final String name;
	public final CreativeTabs tab;
	public final int range;
	public final int heatPerShot;
	public final int maxHeat;
	public final int maxUseTime;
	public final int energyPerShot;
	public final int energyCapacity;
	public final int cooldown;
	public final int damage;
	public final int shotSpeed;
	public final float zoom;
	public final EnumRarity rarity;
	public final boolean hasEffect;

	public ModItemEnergyWeaponBase(String modid, String name, CreativeTabs tab, int range, int heatPerShot, int maxHeat,
			int maxUseTime, int energyPerShot, int energyCapacity, int cooldown, int damage, int shotSpeed, float zoom,
			EnumRarity rarity, boolean hasEffect) {
		super(name, range);
		this.modid = modid;
		this.name = name;
		this.tab = tab;
		this.range = range;
		this.heatPerShot = heatPerShot;
		this.maxHeat = maxHeat;
		this.maxUseTime = maxUseTime;
		this.energyPerShot = energyPerShot;
		this.energyCapacity = energyCapacity;
		this.cooldown = cooldown;
		this.damage = damage;
		this.shotSpeed = shotSpeed;
		this.zoom = zoom;
		this.rarity = rarity;
		this.hasEffect = hasEffect;
		this.bFull3D = true;
		this.leftClickFire = true;
	}

	@Override
	public int getCapacity() {
		return energyCapacity;
	}

	@Override
	public int getInput() {
		return 128;
	}

	@Override
	public int getOutput() {
		return 128;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return maxUseTime;
	}

	@Override
	public boolean isAlwaysEquipped(ItemStack weapon) {
		return true;
	}

	@Override
	public int getBaseShootCooldown(ItemStack weapon) {
		return cooldown;
	}

	@Override
	public float getBaseZoom(ItemStack weapon, EntityLivingBase shooter) {
		return zoom;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isWeaponZoomed(EntityLivingBase entityPlayer, ItemStack weapon) {
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			return entityPlayer.isHandActive() && entityPlayer.getActiveHand() == EnumHand.MAIN_HAND;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public WeaponSound getFireSound(ItemStack weapon, EntityLivingBase entity) {
		return null;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack) {
		return EnumAction.BOW;
	}

	@Override
	@SuppressWarnings("all")
	public void addCustomDetails(ItemStack weapon, EntityPlayer player, List infos) {
	}

	@Override
	public int getBaseEnergyUse(ItemStack item) {
		return energyPerShot / getBaseShootCooldown(item);
	}

	@Override
	public int getBaseMaxHeat(ItemStack item) {
		return maxHeat;
	}

	@Override
	public float getWeaponBaseDamage(ItemStack weapon) {
		return damage;
	}

	@Override
	public boolean canFire(ItemStack itemStack, World world, EntityLivingBase shooter) {
		return !isOverheated(itemStack) && DrainEnergy(itemStack, getShootCooldown(itemStack), true)
				&& !isEntitySpectator(shooter);
	}

	@Override
	public float getShotSpeed(ItemStack weapon, EntityLivingBase shooter) {
		return shotSpeed;
	}

	@Override
	public float getWeaponBaseAccuracy(ItemStack weapon, boolean zoomed) {
		return 1f + getHeat(weapon) / (zoomed ? 30f : 10f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vector2f getSlotPosition(int slot, ItemStack weapon) {
		switch (slot) {
			case Reference.MODULE_BATTERY:
				return new Vector2f(170, 115);
			case Reference.MODULE_COLOR:
				return new Vector2f(60, 45);
			case Reference.MODULE_BARREL:
				return new Vector2f(60, 115);
			case Reference.MODULE_SIGHTS:
				return new Vector2f(150, 35);
			default:
				return new Vector2f(205, 80 + ((slot - Reference.MODULE_OTHER) * 22));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vector2f getModuleScreenPosition(int slot, ItemStack weapon) {
		switch (slot) {
			case Reference.MODULE_BATTERY:
				return new Vector2f(165, 80);
			case Reference.MODULE_COLOR:
				return new Vector2f(110, 90);
			case Reference.MODULE_BARREL:
				return new Vector2f(90, 95);
			case Reference.MODULE_SIGHTS:
				return new Vector2f(150, 72);
		}
		return getSlotPosition(slot, weapon);
	}

	@Override
	public boolean onServerFire(ItemStack arg0, EntityLivingBase arg1, WeaponShot arg2, Vec3d arg3, Vec3d arg4,
			int arg5) {
		return true;
	}

	@Override
	public boolean supportsModule(int arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public boolean supportsModule(ItemStack arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public void onClientShot(ItemStack arg0, EntityLivingBase arg1, Vec3d arg2, Vec3d arg3, WeaponShot arg4) {
	}

	@Override
	public void onProjectileHit(RayTraceResult arg0, ItemStack arg1, World arg2, float arg3) {
	}

	@Override
	public boolean hasShootDelayPassed() {
		return true;
	}

}