package net.thep2wking.oedldoedltechnology.entity.throwable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class EntityAlienEgg extends EntityThrowable {
	public EntityAlienEgg(World worldIn) {
		super(worldIn);
	}

	public EntityAlienEgg(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityAlienEgg(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D,
						0.0D, Item.getIdFromItem(ModItems.ALIEN_EGG));
			}
		}
	}

	@Override
	public void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}
}