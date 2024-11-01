package net.salju.trialstowers.entity;

import net.salju.trialstowers.init.TrialsSounds;
import net.salju.trialstowers.init.TrialsMobs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Containers;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;

public class Bogged extends AbstractSkeleton {
	private static final EntityDataAccessor<Boolean> MUSHROOMS = SynchedEntityData.defineId(Bogged.class, EntityDataSerializers.BOOLEAN);

	public Bogged(EntityType<Bogged> type, Level world) {
		super(type, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MUSHROOMS, true);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Mushrooms", this.hasMushrooms());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.getEntityData().set(MUSHROOMS, tag.getBoolean("Mushrooms"));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return TrialsSounds.BOGGED_AMBIENT.get();
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return TrialsSounds.BOGGED_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return TrialsSounds.BOGGED_DEATH.get();
	}

	@Override
	public SoundEvent getStepSound() {
		return TrialsSounds.BOGGED_STEP.get();
	}

	@Override
	public AbstractArrow getArrow(ItemStack stack, float f) {
		AbstractArrow arrow = super.getArrow(stack, f);
		if (arrow instanceof Arrow) {
			((Arrow) arrow).addEffect(new MobEffectInstance(MobEffects.POISON, 300));
		}
		return arrow;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1.0D, (this.level().getDifficulty() != Difficulty.HARD ? 70 : 50), 15.0F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand handy) {
		ItemStack stack = player.getItemInHand(handy);
		if (stack.getItem() instanceof ShearsItem && this.hasMushrooms()) {
			this.getEntityData().set(MUSHROOMS, false);
			if (this.level() instanceof ServerLevel lvl) {
				this.gameEvent(GameEvent.SHEAR, player);
				this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
				Containers.dropItemStack(this.level(), this.getX(), (this.getY() + 1.85), this.getZ(), new ItemStack(Items.RED_MUSHROOM));
				Containers.dropItemStack(this.level(), this.getX(), (this.getY() + 1.85), this.getZ(), new ItemStack(Items.BROWN_MUSHROOM));
				stack.hurtAndBreak(1, player, (item) -> {
					item.broadcastBreakEvent(handy);
				});
			}
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, handy);
	}

	@Override
	public void reassessWeaponGoal() {
		//
	}

	public boolean hasMushrooms() {
		return this.getEntityData().get(MUSHROOMS);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 18);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		return builder;
	}

	public static void init() {
		SpawnPlacements.register(TrialsMobs.BOGGED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}
}