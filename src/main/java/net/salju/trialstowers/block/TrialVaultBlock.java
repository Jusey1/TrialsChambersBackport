package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsProperties;
import net.salju.trialstowers.init.TrialsSounds;
import net.salju.trialstowers.init.TrialsItems;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;

public class TrialVaultBlock extends BaseEntityBlock {
	public static final BooleanProperty ACTIVE = TrialsProperties.ACTIVE;
	public static final BooleanProperty EJECT = TrialsProperties.EJECT;
	private final boolean isOminous;

	public TrialVaultBlock(BlockBehaviour.Properties props, boolean check) {
		super(props);
		this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, true).setValue(EJECT, false));
		this.isOminous = check;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
		builder.add(EJECT);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rez) {
		BlockEntity entity = world.getBlockEntity(pos);
		ItemStack stack = player.getItemInHand(hand);
		if (entity instanceof TrialVaultEntity target && isActive(state) && !isEjecting(state)) {
			if (stack.is(this.isOminous ? TrialsItems.TRIAL_KEY_OMNI.get() : TrialsItems.TRIAL_KEY.get())) {
				world.setBlock(pos, state.setValue(EJECT, Boolean.valueOf(true)), 3);
				if (!player.isCreative()) {
					stack.shrink(1);
				}
				if (world instanceof ServerLevel lvl) {
					lvl.playSound(null, pos, TrialsSounds.VAULT_INSERT_KEY.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int i, int e) {
		return world.getBlockEntity(pos) == null ? super.triggerEvent(state, world, pos, i, e) : world.getBlockEntity(pos).triggerEvent(i, e);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		TrialVaultEntity vault = new TrialVaultEntity(pos, state);
		vault.setVault(this.isOminous);
		return vault;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, TrialsBlockEntities.VAULT.get(), TrialVaultEntity::tick);
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
}