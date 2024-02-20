package net.thep2wking.oedldoedltechnology.config.categories;

import net.minecraftforge.common.config.Config;

public class Content {
	@Config.Name("Spawn Republican Space Ranger")
	public boolean SPAWN_REPUBLICAN_SPACE_RANGER = true;

	@Config.Name("railgun")
	public final Railgun RAILGUN = new Railgun();

	public static class Railgun {
		@Config.Name("projectile")
		public final RailgunProjectile RAILGUN_PROJECTILE = new RailgunProjectile();

		@Config.Name("Range")
		@Config.RangeInt(min = 0, max = 128)
		public int RANGE = 64;

		@Config.Name("Cooldown")
		@Config.RangeInt(min = 0, max = 256)
		public int COOLDOWN = 100;

		@Config.Name("Damage")
		@Config.RangeInt(min = 0, max = 64)
		public int DAMAGE = 5;

		@Config.Name("Max Use Time")
		@Config.RangeInt(min = 0, max = 1024)
		public int MAX_USE_TIME = 512;

		@Config.Name("Shot Speed")
		@Config.RangeInt(min = 0, max = 32)
		public int SHOT_SPEED = 8;

		@Config.Name("Zoom")
		@Config.RangeDouble(min = 0, max = 8)
		public double ZOOM = 0.1;

		@Config.Name("Max Heat")
		@Config.RangeInt(min = 0, max = 100)
		public int MAX_HEAT = 100;

		@Config.Name("Max Energy")
		@Config.RangeInt(min = 0, max = Integer.MAX_VALUE)
		public int MAX_ENERGY = 64000;

		@Config.Name("Energy Per Shot")
		@Config.RangeInt(min = 0, max = Integer.MAX_VALUE)
		public int ENERGY_PER_SHOT = 4096;
	}

	public static class RailgunProjectile {
		@Config.Name("Explosion Strength")
		@Config.RangeInt(min = 0, max = 128)
		public int EXPLOSION_STRENGTH = 5;

		@Config.Name("Explosion Damage")
		public boolean EXPLOSION_DAMAGE = true;

		@Config.Name("Explosion Fire")
		public boolean EXPLOSION_FIRE = false;
	}

	@Config.Name("up_n_atomizer")
	public final UpNAtomizer UP_N_ATOMIZER = new UpNAtomizer();

	public static class UpNAtomizer {
		@Config.Name("projectile")
		public final UpNAtomizerProjectile UP_N_ATOMIZER_PROJECTILE = new UpNAtomizerProjectile();

		@Config.Name("Range")
		@Config.RangeInt(min = 0, max = 128)
		public int RANGE = 24;

		@Config.Name("Cooldown")
		@Config.RangeInt(min = 0, max = 256)
		public int COOLDOWN = 75;

		@Config.Name("Damage")
		@Config.RangeInt(min = 0, max = 64)
		public int DAMAGE = 3;

		@Config.Name("Max Use Time")
		@Config.RangeInt(min = 0, max = 1024)
		public int MAX_USE_TIME = 256;

		@Config.Name("Shot Speed")
		@Config.RangeInt(min = 0, max = 32)
		public int SHOT_SPEED = 2;

		@Config.Name("Zoom")
		@Config.RangeDouble(min = 0, max = 8)
		public double ZOOM = 0.15;

		@Config.Name("Max Heat")
		@Config.RangeInt(min = 0, max = 100)
		public int MAX_HEAT = 100;

		@Config.Name("Max Energy")
		@Config.RangeInt(min = 0, max = Integer.MAX_VALUE)
		public int MAX_ENERGY = 64000;

		@Config.Name("Energy Per Shot")
		@Config.RangeInt(min = 0, max = Integer.MAX_VALUE)
		public int ENERGY_PER_SHOT = 2048;
	}

	public static class UpNAtomizerProjectile {
		@Config.Name("Motion Strength")
		@Config.RangeDouble(min = 0, max = 8)
		public double MOTION_STRENGTH = 0.6;

		@Config.Name("Radius")
		@Config.RangeInt(min = 0, max = 8)
		public int RADIUS = 3;

		@Config.Name("Potion Effects")
		public boolean POTION_EFFECTS = true;
	}
}