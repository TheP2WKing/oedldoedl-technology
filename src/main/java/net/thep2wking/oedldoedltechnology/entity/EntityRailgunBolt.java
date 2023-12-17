package net.thep2wking.oedldoedltechnology.entity;

import matteroverdrive.api.weapon.WeaponShot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;

public class EntityRailgunBolt extends ModEntityPlasmaShotBase {
	public EntityRailgunBolt(World world, EntityLivingBase entityLivingBase, Vec3d position, Vec3d dir, WeaponShot shot,
			float speed) {
		super(world, entityLivingBase, position, dir, shot, speed);
	}

	public EntityRailgunBolt(World world) {
		super(world);
	}

	@Override
	public boolean manageHitEffect(RayTraceResult hit) {
		if (!world.isRemote) {
			world.newExplosion(null, hit.hitVec.x, hit.hitVec.y, hit.hitVec.z, 5, false, true);
		}
		return true;
	}
}