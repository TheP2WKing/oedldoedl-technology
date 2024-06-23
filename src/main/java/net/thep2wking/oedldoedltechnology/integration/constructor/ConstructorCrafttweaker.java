package net.thep2wking.oedldoedltechnology.integration.constructor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.oedldoedl.constructor")
@ZenRegister
public class ConstructorCrafttweaker {
	@ZenMethod
	public static void addRecipe(final IItemStack output1, final IItemStack input1, final int time) {
		if (checkNull(output1, false) & checkNull(input1, true) & time > 0) {
			CraftTweakerAPI.apply(new ConstructorCrafttweakerAction.Add(output1, input1, time));
		}
	}

	@ZenMethod
	public static void removeRecipe(final IItemStack output1) {
		if (checkNull(output1, false)) {
			CraftTweakerAPI.apply(new ConstructorCrafttweakerAction.Remove(output1));
		}
	}

	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new ConstructorCrafttweakerAction.RemoveAll());
	}

	private static boolean checkNull(Object obj, boolean isInput) {
		if (obj == null) {
			CraftTweakerAPI.logError((isInput ? "Input" : "Output") + " cannot be null");
			return false;
		}
		return true;
	}
}