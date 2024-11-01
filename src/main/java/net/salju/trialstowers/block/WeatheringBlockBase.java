package net.salju.trialstowers.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.CriteriaTriggers;

public class WeatheringBlockBase extends Block implements WeatheringTrialsCopper {
	private final WeatheringCopper.WeatherState weather;

	public WeatheringBlockBase(WeatheringCopper.WeatherState state, BlockBehaviour.Properties props) {
		super(props);
		this.weather = state;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rez) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof HoneycombItem) {
			return WeatheringTrialsCopper.getWaxed(state).map((newState) -> {
				if (player instanceof ServerPlayer ply) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(ply, pos, stack);
				}
				if (!player.isCreative()) {
					stack.shrink(1);
				}
				world.setBlock(pos, newState, 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
				world.levelEvent(player, 3003, pos, 0);
				return InteractionResult.SUCCESS;
			}).orElse(InteractionResult.PASS);
		} else if (stack.getItem() instanceof AxeItem) {
			return WeatheringTrialsCopper.getPreviousBlock(state).map((newState) -> {
				if (player instanceof ServerPlayer ply) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(ply, pos, stack);
				}
				world.setBlock(pos, newState, 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
				world.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.levelEvent(player, 3005, pos, 0);
				stack.hurtAndBreak(1, player, (ply) -> {
					ply.broadcastBreakEvent(hand);
				});
				return InteractionResult.SUCCESS;
			}).orElse(InteractionResult.PASS);
		}
		return InteractionResult.PASS;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel lvl, BlockPos pos, RandomSource rng) {
		this.onRandomTick(state, lvl, pos, rng);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return WeatheringTrialsCopper.getNextBlock(state).isPresent();
	}

	public WeatheringCopper.WeatherState getAge() {
		return this.weather;
	}
}