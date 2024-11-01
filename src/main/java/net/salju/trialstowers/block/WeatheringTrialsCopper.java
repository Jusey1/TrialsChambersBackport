package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;
import java.util.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.BiMap;
import com.google.common.base.Suppliers;

public interface WeatheringTrialsCopper extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
	Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
		return ImmutableBiMap.<Block, Block>builder().put(TrialsBlocks.CHISELED_COPPER.get(), TrialsBlocks.EX_CHISELED_COPPER.get()).put(TrialsBlocks.EX_CHISELED_COPPER.get(), TrialsBlocks.W_CHISELED_COPPER.get())
				.put(TrialsBlocks.W_CHISELED_COPPER.get(), TrialsBlocks.OXI_CHISELED_COPPER.get()).put(TrialsBlocks.COPPER_GRATE.get(), TrialsBlocks.EX_COPPER_GRATE.get()).put(TrialsBlocks.EX_COPPER_GRATE.get(), TrialsBlocks.W_COPPER_GRATE.get())
				.put(TrialsBlocks.W_COPPER_GRATE.get(), TrialsBlocks.OXI_COPPER_GRATE.get()).put(TrialsBlocks.COPPER_TRAPDOOR.get(), TrialsBlocks.EX_COPPER_TRAPDOOR.get())
				.put(TrialsBlocks.EX_COPPER_TRAPDOOR.get(), TrialsBlocks.W_COPPER_TRAPDOOR.get()).put(TrialsBlocks.W_COPPER_TRAPDOOR.get(), TrialsBlocks.OXI_COPPER_TRAPDOOR.get()).put(TrialsBlocks.COPPER_DOOR.get(), TrialsBlocks.EX_COPPER_DOOR.get())
				.put(TrialsBlocks.EX_COPPER_DOOR.get(), TrialsBlocks.W_COPPER_DOOR.get()).put(TrialsBlocks.W_COPPER_DOOR.get(), TrialsBlocks.OXI_COPPER_DOOR.get()).put(TrialsBlocks.COPPER_BULB.get(), TrialsBlocks.EX_COPPER_BULB.get())
				.put(TrialsBlocks.EX_COPPER_BULB.get(), TrialsBlocks.W_COPPER_BULB.get()).put(TrialsBlocks.W_COPPER_BULB.get(), TrialsBlocks.OXI_COPPER_BULB.get()).build();
	});

	Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> {
		return NEXT_BY_BLOCK.get().inverse();
	});
	
	Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(() -> {
		return ImmutableBiMap.<Block, Block>builder().put(TrialsBlocks.CHISELED_COPPER.get(), TrialsBlocks.WAXED_CHISELED_COPPER.get()).put(TrialsBlocks.EX_CHISELED_COPPER.get(), TrialsBlocks.WAXED_EX_CHISELED_COPPER.get())
				.put(TrialsBlocks.W_CHISELED_COPPER.get(), TrialsBlocks.WAXED_W_CHISELED_COPPER.get()).put(TrialsBlocks.OXI_CHISELED_COPPER.get(), TrialsBlocks.WAXED_OXI_CHISELED_COPPER.get())
				.put(TrialsBlocks.COPPER_GRATE.get(), TrialsBlocks.WAXED_COPPER_GRATE.get()).put(TrialsBlocks.EX_COPPER_GRATE.get(), TrialsBlocks.WAXED_EX_COPPER_GRATE.get())
				.put(TrialsBlocks.W_COPPER_GRATE.get(), TrialsBlocks.WAXED_W_COPPER_GRATE.get()).put(TrialsBlocks.OXI_COPPER_GRATE.get(), TrialsBlocks.WAXED_OXI_COPPER_GRATE.get())
				.put(TrialsBlocks.COPPER_TRAPDOOR.get(), TrialsBlocks.WAXED_COPPER_TRAPDOOR.get()).put(TrialsBlocks.EX_COPPER_TRAPDOOR.get(), TrialsBlocks.WAXED_EX_COPPER_TRAPDOOR.get())
				.put(TrialsBlocks.W_COPPER_TRAPDOOR.get(), TrialsBlocks.WAXED_W_COPPER_TRAPDOOR.get()).put(TrialsBlocks.OXI_COPPER_TRAPDOOR.get(), TrialsBlocks.WAXED_OXI_COPPER_TRAPDOOR.get())
				.put(TrialsBlocks.COPPER_DOOR.get(), TrialsBlocks.WAXED_COPPER_DOOR.get()).put(TrialsBlocks.EX_COPPER_DOOR.get(), TrialsBlocks.WAXED_EX_COPPER_DOOR.get()).put(TrialsBlocks.W_COPPER_DOOR.get(), TrialsBlocks.WAXED_W_COPPER_DOOR.get())
				.put(TrialsBlocks.OXI_COPPER_DOOR.get(), TrialsBlocks.WAXED_OXI_COPPER_DOOR.get()).put(TrialsBlocks.COPPER_BULB.get(), TrialsBlocks.WAXED_COPPER_BULB.get())
				.put(TrialsBlocks.EX_COPPER_BULB.get(), TrialsBlocks.WAXED_EX_COPPER_BULB.get()).put(TrialsBlocks.W_COPPER_BULB.get(), TrialsBlocks.WAXED_W_COPPER_BULB.get())
				.put(TrialsBlocks.OXI_COPPER_BULB.get(), TrialsBlocks.WAXED_OXI_COPPER_BULB.get()).build();
	});

	Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> {
		return WAXABLES.get().inverse();
	});

	static Optional<BlockState> getNextBlock(BlockState state) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get().get(state.getBlock())).map((blok) -> {
			return blok.withPropertiesOf(state);
		});
	}

	static Optional<BlockState> getPreviousBlock(BlockState state) {
		return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(state.getBlock())).map((blok) -> {
			return blok.withPropertiesOf(state);
		});
	}

	static Optional<BlockState> getWaxed(BlockState state) {
		return Optional.ofNullable(WAXABLES.get().get(state.getBlock())).map((blok) -> {
			return blok.withPropertiesOf(state);
		});
	}

	static Optional<BlockState> removeWaxed(BlockState state) {
		return Optional.ofNullable(WAX_OFF_BY_BLOCK.get().get(state.getBlock())).map((blok) -> {
			return blok.withPropertiesOf(state);
		});
	}

	default Optional<BlockState> getNext(BlockState state) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get().get(state.getBlock())).map((blok) -> {
			return blok.withPropertiesOf(state);
		});
	}

	default float getChanceModifier() {
		return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
	}
}