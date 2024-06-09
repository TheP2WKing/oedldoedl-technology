package net.thep2wking.oedldoedltechnology.api;

import java.util.List;

import io.netty.buffer.ByteBuf;
import matteroverdrive.api.gravity.IGravitationalAnomaly;
import matteroverdrive.api.gravity.IGravityEntity;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.client.data.Color;
import matteroverdrive.client.render.RenderParticlesHandler;
import matteroverdrive.client.sound.MOPositionedSound;
import matteroverdrive.fx.EntityFXGenericAnimatedParticle;
import matteroverdrive.fx.PhaserBoltRecoil;
import matteroverdrive.init.MatterOverdriveSounds;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.util.handler.ModClientWeaponHandler;
import net.thep2wking.oedldoedltechnology.util.network.ModPacketUpdatePlasmaBolt;

public class ModEntityPlasmaShotBase extends Entity implements IProjectile, IGravityEntity, IEntityAdditionalSpawnData {
	public Entity shootingEntity;
	private BlockPos blockPos;
	private int distanceTraveled;
	private float damage;
	private IBlockState blockState;
	private int life;
	private int color;
	private float fireDamageMultiply;
	private ItemStack weapon;
	private float renderSize = 2;
	private boolean canHurtCaster = false;
	private float knockback;
	private boolean canRicoche;
	private float explodeMultiply;

	public ModEntityPlasmaShotBase(World world) {
		super(world);
		setRenderDistanceWeight(3);
		this.setSize(0.5F, 0.5F);
	}

