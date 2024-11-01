package net.salju.trialstowers.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class TrialsEffect extends MobEffect {
	private final String name;
	
	public TrialsEffect(MobEffectCategory cate, int i, String s) {
		super(cate, i);
		this.name = s;
	}

	@Override
	public String getDescriptionId() {
		return this.name;
	}

	@Override
	public boolean isDurationEffectTick(int dur, int str) {
		return true;
	}
}