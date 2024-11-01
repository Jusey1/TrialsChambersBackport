package net.salju.trialstowers.events;

import net.salju.trialstowers.block.TuffLightBlock;
import net.salju.trialstowers.block.WaxedBlockLight;
import net.salju.trialstowers.block.WeatheringBlockLight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import java.util.List;

public class TrialsManager {
	public static List<ItemStack> getLoot(BlockEntity target, Level world, String table) {
		return world.getServer().getLootData().getLootTable(new ResourceLocation(table)).getRandomItems((new LootParams.Builder((ServerLevel) world)).withParameter(LootContextParams.BLOCK_ENTITY, target).create(LootContextParamSets.EMPTY));
	}

	public static void affectBlocks(BlockPos top, BlockPos bot, ServerLevel lvl) {
		for (BlockPos pos : BlockPos.betweenClosed(top, bot)) {
			BlockState state = lvl.getBlockState(pos);
			if (state.getBlock() instanceof LeverBlock blok) {
				blok.pull(state, lvl, pos);
			} else if (state.getBlock() instanceof ButtonBlock blok) {
				blok.press(state, lvl, pos);
			} else if (state.getBlock() instanceof AbstractCandleBlock blok) {
				blok.extinguish(null, state, lvl, pos);
			} else if (state.getBlock() instanceof TrapDoorBlock && state.getBlock() != Blocks.IRON_TRAPDOOR) {
				lvl.setBlock(pos, state.cycle(TrapDoorBlock.OPEN), 2);
				lvl.playSound(null, pos, state.getValue(TrapDoorBlock.OPEN) ? SoundEvents.WOODEN_TRAPDOOR_CLOSE : SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, lvl.getRandom().nextFloat() * 0.1F + 0.9F);
				lvl.gameEvent(null, state.getValue(TrapDoorBlock.OPEN) ? GameEvent.BLOCK_CLOSE : GameEvent.BLOCK_OPEN, pos);
			} else if (state.getBlock() instanceof DoorBlock && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER && state.getBlock() != Blocks.IRON_DOOR) {
				lvl.setBlock(pos, state.cycle(DoorBlock.OPEN), 10);
				lvl.playSound(null, pos, state.getValue(DoorBlock.OPEN) ? SoundEvents.WOODEN_DOOR_CLOSE : SoundEvents.WOODEN_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, lvl.getRandom().nextFloat() * 0.1F + 0.9F);
				lvl.gameEvent(null, state.getValue(DoorBlock.OPEN) ? GameEvent.BLOCK_CLOSE : GameEvent.BLOCK_OPEN, pos);
			} else if (state.getBlock() instanceof CampfireBlock) {
				lvl.setBlock(pos, state.setValue(CampfireBlock.LIT, false), 2);
			} else if (state.getBlock() instanceof WeatheringBlockLight || state.getBlock() instanceof WaxedBlockLight || state.getBlock() instanceof TuffLightBlock) {
				lvl.setBlock(pos, state.setValue(BlockStateProperties.LIT, !state.getValue(BlockStateProperties.LIT)), 2);
			} else if (state.getBlock() instanceof DispenserBlock dispenserBlock) {
				lvl.scheduleTick(pos, dispenserBlock, 4);
				lvl.setBlock(pos, state.setValue(DispenserBlock.TRIGGERED, true), 4);
			} else if (state.getBlock() instanceof DropperBlock dropperBlock) {
				lvl.scheduleTick(pos, dropperBlock, 4);
				lvl.setBlock(pos, state.setValue(DropperBlock.TRIGGERED, true), 4);
			} else if (state.getBlock() instanceof BaseFireBlock) {
				lvl.removeBlock(pos, false);
			}
		}
	}
}