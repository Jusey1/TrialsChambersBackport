package net.salju.trialstowers.enchantment;

import net.salju.trialstowers.item.MaceItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class MaceArmorEnchantment extends Enchantment {
	public MaceArmorEnchantment(Enchantment.Rarity rare, EquipmentSlot... slots) {
		super(rare, EnchantmentCategory.DIGGER, slots);
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return (stack.getItem() instanceof MaceItem);
	}
}