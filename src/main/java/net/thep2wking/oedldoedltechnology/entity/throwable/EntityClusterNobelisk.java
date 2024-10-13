package net.thep2wking.oedldoedltechnology.entity.throwable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thep2wking.oedldoedltechnology.api.nobelisk.ModEntityNobeliskBase;

public class EntityClusterNobelisk extends ModEntityNobeliskBase {
	public EntityClusterNobelisk(World worldIn) {
		super(worldIn);
	}

	public EntityClusterNobelisk(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityClusterNobelisk(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public String getEntityTextureName() {
		return "cluster_nobelisk";
	}

	@Override
	public void getDetonationEffect(World world, BlockPos pos) {
		this.world.newExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 5.0F, false, true);

		spawnCluster(world, pos, 1.0, 0.0);
		spawnCluster(world, pos, -1.0, 0.0);
		spawnCluster(world, pos, 0.0, 1.0);
		spawnCluster(world, pos, 0.0, -1.0);
	}

	private void spawnCluster(World world, BlockPos pos, double motionX, double motionZ) {
		EntityClusterExplosion cluster = new EntityClusterExplosion(world, this.getThrower());
		cluster.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		cluster.motionX = motionX * 0.25;
		cluster.motionY = 0.65;
		cluster.motionZ = motionZ * 0.25;
		world.spawnEntity(cluster);
	}
}