package net.salju.trialstowers.item;

import net.salju.trialstowers.init.TrialsSounds;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;

public class OmniItem extends Item {
	public OmniItem(Item.Properties props) {
		super(props);
	}

	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(world, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity target) {
		if (target.hasEffect(MobEffects.BAD_OMEN)) {
			target.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 12000, (target.getEffect(MobEffects.BAD_OMEN).getAmplifier() + 1)));
		} else {
			target.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 12000, 0));
		}
		target.playSound(TrialsSounds.BOTTLE_DISPOSE.get());
		if (target instanceof Player player && !player.isCreative()) {
			stack.shrink(1);
		}
		return super.finishUsingItem(stack, world, target);
	}
}