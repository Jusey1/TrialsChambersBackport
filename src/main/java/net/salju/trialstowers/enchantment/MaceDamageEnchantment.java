package net.salju.trialstowers.enchantment;

import net.salju.trialstowers.item.MaceItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class MaceDamageEnchantment extends Enchantment {
	public MaceDamageEnchantment(Enchantment.Rarity rare, EquipmentSlot... slots) {
		super(rare, EnchantmentCategory.DIGGER, slots);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		if (ench instanceof DamageEnchantment || ench == Enchantments.BLOCK_EFFICIENCY) {
			return false;
		}
		return super.checkCompatibility(ench);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return (stack.getItem() instanceof MaceItem);
	}
}