	public ModEntityPlasmaShotBase(World world, EntityLivingBase entityLivingBase, Vec3d position, Vec3d dir,
			WeaponShot shot, float speed) {
		super(world);
		rand.setSeed(shot.getSeed());
		setEntityId(shot.getSeed());
		this.shootingEntity = entityLivingBase;
		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(position.x, position.y, position.z, entityLivingBase.rotationYaw,
				entityLivingBase.rotationPitch);
		this.motionX = dir.x;
		this.motionY = dir.y;
		this.motionZ = dir.z;
		this.height = 0.0F;
		this.life = shot.getRange();
		this.damage = shot.getDamage();
		this.color = shot.getColor();
		this.blockPos = new BlockPos(0, 0, 0);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, speed * 1.5F, shot.getAccuracy());
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
	}

	public void setThrowableHeading(double x, double y, double z, float speed, float accuracy) {
		float dirLength = MathHelper.sqrt(x * x + y * y + z * z);
		x /= (double) dirLength;
		y /= (double) dirLength;
		z /= (double) dirLength;
		x += this.rand.nextGaussian() * 0.007499999832361937D * (double) accuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937D * (double) accuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937D * (double) accuracy;
		x *= (double) speed;
		y *= (double) speed;
		z *= (double) speed;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f3) * 180.0D / Math.PI);
	}

	@Override
	protected void entityInit() {
	}

	public void simulateDelay(int delay) {
		if (delay > 0) {
			double lastMotionX = motionX;
			double lastMotionY = motionY;
			double lastMotionZ = motionZ;
			motionX *= delay;
			motionY *= delay;
			motionZ *= delay;
			onUpdate();
			motionX = lastMotionX;
			motionY = lastMotionY;
			motionZ = lastMotionZ;
		}
	}

	@Override
	public void onUpdate() {
		if (!world.isRemote && shootingEntity instanceof EntityPlayerMP) {
			OedldoedlTechnology.NETWORK.sendTo(new ModPacketUpdatePlasmaBolt(getEntityId(), posX, posY, posZ),
					(EntityPlayerMP) shootingEntity);
		}
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D
					/ Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D
					/ Math.PI);
		}
		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getMaterial() != Material.AIR) {
			AxisAlignedBB axisalignedbb = blockState.getCollisionBoundingBox(this.world, blockPos);
			if (axisalignedbb != null && axisalignedbb.contains(new Vec3d(this.posX, this.posY, this.posZ))) {
				setDead();
			}
		}
		if (this.distanceTraveled > this.life) {
			setDead();
			return;
		}
		distanceTraveled += new Vec3d(motionX, motionY, motionZ).lengthVector();
		float motionLeway = 0.0f;
		Vec3d vec31 = new Vec3d(this.posX - this.motionX * motionLeway, this.posY - this.motionY * motionLeway,
				this.posZ - this.motionZ * motionLeway);
		Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec31, vec3, false, true, false);
		vec31 = new Vec3d(this.posX - this.motionX * motionLeway, this.posY - this.motionY * motionLeway,
				this.posZ - this.motionZ * motionLeway);
		vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if (movingobjectposition != null) {
			vec3 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y,
					movingobjectposition.hitVec.z);
		}
		Entity entity = null;
		Vec3d hit = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		for (Entity entity1 : list) {
			if (entity1 != null && entity1.canBeAttackedWithItem() && !entity1.isDead
					&& entity1 instanceof EntityLivingBase && ((EntityLivingBase) entity1).deathTime == 0) {
				float f1 = 0.3f;
				if (this.shootingEntity != null) {
					if (this.shootingEntity instanceof EntityLivingBase) {
						if (!canAttackTeammate((EntityLivingBase) entity1, (EntityLivingBase) this.shootingEntity)) {
							continue;
						}
					}
					if (!canHurtCaster
							&& (entity1 == this.shootingEntity || entity1 == this.shootingEntity.getRidingEntity())) {
						continue;
					}
				}
				AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double) f1, (double) f1,
						(double) f1);
				RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

				if (movingobjectposition1 != null) {
					double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						hit = movingobjectposition1.hitVec;
						d0 = d1;
					}
				}
			}
		}
		if (entity != null) {
			movingobjectposition = new RayTraceResult(entity, hit);
		}
		if (movingobjectposition != null && movingobjectposition.entityHit != null
				&& movingobjectposition.entityHit instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
			if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
					&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
				movingobjectposition = null;
			}
		}
		if (movingobjectposition != null) {
			if (movingobjectposition.entityHit != null) {
				DamageSource damagesource;
				if (this.shootingEntity == null) {
					damagesource = getDamageSource(this);
				} else {
					damagesource = getDamageSource(this.shootingEntity);
				}
				movingobjectposition.entityHit.hurtResistantTime = 0;
				double lastMotionX = movingobjectposition.entityHit.motionX;
				double lastMotionZ = movingobjectposition.entityHit.motionZ;
				boolean attack = movingobjectposition.entityHit.attackEntityFrom(damagesource, this.damage);
				if (!attack && movingobjectposition.entityHit instanceof EntityDragon) {
					attack = ((EntityDragon) movingobjectposition.entityHit).attackEntityFromPart(
							((EntityDragon) movingobjectposition.entityHit).dragonPartBody, damagesource, this.damage);
				}
				if (attack) {
					movingobjectposition.entityHit.motionX = lastMotionX
							+ (movingobjectposition.entityHit.motionX - lastMotionX) * knockback;
					movingobjectposition.entityHit.motionZ = lastMotionZ
							+ (movingobjectposition.entityHit.motionZ - lastMotionZ) * knockback;
					if (movingobjectposition.entityHit instanceof EntityLivingBase) {
						EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

						if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
							EnchantmentHelper.applyArthropodEnchantments(entitylivingbase, this.shootingEntity);
							EnchantmentHelper.applyThornEnchantments((EntityLivingBase) this.shootingEntity,
									entitylivingbase);
						}
						if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
								&& movingobjectposition.entityHit instanceof EntityPlayer
								&& this.shootingEntity instanceof EntityPlayerMP) {
							((EntityPlayerMP) this.shootingEntity).connection
									.sendPacket(new SPacketChangeGameState(6, 0.0F));
						}
					}
					if (fireDamageMultiply > 0) {
						movingobjectposition.entityHit.setFire((int) (10 * fireDamageMultiply));
					}
					if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
						this.setDead();
					}
				} else {
					if (movingobjectposition.entityHit instanceof EntityLivingBase) {
						this.setDead();
					}
				}
				if (world.isRemote) {
					if (weapon != null && weapon.getItem() instanceof ModItemEnergyWeaponBase) {
						((ModItemEnergyWeaponBase) weapon.getItem()).onProjectileHit(movingobjectposition, weapon,
								world, 5);
					}
					onHit(movingobjectposition);
				}
				manageHitEffect(movingobjectposition);
				// MinecraftForge.EVENT_BUS.post(new ModEventPlasmaBoltHit(weapon, movingobjectposition, this,
				// 		world.isRemote ? Side.CLIENT : Side.SERVER));
			} else {
				// onHit(movingobjectposition);
				this.blockPos = movingobjectposition.getBlockPos();
				this.blockState = this.world.getBlockState(blockPos);
				if (this.blockState.getMaterial() != Material.AIR) {
					this.blockState.getBlock().onEntityCollidedWithBlock(this.world, blockPos, this.blockState, this);
					if (this.blockState instanceof BlockTNT) {
						world.setBlockToAir(blockPos);
						EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world,
								(double) ((float) blockPos.getX() + 0.5F), (double) ((float) blockPos.getY() + 0.5F),
								(double) ((float) blockPos.getZ() + 0.5F),
								shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) shootingEntity : null);
						entitytntprimed.setFuse(0);
						world.spawnEntity(entitytntprimed);
					}
				}
				if (world.isRemote) {
					if (weapon != null && weapon.getItem() instanceof ModItemEnergyWeaponBase) {
						((ModItemEnergyWeaponBase) weapon.getItem()).onProjectileHit(movingobjectposition, weapon,
								world, 5);
					}
					onHit(movingobjectposition);
				}
				// MinecraftForge.EVENT_BUS.post(new ModEventPlasmaBoltHit(weapon, movingobjectposition, this,
				// 		world.isRemote ? Side.CLIENT : Side.SERVER));
				if (!manageHitEffect(movingobjectposition)) {
					if (canRicoche) {
						handleRicochets(movingobjectposition);
					} else {
						setDead();
					}
				} else {
					setDead();
				}
			}
		}
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		this.setPositionAndRotation(this.posX, this.posY, this.posZ,
				(float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI),
				(float) (Math.atan2(motionY, (double) f3) * 180.0D / Math.PI));
		this.doBlockCollisions();
		super.onUpdate();
	}

	protected void handleRicochets(RayTraceResult hit) {
		Vec3d motion = new Vec3d(this.motionX, this.motionY, this.motionZ);
		Vec3d surfaceNormal = new Vec3d(hit.sideHit.getDirectionVec());
		double deflectDot = surfaceNormal.dotProduct(motion) * 2;
		Vec3d projection = new Vec3d(surfaceNormal.x * deflectDot, surfaceNormal.y * deflectDot,
				surfaceNormal.z * deflectDot);
		Vec3d deflectDir = motion.subtract(projection);
		this.motionX = deflectDir.x;
		this.motionY = deflectDir.y;
		this.motionZ = deflectDir.z;
		this.distanceTraveled += 2;
		canHurtCaster = true;
	}

	@Override
	public void setDead() {
		if (world.isRemote) {
			((ModClientWeaponHandler) OedldoedlTechnology.PROXY.getModWeaponHandler()).removePlasmaBolt(this);
		}
		super.setDead();
	}

	public SoundEvent setImpactSound() {
		return MatterOverdriveSounds.weaponsSizzle;
	}

	@SideOnly(Side.CLIENT)
	public void onHit(RayTraceResult hit) {
		Vec3d sideHit;
		if (hit.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
			sideHit = new Vec3d(hit.sideHit.getDirectionVec());
		} else {
			sideHit = new Vec3d(-motionX, -motionY, -motionZ);
		}
		Color c = new Color(color);
		if (hit.typeOfHit.equals(RayTraceResult.Type.ENTITY)) {
			if (hit.entityHit instanceof EntityLivingBase) {
				EntityFXGenericAnimatedParticle blood = new EntityFXGenericAnimatedParticle(world,
						hit.hitVec.x + rand.nextDouble() * 0.4 - 0.2, hit.hitVec.y + rand.nextDouble() * 0.4 - 0.2,
						hit.hitVec.z + rand.nextDouble() * 0.4 - 0.2, 6f + rand.nextFloat() * 2,
						RenderParticlesHandler.blood);
				blood.setParticleMaxAge(12);
				blood.setRenderDistanceWeight(3f);
				ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(blood,
						RenderParticlesHandler.Blending.Transparent);
			}
		} else {
			int hitPraticles = Math.max(0, (int) (16 * renderSize)
					- ((int) (8 * renderSize) * Minecraft.getMinecraft().gameSettings.particleSetting));
			for (int i = 0; i < hitPraticles; i++) {
				Minecraft.getMinecraft().effectRenderer.addEffect(new PhaserBoltRecoil(world, hit.hitVec.x,
						hit.hitVec.y, hit.hitVec.z, c, sideHit.x * 30, sideHit.y * 30, sideHit.z * 30));
			}
			if (getRenderSize() > 0.5) {
				MOPositionedSound sizzleSound = new MOPositionedSound(setImpactSound(),
						SoundCategory.PLAYERS, rand.nextFloat() * 0.2f + 0.4f, rand.nextFloat() * 0.6f + 0.7f);
				sizzleSound.setPosition((float) hit.hitVec.x, (float) hit.hitVec.y, (float) hit.hitVec.z);
				Minecraft.getMinecraft().getSoundHandler().playSound(sizzleSound);
				if (hit.typeOfHit == RayTraceResult.Type.BLOCK) {
					MOPositionedSound ricochetSound = new MOPositionedSound(setImpactSound(),
							SoundCategory.PLAYERS, rand.nextFloat() * 0.2f + 0.6f, rand.nextFloat() * 0.2f + 1f);
					ricochetSound.setPosition((float) hit.hitVec.x, (float) hit.hitVec.y, (float) hit.hitVec.z);
					Minecraft.getMinecraft().getSoundHandler().playSound(ricochetSound);
				}
			}
			if (rand.nextBoolean()) {
				EntityFXGenericAnimatedParticle smoke = new EntityFXGenericAnimatedParticle(world, hit.hitVec.x,
						hit.hitVec.y, hit.hitVec.z, 8f + rand.nextFloat() * 2, RenderParticlesHandler.smoke);
				smoke.setParticleMaxAge(20);
				smoke.setRenderDistanceWeight(2f);
				smoke.setColorRGBA(c.multiply(1, 1, 1, 0.5f));
				smoke.setBottomPivot(true);
				ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(smoke,
						RenderParticlesHandler.Blending.Transparent);
			}
		}
	}

	public boolean manageHitEffect(RayTraceResult hit) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	private void manageClientExplosions(RayTraceResult hit) {
		float explosionRange = this.explodeMultiply * 0.2f;
		EntityFXGenericAnimatedParticle explosion = new EntityFXGenericAnimatedParticle(world, hit.hitVec.x,
				hit.hitVec.y, hit.hitVec.z, 10, RenderParticlesHandler.explosion);
		explosion.setRenderDistanceWeight(3f);
		explosion.setParticleMaxAge(18);
		ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(explosion,
				RenderParticlesHandler.Blending.Transparent);
		for (int i = 0; i < 6; i++) {
			explosion = new EntityFXGenericAnimatedParticle(world, hit.hitVec.x + rand.nextGaussian() * explosionRange,
					hit.hitVec.y + rand.nextGaussian() * explosionRange,
					hit.hitVec.z + rand.nextGaussian() * explosionRange, 4 + rand.nextFloat() * 4,
					RenderParticlesHandler.explosion);
			explosion.setRenderDistanceWeight(3f);
			explosion.setParticleMaxAge(18);
			ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(explosion,
					RenderParticlesHandler.Blending.Transparent);
		}
	}

	private boolean canAttackTeammate(EntityLivingBase shooter, EntityLivingBase target) {
		if (shooter != null && target != null) {
			if (shooter.getTeam() != null && shooter.isOnSameTeam(target)) {
				return shooter.getTeam().getAllowFriendlyFire();
			}
			return true;
		}
		return true;
	}

	public DamageSource getDamageSource(Entity shootingEntity) {
		EntityDamageSourceIndirect dmg = new EntityDamageSourceIndirect("plasmaBolt", this, shootingEntity);
		dmg.setProjectile();
		return dmg;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) this.blockPos.getX());
		tagCompound.setShort("yTile", (short) this.blockPos.getY());
		tagCompound.setShort("zTile", (short) this.blockPos.getZ());
		tagCompound.setFloat("damage", this.damage);
		tagCompound.setInteger("distanceTraveled", this.distanceTraveled);
		tagCompound.setInteger("life", this.life);
		tagCompound.setInteger("color", this.color);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		this.blockPos = new BlockPos(tagCompound.getShort("xTile"), tagCompound.getShort("yTile"),
				tagCompound.getShort("zTile"));
		if (tagCompound.hasKey("damage", 99)) {
			this.damage = tagCompound.getFloat("damage");
		}
		if (tagCompound.hasKey("ticksInAir", 99)) {
			this.distanceTraveled = tagCompound.getInteger("distanceTraveled");
		}
		if (tagCompound.hasKey("life", 99)) {
			this.life = tagCompound.getInteger("life");
		}
		if (tagCompound.hasKey("color", 99)) {
			this.color = tagCompound.getInteger("color");
		}
	}

	public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setDamage(float p_70239_1_) {
		this.damage = p_70239_1_;
	}

	public boolean canAttackWithItem() {
		return false;
	}

	public void setWeapon(ItemStack weapon) {
		this.weapon = weapon;
	}

	public int getColor() {
		return color;
	}

	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}

	@Override
	public boolean isAffectedByAnomaly(IGravitationalAnomaly anomaly) {
		return false;
	}

	@Override
	public void onEntityConsumed(IGravitationalAnomaly anomaly) {
	}

	public void setFireDamageMultiply(float firey) {
		this.fireDamageMultiply = firey;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(damage);
		buffer.writeInt(color);
		buffer.writeFloat(fireDamageMultiply);
		buffer.writeInt(life);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		damage = additionalData.readFloat();
		color = additionalData.readInt();
		fireDamageMultiply = additionalData.readFloat();
		life = additionalData.readInt();
	}

	public float getLife() {
		return 1f - ((float) distanceTraveled / (float) life);
	}

	public float setLife(float life) {
		return life;
	}

	public float getRenderSize() {
		return renderSize;
	}

	public void setRenderSize(float size) {
		this.renderSize = size;
	}

	public void setKnockBack(float knockback) {
		this.knockback = knockback;
	}

	public void markRicochetable() {
		this.canRicoche = true;
	}

	public void setExplodeMultiply(float explodeMultiply) {
		this.explodeMultiply = explodeMultiply;
	}
}