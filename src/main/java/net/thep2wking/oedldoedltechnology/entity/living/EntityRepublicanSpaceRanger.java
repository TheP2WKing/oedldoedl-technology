package net.thep2wking.oedldoedltechnology.entity.living;

import javax.annotation.Nullable;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.entity.IRangedEnergyWeaponAttackMob;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.entity.ai.AndroidTargetSelector;
import matteroverdrive.entity.ai.EntityAIMoveAlongPath;
import matteroverdrive.entity.ai.EntityAIPhaserBoltAttack;
import matteroverdrive.entity.ai.EntityAIRangedRunFromMelee;
import matteroverdrive.entity.monster.EntityRogueAndroid;
import matteroverdrive.entity.monster.EntityRougeAndroidMob;
import matteroverdrive.items.includes.EnergyContainer;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.init.ModSounds;
import net.thep2wking.oedldoedltechnology.util.handler.ModWeaponFactory;

public class EntityRepublicanSpaceRanger extends EntityRougeAndroidMob implements IRangedEnergyWeaponAttackMob {
	public static final ResourceLocation LOOT_TABLE = new ResourceLocation(OedldoedlTechnology.MODID, "entities/republican_space_ranger");
	public static final int EXPERIENCE_VALUE = 20;

	public static boolean DROP_NORMAL_WEAPONS = true;
	public static boolean DROP_LEGENDARY_WEAPONS = true;
	public static boolean UNLIMITED_WEAPON_ENERGY = true;
	private final EntityAIPhaserBoltAttack aiBoltAttack = new EntityAIPhaserBoltAttack(this, 1.0, 60, 15.0F);
	private final EntityAIRangedRunFromMelee aiRangedRunFromMelee = new EntityAIRangedRunFromMelee(this, 1.0);
	// private final List<String> NAMES = Arrays.asList("Commander", "Dick", "Butch", "Vanessa", "Luke", "TheF2PKing");

	public EntityRepublicanSpaceRanger(World world) {
		super(world);
		this.init(world);
		this.experienceValue = EXPERIENCE_VALUE;
	}

	public EntityRepublicanSpaceRanger(World world, int level, boolean legendary) {
		super(world, level, legendary);
		this.init(world);
		this.experienceValue = EXPERIENCE_VALUE;
	}

