package net.salju.trialstowers.gui;

import net.salju.trialstowers.init.TrialsMenus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.network.FriendlyByteBuf;
import javax.annotation.Nullable;

public class CrafterGuiMenu extends AbstractContainerMenu {
	private final Container crafter;

	public CrafterGuiMenu(int id, Inventory inv, @Nullable FriendlyByteBuf extraData) {
		this(id, inv, new SimpleContainer(10));
	}

	public CrafterGuiMenu(int id, Inventory inv, Container con) {
		super(TrialsMenus.CRAFTER.get(), id);
		checkContainerSize(con, 10);
		this.crafter = con;
		con.startOpen(inv.player);
		this.addSlot(new CrafterResultSlot(con, 9, 124, 35));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				this.addSlot(new CrafterSlot(con, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}
		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(inv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}
		for (int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(inv, l, 8 + l * 18, 142));
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return this.crafter.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int i) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(i);
		if (slot != null && slot.hasItem()) {
			ItemStack slotstack = slot.getItem();
			stack = slotstack.copy();
			if (slot instanceof CrafterResultSlot) {
				return ItemStack.EMPTY;
			} else if (i < 10) {
				if (!this.moveItemStackTo(slotstack, 10, 46, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(slotstack, 0, 10, false)) {
				return ItemStack.EMPTY;
			}
			if (slotstack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (slotstack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotstack);
		}
		return stack;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.crafter.stopOpen(player);
	}
}