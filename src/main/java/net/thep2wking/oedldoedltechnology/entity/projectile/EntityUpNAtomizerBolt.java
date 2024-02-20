package net.thep2wking.oedldoedltechnology.entity.projectile;

import java.util.List;

import matteroverdrive.api.weapon.WeaponShot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.ModEntityPlasmaShotBase;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;

public class EntityUpNAtomizerBolt extends ModEntityPlasmaShotBase {
	public EntityUpNAtomizerBolt(World world, EntityLivingBase entityLivingBase, Vec3d position, Vec3d dir,
			WeaponShot shot, float speed) {
		super(world, entityLivingBase, position, dir, shot, speed);
	}

	public EntityUpNAtomizerBolt(World world) {
		super(world);
	}

	@Override
	public boolean manageHitEffect(RayTraceResult hit) {
		int boxSize = TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.RADIUS;
		EntityLivingBase entity = (EntityLivingBase) hit.entityHit;
		Vec3d block = (Vec3d) hit.hitVec;

		if (!world.isRemote) {
			if (entity == null) {
				BlockPos blockPos = new BlockPos(block.x, block.y, block.z);
				List<EntityLivingBase> listBlockPos = world.getEntitiesWithinAABB(EntityLivingBase.class,
						new AxisAlignedBB(blockPos).grow(boxSize));
				for (EntityLivingBase entities : listBlockPos) {
					if (entities != null) {
						if (entities instanceof EntityPlayer && !((EntityPlayer) entities).capabilities.isCreativeMode
								&& TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.POTION_EFFECTS) {
							entities.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 3, false, false));
							entities.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0, false, false));
						}
						double d5 = entities.posX - blockPos.getX();
						double d7 = entities.posY + (double) entities.getEyeHeight() - blockPos.getY();
						double d9 = entities.posZ - blockPos.getZ();
						entities.motionX += d5
								* TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH;
						entities.motionY += d7
								* (TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH * 1.5);
						entities.motionZ += d9
								* TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH;
						if (entities instanceof EntityPlayer
								&& !((EntityPlayer) entities).capabilities.isCreativeMode) {
							entities.velocityChanged = true;
						}
					}
				}
			} else if (entity != null) {
				BlockPos entityPos = new BlockPos(entity.posX, entity.posY, entity.posZ);
				List<EntityLivingBase> listEntityPos = world.getEntitiesWithinAABB(EntityLivingBase.class,
						new AxisAlignedBB(entityPos).grow(boxSize));
				for (EntityLivingBase entities : listEntityPos) {
					if (entities != null) {
						if (entities instanceof EntityPlayer && !((EntityPlayer) entities).capabilities.isCreativeMode
								&& TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.POTION_EFFECTS) {
							entities.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 3, false, false));
							entities.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0, false, false));
						}
						double d5 = entities.posX - entityPos.getX();
						double d7 = entities.posY + (double) entities.getEyeHeight() - entityPos.getY();
						double d9 = entities.posZ - entityPos.getZ();
						entities.motionX += d5
								* TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH;
						entities.motionY += d7
								* TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH;
						entities.motionZ += d9
								* TechnologyConfig.CONTENT.UP_N_ATOMIZER.UP_N_ATOMIZER_PROJECTILE.MOTION_STRENGTH;
						if (entities instanceof EntityPlayer
								&& !((EntityPlayer) entities).capabilities.isCreativeMode) {
							entities.velocityChanged = true;
						}
					}
				}
			}
		}
		return true;
	}
}