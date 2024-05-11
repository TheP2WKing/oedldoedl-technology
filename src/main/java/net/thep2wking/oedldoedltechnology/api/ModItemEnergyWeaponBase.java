package net.thep2wking.oedldoedltechnology.api;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.api.weapon.WeaponStats;
import matteroverdrive.client.sound.MOPositionedSound;
import matteroverdrive.client.sound.WeaponSound;
import matteroverdrive.init.MatterOverdriveSounds;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import matteroverdrive.util.MOEnergyHelper;
import matteroverdrive.util.WeaponHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedlcore.config.CoreConfig;
import net.thep2wking.oedldoedlcore.util.ModTooltips;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.config.TechnologyConfig;
import net.thep2wking.oedldoedltechnology.util.handler.ModClientWeaponHandler;
import net.thep2wking.oedldoedltechnology.util.proxy.ClientProxy;

public class ModItemEnergyWeaponBase extends EnergyWeapon {
	public final String modid;
	public final String name;
	public final CreativeTabs tab;
	public final int range;
	public final int cooldown;
	public final int damage;
	public final int maxUseTime;
	public final int shotSpeed;
	public final float zoom;
	public final int maxHeat;
	public final int maxEnergy;
	public final int energyPerShot;
	public final EnumRarity rarity;
	public final boolean hasEffect;
	public final int tooltipLines;
	public final int annotationLines;

	public ModItemEnergyWeaponBase(String modid, String name, CreativeTabs tab, int range, int cooldown, int damage,
			int maxUseTime, int shotSpeed, float zoom, int maxHeat, int maxEnergy, int energyPerShot, EnumRarity rarity,
			boolean hasEffect, int tooltipLines, int annotationLines) {
		super(name, range);
		this.modid = modid;
		this.name = name;
		this.tab = tab;
		this.range = range;
		this.cooldown = cooldown;
		this.damage = damage;
		this.maxUseTime = maxUseTime;
		this.shotSpeed = shotSpeed;
		this.zoom = zoom;
		this.maxHeat = maxHeat;
		this.maxEnergy = maxEnergy;
		this.energyPerShot = energyPerShot;
		this.rarity = rarity;
		this.hasEffect = hasEffect;
		this.tooltipLines = tooltipLines;
		this.annotationLines = annotationLines;
		setUnlocalizedName(this.modid + "." + this.name);
		setCreativeTab(this.tab);
		this.bFull3D = true;
		this.leftClickFire = true;
		MinecraftForge.EVENT_BUS.register(this);
	}

