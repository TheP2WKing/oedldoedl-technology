package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraftforge.items.IItemHandlerModifiable;

public class UpgradeItemHandler extends SubItemHandler {
	public UpgradeItemHandler(IItemHandlerModifiable compose, int minSlot, int maxSlotExclusive) {
		super(compose, minSlot, maxSlotExclusive);
		getSlotLimit(1);
	}
}