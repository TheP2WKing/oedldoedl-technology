package net.thep2wking.oedldoedltechnology.api.nobelisk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.init.ModSounds;

public abstract class ModEntityNobeliskBase extends EntityThrowable {
	public float boundingBoxSize = 0.3f;
	public String throwerUUID;
	private static final DataParameter<Boolean> HAS_COLLIDED = EntityDataManager.createKey(ModEntityNobeliskBase.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Float> ROT_X = EntityDataManager.createKey(ModEntityNobeliskBase.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> ROT_Y = EntityDataManager.createKey(ModEntityNobeliskBase.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> ROT_Z = EntityDataManager.createKey(ModEntityNobeliskBase.class,
			DataSerializers.FLOAT);

	public ModEntityNobeliskBase(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.setSize(boundingBoxSize, boundingBoxSize);
	}

	public ModEntityNobeliskBase(World world, EntityLivingBase thrower) {
		super(world, thrower);
		this.setSize(boundingBoxSize, boundingBoxSize);
		if (thrower != null) {
			this.throwerUUID = thrower.getUniqueID().toString();
		}
	}

	public ModEntityNobeliskBase(World world) {
		super(world);
		this.setSize(boundingBoxSize, boundingBoxSize);
	}

	@Override
	public float getEyeHeight() {
		return this.height / 2.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}
		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	public void applyCollisionEffects() {
		this.setNoGravity(true);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.setHasCollided(true);
		this.setRotX(this.ticksExisted * 5 % 360);
		this.setRotY(this.ticksExisted * 5 % 360);
		this.setRotZ(this.ticksExisted * 5 % 360);
		this.world.setEntityState(this, (byte) 3);
		this.setVelocity(0, 0, 0);
		this.markVelocityChanged();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	public void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
				this.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
				this.applyCollisionEffects();
				if (result.getBlockPos() != null) {
					this.world.playSound(null, result.getBlockPos(), ModSounds.NOBELISK_IMPACT,
							SoundCategory.AMBIENT,
							1.25f, 1.0f);
				}
			}
		}
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.capabilities.isCreativeMode) {
				for (int i = 0; i < 10; i++) {
					this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
							this.posX + (this.rand.nextDouble() - 0.5) * this.width,
							this.posY + this.rand.nextDouble() * this.height,
							this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
							0.0, 0.0, 0.0);
				}
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH,
						SoundCategory.NEUTRAL, 0.5F, 1.0F);
				this.world.setEntityState(this, (byte) 3);
				this.setDead();
				return true;
			}
		}
		return super.hitByEntity(entityIn);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		this.dataManager.register(HAS_COLLIDED, false);
		this.dataManager.register(ROT_X, 0.0f);
		this.dataManager.register(ROT_Y, 0.0f);
		this.dataManager.register(ROT_Z, 0.0f);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("ThrowerUUID")) {
			this.throwerUUID = compound.getString("ThrowerUUID");
		}
		this.setHasCollided(compound.getBoolean("HasCollided"));
		this.setRotX(compound.getFloat("RotX"));
		this.setRotY(compound.getFloat("RotY"));
		this.setRotZ(compound.getFloat("RotZ"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (this.throwerUUID != null) {
			compound.setString("ThrowerUUID", this.throwerUUID);
		}
		compound.setBoolean("HasCollided", this.hasCollided());
		compound.setFloat("RotX", this.getRotX());
		compound.setFloat("RotY", this.getRotY());
		compound.setFloat("RotZ", this.getRotZ());
	}

	public String getThrowerUUID() {
		return throwerUUID;
	}

	public boolean hasCollided() {
		return this.dataManager.get(HAS_COLLIDED);
	}

	public void setHasCollided(boolean hasCollided) {
		this.dataManager.set(HAS_COLLIDED, hasCollided);
	}

	public float getRotX() {
		return this.dataManager.get(ROT_X);
	}

	public void setRotX(float rotX) {
		this.dataManager.set(ROT_X, rotX);
	}

	public float getRotY() {
		return this.dataManager.get(ROT_Y);
	}

	public void setRotY(float rotY) {
		this.dataManager.set(ROT_Y, rotY);
	}

	public float getRotZ() {
		return this.dataManager.get(ROT_Z);
	}

	public void setRotZ(float rotZ) {
		this.dataManager.set(ROT_Z, rotZ);
	}

	public abstract String getEntityTextureName();

	public abstract void getDetonationEffect(World world, BlockPos pos);

	@SideOnly(Side.CLIENT)
	public void spawnParticles(World world, BlockPos pos) {
	}
}