	private final DecimalFormat damageFormater = new DecimalFormat("#.##");
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
	public Vec3d getFirePosition(EntityPlayer entityPlayer, Vec3d dir, boolean isAiming) {
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
								.setRecoil(3f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 0.5f);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(4 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 3), 1);
					} else {
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setRecoil(6f + Math.min(2, getAccuracy(itemStack, entityPlayer, true)), 1, 1);
						matteroverdrive.proxy.ClientProxy.instance().getClientWeaponHandler()
								.setCameraRecoil(6 + ((getHeat(itemStack) / getMaxHeat(itemStack)) * 4), 1);
					}
				}
				return;
			} else if (needsRecharge(itemStack)) {
				chargeFromEnergyPack(itemStack, entityPlayer);
			}
		}
		super.onShooterClientUpdate(itemStack, world, entityPlayer, sendServerTick);
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
		ModEntityPlasmaShotBase fire = spawnBolt(weapon, shooter, position, dir, shot);
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
	public void onProjectileHit(RayTraceResult arg0, ItemStack arg1, World arg2, float arg3) {
	}

	public ModEntityPlasmaShotBase setBolt(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		ModEntityPlasmaShotBase bolt = new ModEntityPlasmaShotBase(shooter.world, shooter, position, dir, shot,
				getShotSpeed(weapon, shooter));
		bolt.setKnockBack(1);
		return bolt;
	}

	public ModEntityPlasmaShotBase spawnBolt(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir,
			WeaponShot shot) {
		ModEntityPlasmaShotBase fire = setBolt(weapon, shooter, position, dir, shot);
		shooter.world.spawnEntity(fire);
		if (shooter.world.isRemote && shooter instanceof EntityPlayer) {
			((ModClientWeaponHandler) OedldoedlTechnology.PROXY.getModWeaponHandler()).addPlasmaBolt(fire);
		}
		return fire;
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

	public SoundEvent setShotSound() {
		return MatterOverdriveSounds.weaponsPhaserRifleShot;
	}

	public String addStatWithMultiplyInfo(String statName, Object value, double multiply, String units) {
		String info = String.format("%s: %s%s", statName, TextFormatting.DARK_AQUA, value);
		if (!units.isEmpty()) {
			info = info + " " + units;
		}
		if (multiply != 1.0) {
			if (multiply > 0.002) {
				info = info + TextFormatting.DARK_GREEN;
			} else {
				info = info + TextFormatting.DARK_RED;
			}
			info = info + String.format(" (%s) %s", DecimalFormat.getPercentInstance().format(multiply),
					TextFormatting.RESET);
		}
		return info;
	}

	@SuppressWarnings("all")
	public void addModuleDetails(ItemStack weapon, List infos) {
		ItemStack module = WeaponHelper.getModuleAtSlot(2, weapon);
		if (!module.isEmpty()) {
		}
	}

	public ModEntityPlasmaShotBase getDefaultProjectileNew(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir, WeaponShot shot) {
		ModEntityPlasmaShotBase fire = new ModEntityPlasmaShotBase(shooter.world, shooter, position, dir, shot, this.getShotSpeed(weapon, shooter));
		fire.setWeapon(weapon);
		fire.setFireDamageMultiply(WeaponHelper.modifyStat(WeaponStats.FIRE_DAMAGE, weapon, 0.0F));
		float explosionAmount = WeaponHelper.modifyStat(WeaponStats.EXPLOSION_DAMAGE, weapon, 0.0F);
		if (explosionAmount > 0.0F) {
		   fire.setExplodeMultiply(this.getWeaponBaseDamage(weapon) * 0.3F * explosionAmount);
		}
  
		if (WeaponHelper.modifyStat(WeaponStats.RICOCHET, weapon, 0.0F) == 1.0F) {
		   fire.markRicochetable();
		}
  
		return fire;
	 }
  
	 public ModEntityPlasmaShotBase spawnProjectileNew(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir, WeaponShot shot) {
		ModEntityPlasmaShotBase fire = this.getDefaultProjectileNew(weapon, shooter, position, dir, shot);
		shooter.world.spawnEntity(fire);
		if (shooter.world.isRemote && shooter instanceof EntityPlayer) {
		   ((ModClientWeaponHandler)OedldoedlTechnology.PROXY.getModWeaponHandler()).addPlasmaBolt(fire);
		}
  
		return fire;
	 }

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientShot(ItemStack weapon, EntityLivingBase shooter, Vec3d position, Vec3d dir, WeaponShot shot) {
		MOPositionedSound sound = new MOPositionedSound(setShotSound(), SoundCategory.PLAYERS,
				3f + itemRand.nextFloat() * 0.5f, 0.9f + itemRand.nextFloat() * 0.2f);
		sound.setPosition((float) position.x, (float) position.y, (float) position.z);
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		spawnProjectileNew(weapon, shooter, position, dir, shot);
	}

	public void addMOWeaponDetails(ItemStack stack, EntityPlayer player, List<String> tooltip) {
		IEnergyStorage storage = getStorage(stack);
		tooltip.add(TextFormatting.YELLOW
				+ MOEnergyHelper.formatEnergy(storage.getEnergyStored(), storage.getMaxEnergyStored()));
		String energyInfo = TextFormatting.DARK_RED + "Power Use: "
				+ MOEnergyHelper.formatEnergy(null, getEnergyUse(stack) * 20) + "/s";
		float energyMultiply = (float) getEnergyUse(stack) / (float) getBaseEnergyUse(stack);
		if (energyMultiply != 1) {
			energyInfo += " (" + DecimalFormat.getPercentInstance().format(energyMultiply) + ")";
		}
		tooltip.add(energyInfo);
		tooltip.add("");
		tooltip.add(addStatWithMultiplyInfo("Damage", damageFormater.format(getWeaponScaledDamage(stack, player)),
				getWeaponScaledDamage(stack, player) / getWeaponBaseDamage(stack) - 1, ""));
		tooltip.add(addStatWithMultiplyInfo("DPS",
				damageFormater.format((getWeaponScaledDamage(stack, player) / getShootCooldown(stack)) * 20), 1, ""));
		tooltip.add(addStatWithMultiplyInfo("Speed", (int) (20d / getShootCooldown(stack) * 60),
				(double) getBaseShootCooldown(stack) / (double) getShootCooldown(stack), " s/m"));
		tooltip.add(addStatWithMultiplyInfo("Range", getRange(stack), (double) getRange(stack) / (double) range, "b"));
		tooltip.add(addStatWithMultiplyInfo("Accuracy", "", 1 / (modifyStatFromModules(WeaponStats.ACCURACY, stack, 1)
				* getCustomFloatStat(stack, CUSTOM_ACCURACY_MULTIPLY_TAG, 1)), ""));
		StringBuilder heatInfo = new StringBuilder(TextFormatting.DARK_RED + "Heat: ");
		double heatPercent = getHeat(stack) / getMaxHeat(stack);
		for (int i = 0; i < 32 * heatPercent; i++) {
			heatInfo.append("|");
		}
		tooltip.add(heatInfo.toString());
		addCustomDetails(stack, player, tooltip);
		addModuleDetails(stack, tooltip);
		tooltip.add("");
	}

	public static final String KEY_WEAPON = "key.oedldoedltechnology.weapon";

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (ModTooltips.showAnnotationTip()) {
			for (int i = 1; i <= annotationLines; ++i) {
				ModTooltips.addAnnotation(tooltip, this.getUnlocalizedName(), i);
			}
		}
		if (ModTooltips.showInfoTip()) {
			for (int i = 1; i <= tooltipLines; ++i) {
				ModTooltips.addInformation(tooltip, this.getUnlocalizedName(), i);
			}
		} else if (ModTooltips.showInfoTipKey() && !(tooltipLines == 0)) {
			ModTooltips.addKey(tooltip, ModTooltips.KEY_INFO);
		}
		if (GuiScreen.isCtrlKeyDown() && TechnologyConfig.TOOLTIPS.WEAPON_INFO_TOOLTIPS) {
			addMOWeaponDetails(stack, Minecraft.getMinecraft().player, tooltip);
		} else if (TechnologyConfig.TOOLTIPS.WEAPON_INFO_TOOLTIPS
				&& TechnologyConfig.TOOLTIPS.WEAPON_INFO_TOOLTIPS_KEY) {
			ModTooltips.addKey(tooltip, KEY_WEAPON);
		}
	}
}