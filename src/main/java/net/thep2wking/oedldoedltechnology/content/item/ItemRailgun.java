package net.thep2wking.oedldoedltechnology.content.item;

import org.lwjgl.util.vector.Vector2f;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.items.weapon.module.WeaponModuleBarrel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;
import net.thep2wking.oedldoedltechnology.api.ModItemEnergyWeaponBase;
import net.thep2wking.oedldoedltechnology.entity.projectile.EntityRailgunBolt;
import net.thep2wking.oedldoedltechnology.init.ModSounds;

public class ItemRailgun extends ModItemEnergyWeaponBase {
	public ItemRailgun(String modid, String name, CreativeTabs tab, int range, int cooldown, int damage, int maxUseTime,
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
				return new Vector2f(165, 80);
			case Reference.MODULE_COLOR:
				return new Vector2f(110, 90);
			case Reference.MODULE_BARREL:
				return new Vector2f(90, 95);
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
		return 5 + getHeat(weapon) * 0.3f;
	}

	@Override
	public SoundEvent setShotSound() {
		return ModSounds.RAILGUN_SHOT;
	}

	@Override
	public ModEntityPlasmaShotBase setBolt(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		EntityRailgunBolt bolt = new EntityRailgunBolt(shooter.world, shooter, position, dir, shot,
				getShotSpeed(weapon, shooter));
		bolt.setKnockBack(2);
		return bolt;
	}
}