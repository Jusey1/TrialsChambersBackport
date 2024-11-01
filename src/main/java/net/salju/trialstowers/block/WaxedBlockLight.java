package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsBlocks;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class WaxedBlockLight extends WaxedBlockBase {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public WaxedBlockLight(BlockBehaviour.Properties props) {
		super(props);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(POWERED);
		builder.add(LIT);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState newState, boolean check) {
		super.onPlace(state, world, pos, newState, check);
		if (!newState.is(state.getBlock())) {
			if (!world.isClientSide) {
				world.scheduleTick(pos, this, 1);
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blok, BlockPos poz, boolean check) {
		super.neighborChanged(state, world, pos, blok, poz, check);
		if (!world.isClientSide()) {
			world.scheduleTick(pos, this, 1);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel lvl, BlockPos pos, RandomSource rng) {
		super.tick(state, lvl, pos, rng);
		if (state.getValue(POWERED) && !lvl.hasNeighborSignal(pos)) {
			lvl.setBlock(pos, state.setValue(POWERED, false), 2);
			lvl.updateNeighbourForOutputSignal(pos, state.getBlock());
		} else if (lvl.hasNeighborSignal(pos) && !state.getValue(POWERED)) {
			if (state.getValue(LIT)) {
				lvl.setBlock(pos, state.setValue(LIT, false).setValue(POWERED, true), 2);
				lvl.updateNeighbourForOutputSignal(pos, state.getBlock());
			} else {
				lvl.setBlock(pos, state.setValue(LIT, true).setValue(POWERED, true), 2);
				lvl.updateNeighbourForOutputSignal(pos, state.getBlock());
			}
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return state.getValue(LIT);
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return (state.getValue(LIT) ? 15 : 0);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return state.getValue(LIT) ? getCopperLight(state) : 0;
	}

	public int getCopperLight(BlockState state) {
		if (state.is(TrialsBlocks.WAXED_EX_COPPER_BULB.get())) {
			return 12;
		} else if (state.is(TrialsBlocks.WAXED_W_COPPER_BULB.get())) {
			return 8;
		} else if (state.is(TrialsBlocks.WAXED_OXI_COPPER_BULB.get())) {
			return 4;
		} else {
			return 15;
		}
	}
}