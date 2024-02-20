package net.thep2wking.oedldoedltechnology.content.item;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.items.weapon.module.WeaponModuleBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;
import net.thep2wking.oedldoedltechnology.api.ModItemEnergyWeaponBase;
import net.thep2wking.oedldoedltechnology.entity.projectile.EntityUpNAtomizerBolt;
import net.thep2wking.oedldoedltechnology.init.ModSounds;

public class ItemUpNAtomizer extends ModItemEnergyWeaponBase {
	public ItemUpNAtomizer(String modid, String name, CreativeTabs tab, int range, int cooldown, int damage, int maxUseTime,
			int shotSpeed, float zoom, int maxHeat, int maxEnergy, int energyPerShot, EnumRarity rarity,
			boolean hasEffect, int tooltipLines, int annotationLines) {
		super(modid, name, tab, range, cooldown, damage, maxUseTime, shotSpeed, zoom, maxHeat,
				maxEnergy, energyPerShot, rarity, hasEffect, tooltipLines, annotationLines);
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
			default:
				return new Vector2f(205, 80 + ((slot - Reference.MODULE_OTHER) * 22));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vector2f getModuleScreenPosition(int slot, ItemStack weapon) {
		switch (slot) {
			case Reference.MODULE_BATTERY:
				return new Vector2f(150, 80);
			case Reference.MODULE_COLOR:
				return new Vector2f(125, 85);
			case Reference.MODULE_BARREL:
				return new Vector2f(110, 85);
		}
		return getSlotPosition(slot, weapon);
	}

	@Override
	public boolean supportsModule(int slot, ItemStack weapon) {
		return slot != Reference.MODULE_SIGHTS;
	}

	@Override
	public boolean supportsModule(ItemStack weapon, ItemStack module) {
		return !module.isEmpty() && (module.getItem() == MatterOverdrive.ITEMS.weapon_module_barrel
				&& module.getItemDamage() != WeaponModuleBarrel.BLOCK_BARREL_ID
				&& module.getItem() == MatterOverdrive.ITEMS.weapon_module_barrel
				&& module.getItemDamage() != WeaponModuleBarrel.DOOMSDAY_BARREL_ID
				&& module.getItem() == MatterOverdrive.ITEMS.weapon_module_barrel
				&& module.getItemDamage() != WeaponModuleBarrel.EXPLOSION_BARREL_ID
				&& module.getItem() == MatterOverdrive.ITEMS.weapon_module_barrel
				&& module.getItemDamage() != WeaponModuleBarrel.FIRE_BARREL_ID
				|| module.getItem() == MatterOverdrive.ITEMS.weapon_module_color);
	}

	@Override
	public float getWeaponBaseAccuracy(ItemStack weapon, boolean zoomed) {
		return 0.5f + getHeat(weapon) / getMaxHeat(weapon) * 4;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack) {
		return EnumAction.NONE;
	}

	@Override
	public SoundEvent setShotSound() {
		return ModSounds.UP_N_ATOMIZER_SHOT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onShooterClientUpdate(ItemStack itemStack, World world, EntityPlayer entityPlayer,
			boolean sendServerTick) {
		if (Mouse.isButtonDown(0) && this.hasShootDelayPassed()) {
			if (canFire(itemStack, world, entityPlayer)) {
				Vec3d dir = entityPlayer.getLook(1);
				Vec3d pos = getFirePosition(entityPlayer, dir, isWeaponZoomed(entityPlayer, itemStack));
				WeaponShot shot = createClientShot(itemStack, entityPlayer,
						isWeaponZoomed(entityPlayer, itemStack));
				this.onClientShot(itemStack, entityPlayer, pos, dir, shot);
				this.addShootDelay(itemStack);
				this.sendShootTickToServer(world, shot, dir, pos);
				if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
					if (isWeaponZoomed(entityPlayer, itemStack)) {
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(2f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 0.05f);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(3 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 0.1f), 1);
					} else {
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(4f + Math.min(2, getAccuracy(itemStack, entityPlayer, true) * 2), 1, 0.07f);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(4 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 0.5f), 1);
					}
				}
				return;
			} else if (needsRecharge(itemStack)) {
				chargeFromEnergyPack(itemStack, entityPlayer);
			}
		}
		super.onShooterClientUpdate(itemStack, world, entityPlayer, sendServerTick);
	}

	@Override
	public ModEntityPlasmaShotBase setBolt(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		EntityUpNAtomizerBolt bolt = new EntityUpNAtomizerBolt(shooter.world, shooter, position, dir, shot,
				getShotSpeed(weapon, shooter));
		bolt.setKnockBack(2);
		return bolt;
	}
}