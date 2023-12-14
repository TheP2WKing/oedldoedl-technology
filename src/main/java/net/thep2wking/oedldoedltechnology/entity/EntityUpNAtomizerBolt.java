package net.thep2wking.oedldoedltechnology.entity;

import matteroverdrive.api.weapon.WeaponShot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;

public class EntityUpNAtomizerBolt extends ModEntityPlasmaShotBase {
	public EntityUpNAtomizerBolt(World world, EntityLivingBase entityLivingBase, Vec3d position, Vec3d dir,
			WeaponShot shot,
			float speed) {
		super(world, entityLivingBase, position, dir, shot, speed);
	}

	public EntityUpNAtomizerBolt(World world) {
		super(world);
	}

	@Override
	public boolean manageHitEffect(RayTraceResult hit) {
		if (!world.isRemote) {
			EntityLiving entity = (EntityLiving) hit.entityHit;
			if (entity != null) {
				// entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 20));
				entity.knockBack(entity, 2, -entity.motionX, -entity.motionZ);
				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 3));
				entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0));
				entity.motionY *= 2;
				// world.newExplosion(null, hit.hitVec.x, hit.hitVec.y, hit.hitVec.z, 2, false, false);
			} 
		}
		return true;
	}
}