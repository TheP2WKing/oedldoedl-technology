package net.thep2wking.oedldoedltechnology.api.nobelisk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ModEntityNobeliskNew extends EntityLivingThrowable {
	public ModEntityNobeliskNew(World world) {
		super(world);
		this.setSize(0.3F, 0.3F);
		this.bounceMultiplier = 0.5f;
		this.gravity = 0.05f;
	}

	public ModEntityNobeliskNew(World world, EntityLivingBase thrower) {
		super(world, thrower);
		this.setSize(0.3F, 0.3F);
		this.bounceMultiplier = 0f;
		this.gravity = 0.05f;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		return source != DamageSource.OUT_OF_WORLD;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (this.onGround) {
			this.onImpact();
			this.motionX *= 0.9D;
			this.motionZ *= 0.9D;
		}
		


	}


	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)

    {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
        {
            this.motionY += entityThrower.motionY;
        }
    }
}