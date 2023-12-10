package net.thep2wking.oedldoedltechnology.content.item;

import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.List;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.events.weapon.MOEventPlasmaBlotHit;
import matteroverdrive.api.gravity.IGravitationalAnomaly;
import matteroverdrive.api.gravity.IGravityEntity;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.client.data.Color;
import matteroverdrive.client.render.RenderParticlesHandler;
import matteroverdrive.client.render.RenderParticlesHandler.Blending;
import matteroverdrive.client.sound.MOPositionedSound;
import matteroverdrive.entity.weapon.PlasmaBolt;
import matteroverdrive.fx.EntityFXGenericAnimatedParticle;
import matteroverdrive.fx.PhaserBoltRecoil;
import matteroverdrive.handler.weapon.ClientWeaponHandler;
import matteroverdrive.init.MatterOverdriveSounds;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.client.PacketUpdatePlasmaBolt;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RailgunBolt extends PlasmaBolt {
	public Entity shootingEntity;
	private BlockPos blockPos;
	private int distanceTraveled;
	private float damage;
	private IBlockState blockState;
	private int life;
	private int color;
	private float fireDamageMultiply;
	private ItemStack weapon;
	private float renderSize = 2.0F;
	private boolean canHurtCaster = false;
	private float knockback;
	private boolean canRicoche;
	private float explodeMultiply;

	public RailgunBolt(World world) {
		super(world);
		setRenderDistanceWeight(3.0);
		this.setSize(0.5F, 0.5F);
	}

	public RailgunBolt(World world, EntityLivingBase entityLivingBase, Vec3d position, Vec3d dir, WeaponShot shot,
			float speed) {
		super(world);
		this.rand.setSeed((long) shot.getSeed());
		this.setEntityId(shot.getSeed());
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

	@Override
	public void setThrowableHeading(double x, double y, double z, float speed, float accuracy) {
		float dirLength = MathHelper.sqrt(x * x + y * y + z * z);
		x /= (double) dirLength;
		y /= (double) dirLength;
		z /= (double) dirLength;
		x += this.rand.nextGaussian() * 0.007499999832361937 * (double) accuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937 * (double) accuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937 * (double) accuracy;
		x *= (double) speed;
		y *= (double) speed;
		z *= (double) speed;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0 / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f3) * 180.0 / Math.PI);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void simulateDelay(int delay) {
		if (delay > 0) {
			double lastMotionX = this.motionX;
			double lastMotionY = this.motionY;
			double lastMotionZ = this.motionZ;
			this.motionX *= (double) delay;
			this.motionY *= (double) delay;
			this.motionZ *= (double) delay;
			this.onUpdate();
			this.motionX = lastMotionX;
			this.motionY = lastMotionY;
			this.motionZ = lastMotionZ;
		}

	}

	@Override
	public void onUpdate() {
		// if (!this.world.isRemote && this.shootingEntity instanceof EntityPlayerMP) {
		// 	MatterOverdrive.NETWORK.sendTo(
		// 			new PacketUpdatePlasmaBolt(this.getEntityId(), this.posX, this.posY, this.posZ),
		// 			(EntityPlayerMP) this.shootingEntity);
		// }

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0
					/ Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0
					/ Math.PI);
		}

		IBlockState blockState = this.world.getBlockState(this.blockPos);
		if (blockState.getMaterial() != Material.AIR) {
			AxisAlignedBB axisalignedbb = blockState.getCollisionBoundingBox(this.world, this.blockPos);
			if (axisalignedbb != null && axisalignedbb.contains(new Vec3d(this.posX, this.posY, this.posZ))) {
				this.setDead();
			}
		}

		if (this.distanceTraveled > this.life) {
			this.setDead();
		} else {
			this.distanceTraveled = (int) ((double) this.distanceTraveled
					+ (new Vec3d(this.motionX, this.motionY, this.motionZ)).lengthVector());
			float motionLeway = 0.0F;
			Vec3d vec31 = new Vec3d(this.posX - this.motionX * (double) motionLeway,
					this.posY - this.motionY * (double) motionLeway, this.posZ - this.motionZ * (double) motionLeway);
			Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3d(this.posX - this.motionX * (double) motionLeway,
					this.posY - this.motionY * (double) motionLeway, this.posZ - this.motionZ * (double) motionLeway);
			vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (movingobjectposition != null) {
				vec3 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y,
						movingobjectposition.hitVec.z);
			}

			Entity entity = null;
			Vec3d hit = null;
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this,
					this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
			double d0 = 0.0;
			Iterator var11 = list.iterator();

			while (true) {
				Entity entity1;
				RayTraceResult movingobjectposition1;
				double lastMotionZ;
				do {
					do {
						float f1;
						do {
							do {
								do {
									do {
										do {
											do {
												if (!var11.hasNext()) {
													if (entity != null) {
														movingobjectposition = new RayTraceResult(entity, hit);
													}

													if (movingobjectposition != null
															&& movingobjectposition.entityHit != null
															&& movingobjectposition.entityHit instanceof EntityPlayer) {
														EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
														if (entityplayer.capabilities.disableDamage
																|| this.shootingEntity instanceof EntityPlayer
																		&& !((EntityPlayer) this.shootingEntity)
																				.canAttackPlayer(entityplayer)) {
															movingobjectposition = null;
														}
													}

													if (movingobjectposition != null) {
														if (movingobjectposition.entityHit != null) {
															DamageSource damagesource;
															if (this.shootingEntity == null) {
																damagesource = this.getDamageSource(this);
															} else {
																damagesource = this
																		.getDamageSource(this.shootingEntity);
															}

															movingobjectposition.entityHit.hurtResistantTime = 0;
															double lastMotionX = movingobjectposition.entityHit.motionX;
															double lastMotionY = movingobjectposition.entityHit.motionY;
															lastMotionZ = movingobjectposition.entityHit.motionZ;
															boolean attack = movingobjectposition.entityHit
																	.attackEntityFrom(damagesource, this.damage);
															if (!attack
																	&& movingobjectposition.entityHit instanceof EntityDragon) {
																attack = ((EntityDragon) movingobjectposition.entityHit)
																		.attackEntityFromPart(
																				((EntityDragon) movingobjectposition.entityHit).dragonPartBody,
																				damagesource, this.damage);
															}

															if (attack) {
																movingobjectposition.entityHit.motionX = lastMotionX
																		+ (movingobjectposition.entityHit.motionX
																				- lastMotionX)
																				* (double) this.knockback;
																movingobjectposition.entityHit.motionY = lastMotionY
																		+ (movingobjectposition.entityHit.motionY
																				- lastMotionY)
																				* (double) this.knockback;
																movingobjectposition.entityHit.motionZ = lastMotionZ
																		+ (movingobjectposition.entityHit.motionZ
																				- lastMotionZ)
																				* (double) this.knockback;
																if (movingobjectposition.entityHit instanceof EntityLivingBase) {
																	EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;
																	if (this.shootingEntity != null
																			&& this.shootingEntity instanceof EntityLivingBase) {
																		EnchantmentHelper.applyArthropodEnchantments(
																				entitylivingbase, this.shootingEntity);
																		EnchantmentHelper.applyThornEnchantments(
																				(EntityLivingBase) this.shootingEntity,
																				entitylivingbase);
																	}

																	if (this.shootingEntity != null
																			&& movingobjectposition.entityHit != this.shootingEntity
																			&& movingobjectposition.entityHit instanceof EntityPlayer
																			&& this.shootingEntity instanceof EntityPlayerMP) {
																		((EntityPlayerMP) this.shootingEntity).connection
																				.sendPacket(new SPacketChangeGameState(
																						6, 0.0F));
																	}
																}

																if (this.fireDamageMultiply > 0.0F) {
																	movingobjectposition.entityHit.setFire(
																			(int) (10.0F * this.fireDamageMultiply));
																}

																if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
																	this.setDead();
																}
															} else if (movingobjectposition.entityHit instanceof EntityLivingBase) {
																this.setDead();
															}

															if (this.world.isRemote) {
																if (this.weapon != null && this.weapon
																		.getItem() instanceof EnergyWeapon) {
																	((EnergyWeapon) this.weapon.getItem())
																			.onProjectileHit(movingobjectposition,
																					this.weapon, this.world, 5.0F);
																}

																this.onHit(movingobjectposition);
															}

															this.manageExplosions(movingobjectposition);
															// MinecraftForge.EVENT_BUS.post(new MOEventPlasmaBlotHit(
															// 		this.weapon, movingobjectposition, this,
															// 		this.world.isRemote ? Side.CLIENT : Side.SERVER));
														} else {
															this.blockPos = movingobjectposition.getBlockPos();
															this.blockState = this.world.getBlockState(this.blockPos);
															if (this.blockState.getMaterial() != Material.AIR) {
																this.blockState.getBlock().onEntityCollidedWithBlock(
																		this.world, this.blockPos, this.blockState,
																		this);
																if (this.blockState instanceof BlockTNT) {
																	this.world.setBlockToAir(this.blockPos);
																	EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
																			this.world,
																			(double) ((float) this.blockPos.getX()
																					+ 0.5F),
																			(double) ((float) this.blockPos.getY()
																					+ 0.5F),
																			(double) ((float) this.blockPos.getZ()
																					+ 0.5F),
																			this.shootingEntity instanceof EntityLivingBase
																					? (EntityLivingBase) this.shootingEntity
																					: null);
																	entitytntprimed.setFuse(0);
																	this.world.spawnEntity(entitytntprimed);
																}
															}

															if (this.world.isRemote) {
																if (this.weapon != null && this.weapon
																		.getItem() instanceof EnergyWeapon) {
																	((EnergyWeapon) this.weapon.getItem())
																			.onProjectileHit(movingobjectposition,
																					this.weapon, this.world, 5.0F);
																}

																this.onHit(movingobjectposition);
															}

															// MinecraftForge.EVENT_BUS.post(new MOEventPlasmaBlotHit(
															// 		this.weapon, movingobjectposition, this,
															// 		this.world.isRemote ? Side.CLIENT : Side.SERVER));
															if (!this.manageExplosions(movingobjectposition)) {
																if (this.canRicoche) {
																	this.handleRicochets(movingobjectposition);
																} else {
																	this.setDead();
																}
															} else {
																this.setDead();
															}
														}
													}

													this.posX += this.motionX;
													this.posY += this.motionY;
													this.posZ += this.motionZ;
													float f3 = MathHelper.sqrt(
															this.motionX * this.motionX + this.motionZ * this.motionZ);
													this.setPositionAndRotation(this.posX, this.posY, this.posZ,
															(float) (Math.atan2(this.motionX, this.motionZ) * 180.0
																	/ Math.PI),
															(float) (Math.atan2(this.motionY, (double) f3) * 180.0
																	/ Math.PI));
													this.doBlockCollisions();
													super.onUpdate();
													return;
												}

												entity1 = (Entity) var11.next();
											} while (entity1 == null);
										} while (!entity1.canBeAttackedWithItem());
									} while (entity1.isDead);
								} while (!(entity1 instanceof EntityLivingBase));
							} while (((EntityLivingBase) entity1).deathTime != 0);

							f1 = 0.3F;
						} while (this.shootingEntity != null && (this.shootingEntity instanceof EntityLivingBase
								&& !this.canAttackTeammate((EntityLivingBase) entity1,
										(EntityLivingBase) this.shootingEntity)
								|| !this.canHurtCaster && (entity1 == this.shootingEntity
										|| entity1 == this.shootingEntity.getRidingEntity())));

						AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double) f1, (double) f1,
								(double) f1);
						movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
					} while (movingobjectposition1 == null);

					lastMotionZ = vec31.distanceTo(movingobjectposition1.hitVec);
				} while (!(lastMotionZ < d0) && d0 != 0.0);

				entity = entity1;
				hit = movingobjectposition1.hitVec;
				d0 = lastMotionZ;
			}
		}
	}

	@Override
	protected void handleRicochets(RayTraceResult hit) {
		Vec3d motion = new Vec3d(this.motionX, this.motionY, this.motionZ);
		Vec3d surfaceNormal = new Vec3d(hit.sideHit.getDirectionVec());
		double deflectDot = surfaceNormal.dotProduct(motion) * 2.0;
		Vec3d projection = new Vec3d(surfaceNormal.x * deflectDot, surfaceNormal.y * deflectDot,
				surfaceNormal.z * deflectDot);
		Vec3d deflectDir = motion.subtract(projection);
		this.motionX = deflectDir.x;
		this.motionY = deflectDir.y;
		this.motionZ = deflectDir.z;
		this.distanceTraveled += 2;
		this.canHurtCaster = true;
	}

	@Override
	public void setDead() {
		if (this.world.isRemote) {
			// ((ClientWeaponHandler) MatterOverdrive.PROXY.getWeaponHandler()).removePlasmaBolt(this);
		}

		super.setDead();
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected void onHit(RayTraceResult hit) {
		Vec3d sideHit;
		if (hit.typeOfHit.equals(Type.BLOCK)) {
			sideHit = new Vec3d(hit.sideHit.getDirectionVec());
		} else {
			sideHit = new Vec3d(-this.motionX, -this.motionY, -this.motionZ);
		}

		Color c = new Color(this.color);
		if (hit.typeOfHit.equals(Type.ENTITY)) {
			if (hit.entityHit instanceof EntityLivingBase) {
				EntityFXGenericAnimatedParticle blood = new EntityFXGenericAnimatedParticle(this.world,
						hit.hitVec.x + this.rand.nextDouble() * 0.4 - 0.2,
						hit.hitVec.y + this.rand.nextDouble() * 0.4 - 0.2,
						hit.hitVec.z + this.rand.nextDouble() * 0.4 - 0.2, 6.0F + this.rand.nextFloat() * 2.0F,
						RenderParticlesHandler.blood);
				blood.setParticleMaxAge(12);
				blood.setRenderDistanceWeight(3.0F);
				ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(blood, Blending.Transparent);
			}
		} else {
			int hitPraticles = Math.max(0, (int) (16.0F * this.renderSize)
					- (int) (8.0F * this.renderSize) * Minecraft.getMinecraft().gameSettings.particleSetting);

			for (int i = 0; i < hitPraticles; ++i) {
				Minecraft.getMinecraft().effectRenderer.addEffect(new PhaserBoltRecoil(this.world, hit.hitVec.x,
						hit.hitVec.y, hit.hitVec.z, c, sideHit.x * 30.0, sideHit.y * 30.0, sideHit.z * 30.0));
			}

			if ((double) this.getRenderSize() > 0.5) {
				MOPositionedSound sizzleSound = new MOPositionedSound(MatterOverdriveSounds.weaponsSizzle,
						SoundCategory.PLAYERS, this.rand.nextFloat() * 0.2F + 0.4F,
						this.rand.nextFloat() * 0.6F + 0.7F);
				sizzleSound.setPosition((float) hit.hitVec.x, (float) hit.hitVec.y, (float) hit.hitVec.z);
				Minecraft.getMinecraft().getSoundHandler().playSound(sizzleSound);
				if (hit.typeOfHit == Type.BLOCK) {
					MOPositionedSound ricochetSound = new MOPositionedSound(MatterOverdriveSounds.weaponsLaserRicochet,
							SoundCategory.PLAYERS, this.rand.nextFloat() * 0.2F + 0.6F,
							this.rand.nextFloat() * 0.2F + 1.0F);
					ricochetSound.setPosition((float) hit.hitVec.x, (float) hit.hitVec.y, (float) hit.hitVec.z);
					Minecraft.getMinecraft().getSoundHandler().playSound(ricochetSound);
				}
			}

			if (this.rand.nextBoolean()) {
				EntityFXGenericAnimatedParticle smoke = new EntityFXGenericAnimatedParticle(this.world, hit.hitVec.x,
						hit.hitVec.y, hit.hitVec.z, 8.0F + this.rand.nextFloat() * 2.0F, RenderParticlesHandler.smoke);
				smoke.setParticleMaxAge(20);
				smoke.setRenderDistanceWeight(2.0F);
				smoke.setColorRGBA(c.multiply(1.0F, 1.0F, 1.0F, 0.5F));
				smoke.setBottomPivot(true);
				ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(smoke, Blending.Transparent);
			}
		}

	}


	private boolean manageExplosions(RayTraceResult hit) {
		if (this.explodeMultiply > 0.0F) {
			if (!this.world.isRemote) {
				Explosion explosion = new Explosion(this.world, this, hit.hitVec.x, hit.hitVec.y, hit.hitVec.z,
						this.explodeMultiply, false, true);
				if (!ForgeEventFactory.onExplosionStart(this.world, explosion)) {
					explosion.doExplosionA();
					this.world.playSound((EntityPlayer) null, hit.hitVec.x, hit.hitVec.y, hit.hitVec.z,
							MatterOverdriveSounds.weaponsExplosiveShot, SoundCategory.PLAYERS, 3.0F,
							(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
				}
			} else {
				this.manageClientExplosions(hit);
			}

			return true;
		} else {
			return false;
		}
	}

	@SideOnly(Side.CLIENT)

	private void manageClientExplosions(RayTraceResult hit) {
		float explosionRange = this.explodeMultiply * 0.2F;
		EntityFXGenericAnimatedParticle explosion = new EntityFXGenericAnimatedParticle(this.world, hit.hitVec.x,
				hit.hitVec.y, hit.hitVec.z, 10.0F, RenderParticlesHandler.explosion);
		explosion.setRenderDistanceWeight(3.0F);
		explosion.setParticleMaxAge(18);
		ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(explosion, Blending.Transparent);

		for (int i = 0; i < 6; ++i) {
			explosion = new EntityFXGenericAnimatedParticle(this.world,
					hit.hitVec.x + this.rand.nextGaussian() * (double) explosionRange,
					hit.hitVec.y + this.rand.nextGaussian() * (double) explosionRange,
					hit.hitVec.z + this.rand.nextGaussian() * (double) explosionRange,
					4.0F + this.rand.nextFloat() * 4.0F, RenderParticlesHandler.explosion);
			explosion.setRenderDistanceWeight(3.0F);
			explosion.setParticleMaxAge(18);
			ClientProxy.renderHandler.getRenderParticlesHandler().addEffect(explosion, Blending.Transparent);
		}

	}


	private boolean canAttackTeammate(EntityLivingBase shooter, EntityLivingBase target) {
		if (shooter != null && target != null) {
			return shooter.getTeam() != null && shooter.isOnSameTeam(target) ? shooter.getTeam().getAllowFriendlyFire()
					: true;
		} else {
			return true;
		}
	}

	@Override
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

	@Override
	public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public double getDamage() {
		return (double) this.damage;
	}

	@Override
	public void setDamage(float p_70239_1_) {
		this.damage = p_70239_1_;
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}
	@Override
	public void setWeapon(ItemStack weapon) {
		this.weapon = weapon;
	}
	@Override
	public int getColor() {
		return this.color;
	}
	@Override
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
	@Override
	public void setFireDamageMultiply(float firey) {
		this.fireDamageMultiply = firey;
	}
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(this.damage);
		buffer.writeInt(this.color);
		buffer.writeFloat(this.fireDamageMultiply);
		buffer.writeInt(this.life);
	}
	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.damage = additionalData.readFloat();
		this.color = additionalData.readInt();
		this.fireDamageMultiply = additionalData.readFloat();
		this.life = additionalData.readInt();
	}
	@Override
	public float getLife() {
		return 1.0F - (float) this.distanceTraveled / (float) this.life;
	}
	@Override
	public float setLife(float life) {
		return life;
	}
	@Override
	public float getRenderSize() {
		return this.renderSize;
	}
	@Override
	public void setRenderSize(float size) {
		this.renderSize = size;
	}
	@Override
	public void setKnockBack(float knockback) {
		this.knockback = knockback;
	}
	@Override
	public void markRicochetable() {
		this.canRicoche = true;
	}
	@Override
	public void setExplodeMultiply(float explodeMultiply) {
		this.explodeMultiply = explodeMultiply;
	}
}
