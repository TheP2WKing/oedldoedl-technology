package net.thep2wking.oedldoedltechnology.entity.throwable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModEntityNobeliskBase;

public class EntityNormalNobelisk extends ModEntityNobeliskBase {
	public EntityNormalNobelisk(World worldIn) {
		super(worldIn);
	}

	public EntityNormalNobelisk(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityNormalNobelisk(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public String getEntityTextureName() {
		return "normal_nobelisk";
	}

	@Override
	public void getDetonationEffect(World world, BlockPos pos) {
		this.world.newExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 5.0F, false, true);
	}
}