package net.salju.trialstowers.events.potion;

import net.salju.trialstowers.init.TrialsPotions;
import net.salju.trialstowers.init.TrialsItems;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

public class WindPotion implements IBrewingRecipe {
	@Override
	public boolean isInput(ItemStack stack) {
		return ((stack.getItem() == Items.POTION || stack.getItem() == Items.SPLASH_POTION || stack.getItem() == Items.LINGERING_POTION) && PotionUtils.getPotion(stack) == Potions.AWKWARD);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.getItem() == TrialsItems.BREEZE_ROD.get();
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionUtils.setPotion(new ItemStack(input.getItem()), TrialsPotions.WINDED.get());
		}
		return ItemStack.EMPTY;
	}
}