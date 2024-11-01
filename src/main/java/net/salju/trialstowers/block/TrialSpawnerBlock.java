package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsProperties;
import net.salju.trialstowers.init.TrialsBlockEntities;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;

public class TrialSpawnerBlock extends BaseEntityBlock {
	public static final BooleanProperty ACTIVE = TrialsProperties.ACTIVE;
	public static final BooleanProperty EJECT = TrialsProperties.EJECT;
	public static final BooleanProperty CURSED = TrialsProperties.CURSED;

	public TrialSpawnerBlock(BlockBehaviour.Properties props) {
		super(props);
		this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, true).setValue(EJECT, false).setValue(CURSED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
		builder.add(EJECT);
		builder.add(CURSED);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rez) {
		BlockEntity entity = world.getBlockEntity(pos);
		ItemStack stack = player.getItemInHand(hand);
		if (entity instanceof TrialSpawnerEntity target) {
			if (player.isCreative() && stack.getItem() instanceof SpawnEggItem) {
				target.setEgg(stack);
				target.updateBlock();
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult rez, BlockGetter world, BlockPos pos, Player player) {
		if (world.getBlockEntity(pos) instanceof TrialSpawnerEntity target) {
			ItemStack stack = super.getCloneItemStack(state, rez, world, pos, player);
			stack.addTagElement("BlockEntityTag", target.getUpdateTag());
			return stack;
		}
		return super.getCloneItemStack(state, rez, world, pos, player);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
		super.setPlacedBy(world, pos, state, livingEntity, stack);
		if (stack.hasTag() && stack.getTag().contains("BlockEntityTag")) {
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof TrialSpawnerEntity spawnerEntity) {
				spawnerEntity.load(stack.getTag().getCompound("BlockEntityTag"));
			}
		}
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int i, int e) {
		return world.getBlockEntity(pos) == null ? super.triggerEvent(state, world, pos, i, e) : world.getBlockEntity(pos).triggerEvent(i, e);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TrialSpawnerEntity(pos, state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, TrialsBlockEntities.SPAWNER.get(), TrialSpawnerEntity::tick);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	public boolean isActive(BlockState state) {
		return state.getValue(ACTIVE);
	}

	public boolean isEjecting(BlockState state) {
		return state.getValue(EJECT);
	}

	public boolean isCursed(BlockState state) {
		return state.getValue(CURSED);
	}
}
