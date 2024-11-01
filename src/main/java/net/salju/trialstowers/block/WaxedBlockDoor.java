package net.salju.trialstowers.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.CriteriaTriggers;

public class WaxedBlockDoor extends DoorBlock {
	public WaxedBlockDoor(BlockBehaviour.Properties props, BlockSetType type) {
		super(props, type);
	}

	@Override
	public BlockState updateShape(BlockState newState, Direction d, BlockState oldState, LevelAccessor world, BlockPos pos, BlockPos poz) {
		DoubleBlockHalf db = newState.getValue(HALF);
		if (d.getAxis() == Direction.Axis.Y && db == DoubleBlockHalf.LOWER == (d == Direction.UP)) {
			return (oldState.getBlock() instanceof WeatheringBlockDoor || oldState.getBlock() instanceof WaxedBlockDoor) && oldState.getValue(HALF) != db
 ? newState.setValue(FACING, oldState.getValue(FACING)).setValue(OPEN, oldState.getValue(OPEN)).setValue(HINGE, oldState.getValue(HINGE)).setValue(POWERED, oldState.getValue(POWERED))
 : Blocks.AIR.defaultBlockState();
		} else {
			return db == DoubleBlockHalf.LOWER && d == Direction.DOWN && !newState.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(newState, d, oldState, world, pos, poz);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rez) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof AxeItem && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
			return WeatheringTrialsCopper.removeWaxed(state).map((newState) -> {
				if (player instanceof ServerPlayer ply) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(ply, pos, stack);
				}
				world.setBlock(pos, newState, 11);
				world.setBlock(pos.above(), newState.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 11);
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