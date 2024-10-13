package net.thep2wking.oedldoedltechnology.util;

import java.util.Random;

/**
 * @author TheP2WKing
 */
public enum ModPowerSlugColor {
	BLUE("blue", 45),
	YELLOW("yellow", 35),
	PURPLE("purple", 20),
	;

	private String name;
	private int weight;

	ModPowerSlugColor(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public static ModPowerSlugColor fromName(String name) {
		for (ModPowerSlugColor color : values()) {
			if (color.name.equals(name)) {
				return color;
			}
		}
		return BLUE;
	}

	public static ModPowerSlugColor getRandomColor() {
		int totalWeight = 0;
		for (ModPowerSlugColor color : values()) {
			totalWeight += color.getWeight();
		}
		int random = new Random().nextInt(totalWeight);
		int currentWeight = 0;
		for (ModPowerSlugColor color : values()) {
			currentWeight += color.getWeight();
			if (random < currentWeight) {
				return color;
			}
		}
		return BLUE;
	}
}