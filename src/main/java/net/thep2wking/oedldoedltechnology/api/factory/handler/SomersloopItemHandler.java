package net.thep2wking.oedldoedltechnology.api.factory.handler;

import net.minecraftforge.items.IItemHandlerModifiable;

public class SomersloopItemHandler extends SubItemHandler {
	public SomersloopItemHandler(IItemHandlerModifiable compose, int minSlot, int maxSlotExclusive) {
		super(compose, minSlot, maxSlotExclusive);
		getSlotLimit(1);
	}
}