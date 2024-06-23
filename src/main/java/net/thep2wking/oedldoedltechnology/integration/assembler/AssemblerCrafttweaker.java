package net.thep2wking.oedldoedltechnology.integration.assembler;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.oedldoedl.assembler")
@ZenRegister
public class AssemblerCrafttweaker {
	@ZenMethod
	public static void addRecipe(final IItemStack output1, final IItemStack input1, final IItemStack input2,
			final int time) {
		if (checkNull(output1, false) & checkNull(input1, true) & checkNull(input2, true) & time > 0) {
			CraftTweakerAPI.apply(new AssemblerCrafttweakerAction.Add(output1, input1, input2, time));
		}
	}

	@ZenMethod
	public static void removeRecipe(final IItemStack output1) {
		if (checkNull(output1, false)) {
			CraftTweakerAPI.apply(new AssemblerCrafttweakerAction.Remove(output1));
		}
	}

	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new AssemblerCrafttweakerAction.RemoveAll());
	}

	private static boolean checkNull(Object obj, boolean isInput) {
		if (obj == null) {
			CraftTweakerAPI.logError((isInput ? "Input" : "Output") + " cannot be null");
			return false;
		}
		return true;
	}
}