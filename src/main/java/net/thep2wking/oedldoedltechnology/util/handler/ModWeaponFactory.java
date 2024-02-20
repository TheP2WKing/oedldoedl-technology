package net.thep2wking.oedldoedltechnology.util.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.data.WeightedRandomItemStack;
import matteroverdrive.items.includes.EnergyContainer;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.items.weapon.module.WeaponModuleBarrel;
import matteroverdrive.util.MOStringHelper;
import matteroverdrive.util.WeaponHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.thep2wking.oedldoedltechnology.init.ModItems;

public class ModWeaponFactory {
	public static final int MAX_LOOT_LEVEL = 3;
	public final List<WeightedRandomItemStack> weapons;
	public final List<WeightedRandomWeaponModule> barrelModules;
	public final List<WeightedRandomWeaponModule> batteryModules;
	public final List<WeightedRandomWeaponModule> otherModules;
	private final Random random;

	public ModWeaponFactory() {
		this.random = new Random();
		this.barrelModules = new ArrayList<>();
		this.batteryModules = new ArrayList<>();
		this.otherModules = new ArrayList<>();
		this.weapons = new ArrayList<>();
	}

	public static int getTotalModulesWeight(Collection<WeightedRandomWeaponModule> collection,
			WeaponGenerationContext context) {
		int i = 0;
		for (WeightedRandomWeaponModule module : collection) {
			if (module.fits(context)) {
				i += module.itemWeight;
			}
		}
		return i;
	}

	public static WeightedRandomWeaponModule getItem(Collection<WeightedRandomWeaponModule> par1Collection, int weight,
			WeaponGenerationContext context) {
		int j = weight;
		Iterator<WeightedRandomWeaponModule> iterator = par1Collection.iterator();
		WeightedRandomWeaponModule module = null;
		do {
			if (!iterator.hasNext()) {
				return null;
			}
			WeightedRandomWeaponModule temp = iterator.next();
			if (temp.fits(context)) {
				module = temp;
				j -= temp.itemWeight;
			}
		} while (j >= 0);
		return module;
	}

	public static WeightedRandomWeaponModule getRandomModule(Random random,
			Collection<WeightedRandomWeaponModule> collection, WeaponGenerationContext context) {
		return getItem(collection, random.nextInt(getTotalModulesWeight(collection, context) + 1), context);
	}

