package net.thep2wking.oedldoedltechnology.content.item;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.client.sound.MOPositionedSound;
import matteroverdrive.client.sound.WeaponSound;
import matteroverdrive.entity.weapon.PlasmaBolt;
import matteroverdrive.init.MatterOverdriveSounds;
import matteroverdrive.items.weapon.module.WeaponModuleBarrel;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.api.ModItemEnergyWeaponBase;

import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class ItemRailgun extends ModItemEnergyWeaponBase {
	public ItemRailgun(String modid, String name, CreativeTabs tab, int range, int heatPerShot, int maxHeat,
			int maxUseTime, int energyPerShot, int energyCapacity, int cooldown, int damage, int shotSpeed, float zoom,
			EnumRarity rarity, boolean hasEffect) {
		super(modid, name, tab, range, heatPerShot, maxHeat, maxUseTime, energyPerShot, energyCapacity, cooldown,
				damage,
				shotSpeed, zoom, rarity, hasEffect);
				setFull3D();
				leftClickFire = true;
				setUnlocalizedName(this.modid + "." + this.name);
				setRegistryName(this.modid + ":" + this.name);
				setCreativeTab(this.tab);
	}

	public static final int RANGE = 64;
	public static final int MAX_HEAT = 50;
	public static final int MAX_USE_TIME = 512;
	public static final int ENERGY_PER_SHOT = 4096;


	@Override
	public int getCapacity() {
		return 64000;
	}

	@Override
	public int getInput() {
		return 128;
	}

	@Override
	public int getOutput() {
		return 128;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addCustomDetails(ItemStack weapon, EntityPlayer player, List infos) {

	}

	@Override
	public int getBaseEnergyUse(ItemStack item) {
		return ENERGY_PER_SHOT / getShootCooldown(item);
	}

	@Override
	public int getBaseMaxHeat(ItemStack item) {
		return MAX_HEAT;
	}

	@Override
	public float getWeaponBaseDamage(ItemStack weapon) {
		return 6;
	}

	@Override
	public float getWeaponBaseAccuracy(ItemStack weapon, boolean zoomed) {
		if (zoomed) {
			return 1f + getHeat(weapon) * 0.1f;
		} else {
			return 5 + getHeat(weapon) * 0.3f;
		}
	}

	@Override
	public boolean canFire(ItemStack weapon, World world, EntityLivingBase shooter) {
		return DrainEnergy(weapon, getShootCooldown(weapon), true) && !isOverheated(weapon)
				&& !isEntitySpectator(shooter);
	}

	@Override
	public float getShotSpeed(ItemStack weapon, EntityLivingBase shooter) {
		return 8;
	}

	@Override
	public int getBaseShootCooldown(ItemStack weapon) {
		return 50;
	}

	@Override
	public float getBaseZoom(ItemStack weapon, EntityLivingBase shooter) {
		return 0.1f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onShooterClientUpdate(ItemStack itemStack, World world, EntityPlayer entityPlayer,
			boolean sendServerTick) {
		if (Mouse.isButtonDown(0) && hasShootDelayPassed()) {
			if (canFire(itemStack, world, entityPlayer)) {
				Vec3d dir = entityPlayer.getLook(1);
				Vec3d pos = getFirePosition(entityPlayer, dir, isWeaponZoomed(entityPlayer, itemStack));
				WeaponShot shot = createClientShot(itemStack, entityPlayer, isWeaponZoomed(entityPlayer, itemStack));
				onClientShot(itemStack, entityPlayer, pos, dir, shot);
				addShootDelay(itemStack);
				sendShootTickToServer(world, shot, dir, pos);
				if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
					if (isWeaponZoomed(entityPlayer, itemStack)) {
						ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(2f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 0.5f);
						ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(3 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 3), 1);
					} else {
						ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(4f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 1);
						ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(4 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 4), 1);
					}
				}
				return;
			} else if (needsRecharge(itemStack)) {
				chargeFromEnergyPack(itemStack, entityPlayer);
			}
		}

		super.onShooterClientUpdate(itemStack, world, entityPlayer, sendServerTick);
	}

	@SideOnly(Side.CLIENT)
	private Vec3d getFirePosition(EntityPlayer entityPlayer, Vec3d dir, boolean isAiming) {
		Vec3d pos = entityPlayer.getPositionEyes(1);
		if (!isAiming) {
			pos = pos.subtract((double) (MathHelper.cos(entityPlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F),
					0, (double) (MathHelper.sin(entityPlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F));
		}
		pos = pos.addVector(dir.x, dir.y, dir.z);
		return pos;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientShot(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir, WeaponShot shot) {
		MOPositionedSound sound = new MOPositionedSound(MatterOverdriveSounds.weaponsSniperRifleFire,
				SoundCategory.PLAYERS, 3f + itemRand.nextFloat() * 0.5f, 0.9f + itemRand.nextFloat() * 0.2f);
		sound.setPosition((float) position.x, (float) position.y, (float) position.z);
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		spawnProjectile(weapon, shooter, position, dir, shot);
	}

	@Override
	public void onProjectileHit(RayTraceResult hit, ItemStack weapon, World world, float amount) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vector2f getSlotPosition(int slot, ItemStack weapon) {
		switch (slot) {
		case Reference.MODULE_BATTERY:
			return new Vector2f(170, 115);
		case Reference.MODULE_COLOR:
			return new Vector2f(60, 45);
		case Reference.MODULE_BARREL:
			return new Vector2f(60, 115);
		case Reference.MODULE_SIGHTS:
			return new Vector2f(150, 35);
		default:
			return new Vector2f(205, 80 + ((slot - Reference.MODULE_OTHER) * 22));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vector2f getModuleScreenPosition(int slot, ItemStack weapon) {
		switch (slot) {
		case Reference.MODULE_BATTERY:
			return new Vector2f(165, 80);
		case Reference.MODULE_COLOR:
			return new Vector2f(110, 90);
		case Reference.MODULE_BARREL:
			return new Vector2f(90, 95);
		case Reference.MODULE_SIGHTS:
			return new Vector2f(150, 72);
		}
		return getSlotPosition(slot, weapon);
	}

	@Override
	public PlasmaBolt getDefaultProjectile(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		PlasmaBolt bolt = new PlasmaBolt(shooter.world, shooter, position, dir, shot, getShotSpeed(weapon, shooter)) {
			
		};
		bolt.setKnockBack(1);
		return bolt;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack) {
		return itemStack.getItemDamage() == 1 ? EnumAction.BOW : EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return MAX_USE_TIME;
	}

	@Override
	public boolean supportsModule(int slot, ItemStack weapon) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack stack = playerIn.getHeldItem(hand);
		if (hand == EnumHand.OFF_HAND) {
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		playerIn.setActiveHand(hand);
		if (worldIn.isRemote) {
			for (int i = 0; i < 3; i++) {
				Minecraft.getMinecraft().entityRenderer.itemRenderer.updateEquippedItem();
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean supportsModule(ItemStack weapon, ItemStack module) {
		return module.isEmpty() || !(module.getItem() instanceof IWeaponModule)
				|| ((IWeaponModule) module.getItem()).getSlot(module) != Reference.MODULE_BARREL
				&& module.getItemDamage() != WeaponModuleBarrel.HEAL_BARREL_ID && module.getItemDamage() != WeaponModuleBarrel.EXPLOSION_BARREL_ID;
	}

	@Override
	public boolean onServerFire(ItemStack weapon, EntityLivingBase shooter, WeaponShot shot, Vec3d position, Vec3d dir,
			int delay) {
		if (shooter instanceof EntityPlayer) {
		if (!((EntityPlayer) shooter).capabilities.isCreativeMode) {
			DrainEnergy(weapon, getShootCooldown(weapon), false);
			float newHeat = getHeat(weapon) + getMaxHeat(weapon) * 0.8f;
			setHeat(weapon, newHeat);
			manageOverheat(weapon, shooter.world, shooter);
		}
		}
		PlasmaBolt fire = spawnProjectile(weapon, shooter, position, dir, shot);
		fire.simulateDelay(delay);
		return true;
	}

	@Override
	public boolean isAlwaysEquipped(ItemStack weapon) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isWeaponZoomed(EntityLivingBase entityPlayer, ItemStack weapon) {
		// Fix the requirement to have the button down for a "right click".
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
		return entityPlayer.isHandActive() && entityPlayer.getActiveHand() == EnumHand.MAIN_HAND;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public WeaponSound getFireSound(ItemStack weapon, EntityLivingBase entity) {
		return null;
	}
}
