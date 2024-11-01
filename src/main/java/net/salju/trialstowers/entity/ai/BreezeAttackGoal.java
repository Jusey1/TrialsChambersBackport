package net.salju.trialstowers.entity.ai;

import net.salju.trialstowers.init.TrialsSounds;
import net.salju.trialstowers.entity.WindCharge;
import net.salju.trialstowers.entity.Breeze;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import java.util.EnumSet;

public class BreezeAttackGoal extends Goal {
	private final Breeze brezo;
	private int attackTime;
	private int lastSeen;

	public BreezeAttackGoal(Breeze t) {
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.brezo = t;
	}

	@Override
	public boolean canUse() {
		return this.brezo.getTarget() != null && this.brezo.getTarget().isAlive() && this.brezo.canAttack(this.brezo.getTarget());
	}

	@Override
	public void start() {
		this.attackTime = 0;
	}

	@Override
	public void stop() {
		this.brezo.setCharged(false);
		this.lastSeen = 0;
	}

	@Override
	public void tick() {
		--this.attackTime;
		LivingEntity target = this.brezo.getTarget();
		if (target != null) {
			if (this.brezo.getSensing().hasLineOfSight(target)) {
				this.lastSeen = 0;
			} else {
				++this.lastSeen;
				if (this.brezo.isCharging()) {
					this.brezo.setCharged(false);
				}
			}
			if (this.brezo.distanceToSqr(target) < this.getFollowDistance() * this.getFollowDistance() && this.brezo.getSensing().hasLineOfSight(target)) {
				if (this.attackTime <= 0) {
					this.attackTime = 80;
				} else if (this.attackTime == 20) {
					this.brezo.setCharged(true);
					this.brezo.playSound(TrialsSounds.BREEZE_CHARGE.get(), 1.0F, 1.0F);
				} else if (this.attackTime == 1) {
					this.brezo.setCharged(false);
					this.brezo.playSound(TrialsSounds.BREEZE_SHOOT.get(), 1.0F, 1.0F);
					WindCharge ammo = new WindCharge(this.brezo.level(), this.brezo);
					ammo.shoot(this.brezo.getViewVector(1.0F).x, this.brezo.getViewVector(1.0F).y, this.brezo.getViewVector(1.0F).z, 1.45F, 1.0F);
					this.brezo.level().addFreshEntity(ammo);
				}
				this.brezo.getLookControl().setLookAt(target, 10.0F, 10.0F);
				this.brezo.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
			} else if (this.lastSeen < 5) {
				this.brezo.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
			}
			super.tick();
		}
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	private double getFollowDistance() {
		return this.brezo.getAttributeValue(Attributes.FOLLOW_RANGE);
	}
}