	public void initModules() {
		barrelModules.add(new WeightedRandomWeaponModule(null, 200, 0, MAX_LOOT_LEVEL));
		barrelModules.add(new WeightedRandomWeaponModule(
				new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, WeaponModuleBarrel.DAMAGE_BARREL_ID), 100,
				1, MAX_LOOT_LEVEL));
		barrelModules.add(new WeightedRandomWeaponModule(
				new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, WeaponModuleBarrel.FIRE_BARREL_ID), 10, 1,
				MAX_LOOT_LEVEL));
		barrelModules.add(new WeightedRandomWeaponModule(
				new ItemStack(MatterOverdrive.ITEMS.weapon_module_barrel, 1, WeaponModuleBarrel.EXPLOSION_BARREL_ID), 5,
				2, MAX_LOOT_LEVEL));

		batteryModules.add(
				new WeightedRandomWeaponModule(new ItemStack(MatterOverdrive.ITEMS.battery), 100, 1, MAX_LOOT_LEVEL));
		batteryModules.add(
				new WeightedRandomWeaponModule(new ItemStack(MatterOverdrive.ITEMS.hc_battery), 20, 1, MAX_LOOT_LEVEL));

		otherModules.add(new WeightedRandomWeaponModule(null, 300, 0, MAX_LOOT_LEVEL));
		otherModules.add(new WeightedRandomWeaponModule(new ItemStack(MatterOverdrive.ITEMS.sniperScope), 10, 1,
				MAX_LOOT_LEVEL));
	}

	public void initWeapons() {
		weapons.add(new WeightedRandomItemStack(new ItemStack(ModItems.RAILGUN), 30));
		weapons.add(new WeightedRandomItemStack(new ItemStack(ModItems.UP_N_ATOMIZER), 40));
	}

	public ItemStack getRandomDecoratedEnergyWeapon(WeaponGenerationContext context) {
		ItemStack weapon = getRandomEnergyWeapon(context);
		context.setWeaponStack(weapon);
		decorateWeapon(weapon, context);
		if (context.fullCharge) {
			((EnergyContainer) EnergyWeapon.getStorage(weapon)).setFull();
		}
		return weapon;
	}

	public ItemStack getRandomEnergyWeapon(WeaponGenerationContext context) {
		ItemStack weapon;
		weapon = WeightedRandom.getRandomItem(random, weapons).getStack();
		if (context.fullCharge) {
			((EnergyContainer) EnergyWeapon.getStorage(weapon)).setFull();
		}
		return weapon;
	}

	public void decorateWeapon(ItemStack weapon, WeaponGenerationContext context) {
		WeightedRandomWeaponModule barrelModule = null;
		if (context.barrel) {
			barrelModule = getRandomModule(random, barrelModules, context);
			if (barrelModule != null && barrelModule.getWeaponModule() != null) {
				WeaponHelper.setModuleAtSlot(barrelModule.getModuleSlot(), weapon, barrelModule.getWeaponModule());
			}
		}
		if (context.battery) {
			WeightedRandomWeaponModule battery = getRandomModule(random, batteryModules, context);
			if (battery != null && battery.getWeaponModule() != null) {
				WeaponHelper.setModuleAtSlot(battery.getModuleSlot(), weapon, battery.getWeaponModule());
			}
		}
		if (context.other) {
			WeightedRandomWeaponModule other = getRandomModule(random, otherModules, context);
			if (other != null) {
				if (other.getWeaponModule() != null) {
					WeaponHelper.setModuleAtSlot(other.getModuleSlot(), weapon, other.getWeaponModule());
				}
			} else {
				if (barrelModule != null && barrelModule.weaponModule != null) {
					setColorModuleBasedOnBarrel(weapon, barrelModule.weaponModule);
				}
			}
		}
		if (context.legendary) {
			modifyToLegendary(weapon, context);
		}
	}

	public void setColorModuleBasedOnBarrel(ItemStack weapon, ItemStack barrel) {
		if (barrel.getItem() instanceof WeaponModuleBarrel) {
			if (barrel.getItemDamage() == WeaponModuleBarrel.EXPLOSION_BARREL_ID) {
				WeaponHelper.setModuleAtSlot(1, weapon, new ItemStack(MatterOverdrive.ITEMS.weapon_module_color, 1, 6));
			} else if (barrel.getItemDamage() == WeaponModuleBarrel.FIRE_BARREL_ID) {
				WeaponHelper.setModuleAtSlot(1, weapon, new ItemStack(MatterOverdrive.ITEMS.weapon_module_color, 1));
			} else if (barrel.getItemDamage() == WeaponModuleBarrel.DAMAGE_BARREL_ID) {
				WeaponHelper.setModuleAtSlot(1, weapon, new ItemStack(MatterOverdrive.ITEMS.weapon_module_color, 1, 2));
			}
		}
	}

	public void modifyToLegendary(ItemStack weapon, WeaponGenerationContext context) {
		weapon.setStackDisplayName(Reference.UNICODE_LEGENDARY + " " + TextFormatting.GOLD
				+ MOStringHelper.translateToLocal("rarity.legendary") + " " + weapon.getDisplayName());
		int damageLevel = random.nextInt(context.level + 1);
		if (damageLevel > 0) {
			weapon.getTagCompound().setFloat(EnergyWeapon.CUSTOM_DAMAGE_MULTIPLY_TAG, 1 + 0.1f * damageLevel);
		}
		int accuracyLevel = random.nextInt(context.level + 1);
		if (accuracyLevel > 0) {
			weapon.getTagCompound().setFloat(EnergyWeapon.CUSTOM_ACCURACY_MULTIPLY_TAG, 1f - (0.1f * accuracyLevel));
		}
		int shootCooldownLevel = random.nextInt(context.level + 1);
		if (shootCooldownLevel > 0) {
			weapon.getTagCompound().setFloat(EnergyWeapon.CUSTOM_SPEED_MULTIPLY_TAG, 1f - (0.05f * shootCooldownLevel));
		}
		int rangeLevel = random.nextInt(context.level + 1);
		if (rangeLevel > 0) {
			weapon.getTagCompound().setFloat(EnergyWeapon.CUSTOM_RANGE_MULTIPLY_TAG, 1f + 0.15f * rangeLevel);
		}
	}

	public void setSeed(long seed) {
		random.setSeed(seed);
	}

	public static class WeaponGenerationContext {
		public final int level;
		public Entity entity;
		public boolean legendary;
		public boolean fullCharge = true;
		public boolean barrel = true;
		public boolean battery = true;
		public boolean other = true;
		public ItemStack weaponStack;

		public WeaponGenerationContext(int level) {
			this.level = level;
		}

		public WeaponGenerationContext(int level, Entity entity) {
			this(level);
			this.entity = entity;
		}

		public WeaponGenerationContext(int level, Entity entity, boolean legendary) {
			this(level, entity);
			this.legendary = legendary;
		}

		public void setWeaponStack(ItemStack weaponStack) {
			this.weaponStack = weaponStack;
		}
	}

	public static class WeightedRandomWeaponModule extends WeightedRandom.Item {
		private final ItemStack weaponModule;
		private int minLevel;
		private int maxLevel;
		private boolean legendary;
		private int slotID;

		public WeightedRandomWeaponModule(ItemStack weaponModule, int weight, int minLevel, int maxLevel) {
			super(weight);
			if (weaponModule != null) {
				if (weaponModule.getItem() instanceof IWeaponModule) {
					this.slotID = ((IWeaponModule) weaponModule.getItem()).getSlot(weaponModule);
				} else if (weaponModule.hasCapability(CapabilityEnergy.ENERGY, null)) {
					this.slotID = Reference.MODULE_BATTERY;
				}
			}
			this.weaponModule = weaponModule;
			this.minLevel = minLevel;
			this.maxLevel = maxLevel;
		}

		public int getModuleSlot() {
			return slotID;
		}

		@Nonnull
		public ItemStack getWeaponModule() {
			if (this.weaponModule != null) {
				return this.weaponModule.copy();
			}
			return ItemStack.EMPTY;
		}

		public boolean fits(WeaponGenerationContext context) {
			if (weaponModule == null || weaponModule.isEmpty()) {
				return false;
			}
			boolean weaponSupportModule = false;
			if (context.weaponStack != null && !context.weaponStack.isEmpty()
					&& context.weaponStack.getItem() instanceof IWeapon) {
				weaponSupportModule = context.weaponStack.getItem() instanceof IWeapon
						&& ((IWeapon) context.weaponStack.getItem()).supportsModule(context.weaponStack, weaponModule);
			}
			if (legendary && !context.legendary) {
				return weaponSupportModule;
			}
			return context.level >= minLevel && context.level <= maxLevel && weaponSupportModule;
		}
	}
}