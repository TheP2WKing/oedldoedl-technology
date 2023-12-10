package net.thep2wking.oedldoedltechnology.api;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.client.sound.WeaponSound;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.entity.EntityRailgunBolt;
import net.thep2wking.oedldoedltechnology.util.handler.ModClientWeaponHandler;
import net.thep2wking.oedldoedltechnology.util.proxy.ClientProxy;

public class ModItemEnergyWeaponBase extends EnergyWeapon {
	public final String modid;
	public final String name;
	public final CreativeTabs tab;
	public final int range;
	public final int cooldown;
	public final int damage;
	public final int explosionDamage;
	public final int maxUseTime;
	public final int shotSpeed;
	public final float zoom;
	public final int maxHeat;
	public final int maxEnergy;
	public final int energyPerShot;
	public final EnumRarity rarity;
	public final boolean hasEffect;

	public ModItemEnergyWeaponBase(String modid, String name, CreativeTabs tab, int range, int cooldown, int damage,
			int explosionDamage, int maxUseTime, int shotSpeed, float zoom, int maxHeat, int maxEnergy,
			int energyPerShot, EnumRarity rarity, boolean hasEffect) {
		super(name, range);
		this.modid = modid;
		this.name = name;
		this.tab = tab;
		this.range = range;
		this.cooldown = cooldown;
		this.damage = damage;
		this.explosionDamage = explosionDamage;
		this.maxUseTime = maxUseTime;
		this.shotSpeed = shotSpeed;
		this.zoom = zoom;
		this.maxHeat = maxHeat;
		this.maxEnergy = maxEnergy;
		this.energyPerShot = energyPerShot;
		this.rarity = rarity;
		this.hasEffect = hasEffect;
		setUnlocalizedName(this.modid + "." + this.name);
		setCreativeTab(this.tab);
		this.bFull3D = true;
		this.leftClickFire = true;
		MinecraftForge.EVENT_BUS.register(this);
	}

	private boolean isBeaconPayment;

