package net.salju.trialstowers.entity;

import net.salju.trialstowers.init.TrialsSounds;
import net.salju.trialstowers.entity.ai.BreezeAttackGoal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;

public class Breeze extends Monster {
	private static final EntityDataAccessor<Boolean> CHARGE = SynchedEntityData.defineId(Breeze.class, EntityDataSerializers.BOOLEAN);
	private int cd;

	public Breeze(EntityType<Breeze> type, Level world) {
		super(type, world);
		this.xpReward = 10;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CHARGE, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Charging", this.isCharging());
		tag.putInt("Cooldown", this.cd);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.getEntityData().set(CHARGE, tag.getBoolean("Charging"));
		this.cd = tag.getInt("Cooldown");
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.goalSelector.addGoal(2, new BreezeAttackGoal(this));
		this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof Projectile) {
			this.playSound(TrialsSounds.BREEZE_DEFLECT.get(), 1.0F, 1.0F);
			return false;
		}
		return (source.is(DamageTypes.FALL) ? false : super.hurt(source, amount));
	}

	@Override
	public void aiStep() {
		if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, (this.getTarget() != null ? 0.25D : 0.5D), 1.0D));
		}
		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		if (this.cd > 0) {
			--this.cd;
		}
		if (this.getTarget() != null) {
			if (this.distanceTo(this.getTarget()) <= 4.5F && this.cd <= 0) {
				BlockPos pos = this.findPositionNear(6);
				if (pos != null) {
					this.teleportTo(pos.getX(), pos.getY(), pos.getZ());
					this.playSound(TrialsSounds.BREEZE_SLIDE.get(), 1.0F, 1.0F);
					this.cd = 120;
				} else {
					this.cd = 40;
				}
			} else if ((this.getTarget().getEyeY() + 0.96) > this.getEyeY() && this.canAttack(this.getTarget())) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, ((double) 0.3F - this.getDeltaMovement().y) * (double) 0.3F, 0.0D));
			}
		}
		super.customServerAiStep();
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
		return this.isBaby() ? 0.56F : 1.32F;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return (this.onGround() ? TrialsSounds.BREEZE_IDLE.get() : TrialsSounds.BREEZE_IDLE_AIR.get());
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return TrialsSounds.BREEZE_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return TrialsSounds.BREEZE_DEATH.get();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		//
	}

	@Nullable
	public BlockPos findPositionNear(int range) {
		BlockPos pos = null;
		for (int i = 0; i < 25; ++i) {
			int x = this.blockPosition().getX() + this.getRandom().nextInt(range * 2) - range;
			int y = this.blockPosition().getY() + this.getRandom().nextInt(range * 2) - range;
			int z = this.blockPosition().getZ() + this.getRandom().nextInt(range * 2) - range;
			BlockPos test = new BlockPos(x, y, z);
			if (this.level().noCollision(this.getType().getAABB(test.getX(), test.getY(), test.getZ())) && this.canMoveTo(test) && test.getY() >= this.getY()) {
				pos = test;
				break;
			}
		}
		if (pos != null) {
			return BlockPos.containing(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		}
		return null;
	}

	public boolean canMoveTo(BlockPos pos) {
		boolean check = true;
		for (BlockPos checkpos : BlockPos.betweenClosed(pos.above(), this.blockPosition().above())) {
			BlockState checkstate = this.level().getBlockState(checkpos);
			if (checkstate.canOcclude()) {
				check = false;
				break;
			}
		}
		return check;
	}

	public void setCharged(boolean check) {
		this.getEntityData().set(CHARGE, check);
	}

	public boolean isCharging() {
		return this.getEntityData().get(CHARGE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}
}