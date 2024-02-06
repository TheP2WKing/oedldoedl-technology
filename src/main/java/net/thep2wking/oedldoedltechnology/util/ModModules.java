package net.thep2wking.oedldoedltechnology.util;

import net.minecraftforge.fml.common.Loader;

public class ModModules {
	public static final String MATTEROVERDRIVE = "matteroverdrive";

	public static boolean isMatterOverdriveLoaded() {
		return Loader.isModLoaded(MATTEROVERDRIVE);
	}
}