	public Item setBeaconPayment() {
		isBeaconPayment = CoreConfig.PROPERTIES.BEACONS.BEACON_PAYMENTS;
		return this;
	}

	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return isBeaconPayment;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		if (!stack.isItemEnchanted() && CoreConfig.PROPERTIES.COLORFUL_RARITIES) {
			return this.rarity;
		} else if (stack.isItemEnchanted()) {
			switch (this.rarity) {
				case COMMON:
				case UNCOMMON:
					return EnumRarity.RARE;
				case RARE:
					return EnumRarity.EPIC;
				case EPIC:
				default:
					return this.rarity;
			}
		}
		return EnumRarity.COMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if (CoreConfig.PROPERTIES.ENCHANTMENT_EFFECTS) {
			return this.hasEffect || stack.isItemEnchanted();
		}
		return stack.isItemEnchanted();
	}

	@Override
	public int getCapacity() {
		return maxEnergy;
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
	public int getMaxItemUseDuration(ItemStack item) {
		return maxUseTime;
	}

	@Override
	public boolean isAlwaysEquipped(ItemStack weapon) {
		return true;
	}

	@Override
	public int getBaseShootCooldown(ItemStack weapon) {
		return cooldown;
	}

	@Override
	public float getBaseZoom(ItemStack weapon, EntityLivingBase shooter) {
		return zoom;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isWeaponZoomed(EntityLivingBase entityPlayer, ItemStack weapon) {
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			return entityPlayer.isHandActive() && entityPlayer.getActiveHand() == EnumHand.MAIN_HAND;
		}
		return false;
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
	public WeaponSound getFireSound(ItemStack weapon, EntityLivingBase entity) {
		return null;
	}

	@Override
	@SuppressWarnings("all")
	public void addCustomDetails(ItemStack weapon, EntityPlayer player, List infos) {
	}

	@Override
	public int getBaseEnergyUse(ItemStack item) {
		return energyPerShot / getBaseShootCooldown(item);
	}

	@Override
	public int getBaseMaxHeat(ItemStack item) {
		return maxHeat;
	}

	@Override
	public float getWeaponBaseDamage(ItemStack weapon) {
		return damage;
	}

	@Override
	public boolean canFire(ItemStack itemStack, World world, EntityLivingBase shooter) {
		return !isOverheated(itemStack) && DrainEnergy(itemStack, getShootCooldown(itemStack), true)
				&& !isEntitySpectator(shooter);
	}

	@Override
	public float getShotSpeed(ItemStack weapon, EntityLivingBase shooter) {
		return shotSpeed;
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
	@SideOnly(Side.CLIENT)
	public void onShooterClientUpdate(ItemStack itemStack, World world, EntityPlayer entityPlayer,
			boolean sendServerTick) {
		if (Mouse.isButtonDown(0) && this.hasShootDelayPassed()) {
			if (canFire(itemStack, world, entityPlayer)) {
				Vec3d dir = entityPlayer.getLook(1);
				Vec3d pos = getFirePosition(entityPlayer, dir, isWeaponZoomed(entityPlayer, itemStack));
				WeaponShot shot = createClientShot(itemStack, entityPlayer,
						isWeaponZoomed(entityPlayer, itemStack));
				this.onClientShot(itemStack, entityPlayer, pos, dir, shot);
				this.addShootDelay(itemStack);
				this.sendShootTickToServer(world, shot, dir, pos);
				if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
					if (isWeaponZoomed(entityPlayer, itemStack)) {
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(2f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 0.5f);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(3 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 3), 1);
					} else {
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(4f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 1);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
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

	public EntityRailgunBolt spawnProjectileV2(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		EntityRailgunBolt fire = getDefaultProjectileV2(weapon, shooter, position, dir, shot);
		shooter.world.spawnEntity(fire);
		if (shooter.world.isRemote && shooter instanceof EntityPlayer) {
			((ModClientWeaponHandler) OedldoedlTechnology.PROXY.getModWeaponHandler()).addPlasmaBolt(fire);
		}
		return fire;
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
		EntityRailgunBolt fire = spawnProjectileV2(weapon, shooter, position, dir, shot);
		fire.simulateDelay(delay);
		return true;
	}

	@Override
	public boolean supportsModule(int arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public boolean supportsModule(ItemStack arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public void onClientShot(ItemStack arg0, EntityLivingBase arg1, Vec3d arg2, Vec3d arg3, WeaponShot arg4) {
	}

	@Override
	public void onProjectileHit(RayTraceResult arg0, ItemStack arg1, World arg2, float arg3) {
	}

	public EntityRailgunBolt getDefaultProjectileV2(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		EntityRailgunBolt bolt = new EntityRailgunBolt(shooter.world, shooter, position, dir, shot,
				getShotSpeed(weapon, shooter));
		bolt.setKnockBack(1);
		bolt.setExplodeMultiply(explosionDamage);
		return bolt;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack) {
		return EnumAction.BOW;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void manageClientServerTicks(World world) {
		ClientProxy.instance().getModClientWeaponHandler().sendWeaponTickToServer(world, (PacketFirePlasmaShot) null);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void sendShootTickToServer(World world, WeaponShot weaponShot, Vec3d dir, Vec3d pos) {
		PacketFirePlasmaShot packetFirePlasmaShot = new PacketFirePlasmaShot(
				Minecraft.getMinecraft().player.getEntityId(), pos, dir, weaponShot);
		ClientProxy.instance().getModClientWeaponHandler().sendWeaponTickToServer(world, packetFirePlasmaShot);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addShootDelay(ItemStack weaponStack) {
		ClientProxy.instance().getModClientWeaponHandler().addShootDelay(this, weaponStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasShootDelayPassed() {
		return ClientProxy.instance().getModClientWeaponHandler().shootDelayPassed(this);
	}
}