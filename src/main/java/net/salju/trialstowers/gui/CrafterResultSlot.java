package net.salju.trialstowers.gui;

import net.salju.trialstowers.block.CrafterEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.Container;

public class CrafterResultSlot extends Slot {
	public CrafterResultSlot(Container con, int a, int i, int e) {
		super(con, a, i, e);
	}

	@Override
	public ItemStack getItem() {
		if (this.container instanceof CrafterEntity target) {
			return target.getResultItem();
		}
		return super.getItem();
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack remove(int i) {
		return ItemStack.EMPTY;
	}
}