	@Override
	@Nullable
	public ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}

	@Override
	public SoundEvent getDeathSound() {
		return ModSounds.REPUBLICAN_SPACE_RANGER_DEATH;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ModSounds.REPUBLICAN_SPACE_RANGER_IDLE;
	}

	@SuppressWarnings("all")
	public void init(World world) {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiRangedRunFromMelee);
		this.tasks.addTask(3, this.aiBoltAttack);
		this.tasks.addTask(4, new EntityAIMoveAlongPath(this, 1.0));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, true,
				new AndroidTargetSelector(this)));
		if (world != null && !world.isRemote) {
			this.setCombatTask();
		}
		// String name = this.getIsLegendary()
		// 		? String.format("%s %s ", "\u272a", MOStringHelper.translateToLocal("rarity.legendary", new Object[0]))
		// 		: "";
		// name = name + String.format("[%s] ", this.getAndroidLevel());
		// name = name
		// 		+ String.format("[%s] ",
		// 				I18n.format("entity." + OedldoedlTechnology.MODID + ".republican_space_ranger.name"))
		// 		+ ModRandomUtil.selectRandom(rand, NAMES);
		// this.setCustomNameTag(name);
	}

	@Override
	public void dropEquipment(boolean recentlyHit, int lootingLevel) {
		if (EntityRogueAndroid.dropItems && this.recentlyHit > 0 && DROP_NORMAL_WEAPONS) {
			int j = this.rand.nextInt(400) - lootingLevel;
			if (j < 5 || this.getIsLegendary()) {
				this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).copy(), 0.0F);
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		IAttributeInstance movementSpeedAttribute = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		if (movementSpeedAttribute != null) {
			movementSpeedAttribute.setBaseValue(0.25);
		}
		IAttributeInstance followRangeAttribute = this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
		if (followRangeAttribute != null) {
			followRangeAttribute.setBaseValue(24.0);
		}
	}	

	@Override
	public void addRandomArmor() {
		super.addRandomArmor();
		int androidLevel = getAndroidLevel();
		ItemStack gun = OedldoedlTechnology.WEAPON_FACTORY.getRandomDecoratedEnergyWeapon(
			new ModWeaponFactory.WeaponGenerationContext(androidLevel, this, getIsLegendary()));
		if (gun != null) {
			EnergyContainer storage = (EnergyContainer) EnergyWeapon.getStorage(gun);
			if (storage != null && storage instanceof EnergyContainer) {
				storage.setFull();
			}
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, gun);
			IAttributeInstance followRangeAttribute = this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
			if (followRangeAttribute != null && gun.getItem() instanceof EnergyWeapon) {
				followRangeAttribute.setBaseValue((double) (((EnergyWeapon) gun.getItem()).getRange(gun) - 2));
			}
		}
	}

	public void setCombatTask() {
		ItemStack itemstack = this.getHeldItem(EnumHand.MAIN_HAND);
		if (itemstack != null && itemstack.getItem() instanceof EnergyWeapon) {
			this.aiBoltAttack.setMaxChaseDistance(((EnergyWeapon) itemstack.getItem()).getRange(itemstack) - 2);
			if (itemstack.getItem() == MatterOverdrive.ITEMS.ionSniper) {
				this.aiRangedRunFromMelee.setMinDistance(16.0);
			} else if (itemstack.getItem() != MatterOverdrive.ITEMS.plasmaShotgun) {
				this.aiRangedRunFromMelee.setMinDistance(3.0);
			}
		}
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.addRandomArmor();
		this.setEnchantmentBasedOnDifficulty(difficulty);
		return livingdata;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, Vec3d lastSeenPosition, boolean canSee) {
		ItemStack weapon = this.getHeldItem(EnumHand.MAIN_HAND);
		if (!this.world.isRemote) {
			if (lastSeenPosition == null) {
				lastSeenPosition = target.getPositionVector();
			}
			if (weapon.getItem() instanceof EnergyWeapon && weapon != null) {
				EnergyWeapon energyWeapon = (EnergyWeapon) weapon.getItem();
				Vec3d pos = new Vec3d(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
				Vec3d dir = lastSeenPosition.subtract(this.getPositionVector());
				WeaponShot shot = energyWeapon.createServerShot(weapon, this, true);
				float difficulty = MathHelper.clamp(0.2F * (float) this.world.getDifficulty().getDifficultyId(), 0.0F,
						0.6F) + (float) this.getAndroidLevel() * 0.13333334F + (this.getIsLegendary() ? 0.3F : 0.0F);
				shot.setDamage(shot.getDamage() * difficulty);
				difficulty = (float) (3 - this.world.getDifficulty().getDifficultyId()) * 4.0F;
				shot.setAccuracy(shot.getAccuracy() + difficulty);
				energyWeapon.onServerFire(weapon, this, shot, pos, dir, 0);
				energyWeapon.setHeat(weapon, 0.0F);
				if (UNLIMITED_WEAPON_ENERGY) {
					((EnergyContainer) EnergyWeapon.getStorage(weapon)).setFull();
				}
				MatterOverdrive.NETWORK.sendToAllAround(new PacketFirePlasmaShot(this.getEntityId(), pos, dir, shot),
						this.world.provider.getDimension(), this.posX, this.posY, this.posZ, 64.0);
				difficulty = 1.0F + (float) (3 - this.world.getDifficulty().getDifficultyId()) * 0.5F;
				this.aiBoltAttack.setMaxRangedAttackDelay(
						(int) ((float) ((EnergyWeapon) weapon.getItem()).getShootCooldown(weapon) * difficulty));
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		this.setCombatTask();
	}

	@Override
	public ItemStack getWeapon() {
		return this.getHeldItem(EnumHand.MAIN_HAND);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double p_70112_1_) {
		double d1 = this.getEntityBoundingBox().getAverageEdgeLength();
		d1 *= 64.0 * Entity.getRenderDistanceWeight();
		d1 += this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
		return p_70112_1_ < d1 * d1;
	}
}