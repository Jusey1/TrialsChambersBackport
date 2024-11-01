package net.salju.trialstowers.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.CriteriaTriggers;

public class WaxedBlockTDoor extends TrapDoorBlock {
	public WaxedBlockTDoor(BlockBehaviour.Properties props, BlockSetType type) {
		super(props, type);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rez) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof AxeItem) {
			return WeatheringTrialsCopper.removeWaxed(state).map((newState) -> {
				if (player instanceof ServerPlayer ply) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(ply, pos, stack);
				}
				world.setBlock(pos, newState, 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
				world.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.levelEvent(player, 3004, pos, 0);
				stack.hurtAndBreak(1, player, (ply) -> {
					ply.broadcastBreakEvent(hand);
				});
				return InteractionResult.SUCCESS;
			}).orElse(InteractionResult.PASS);
		}
		return super.use(state, world, pos, player, hand, rez);
	}
}