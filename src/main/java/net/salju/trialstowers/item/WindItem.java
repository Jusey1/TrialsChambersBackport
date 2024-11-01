package net.salju.trialstowers.item;

import net.salju.trialstowers.entity.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;

public class WindItem extends Item {
	public WindItem(Item.Properties props) {
		super(props);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!player.isCreative()) {
			stack.shrink(1);
		}
		if (world instanceof ServerLevel lvl) {
			player.getCooldowns().addCooldown(stack.getItem(), 15);
			WindCharge ammo = new WindCharge(world, player);
			ammo.shoot(player.getViewVector(1.0F).x, player.getViewVector(1.0F).y, player.getViewVector(1.0F).z, 1.45F, 1.0F);
			lvl.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
			world.addFreshEntity(ammo);
		}
		return InteractionResultHolder.success(stack);
	}
}