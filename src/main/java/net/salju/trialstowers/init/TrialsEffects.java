package net.salju.trialstowers.init;

import net.salju.trialstowers.effect.TrialsEffect;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class TrialsEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Trials.MODID);
	public static final RegistryObject<MobEffect> WINDED = REGISTRY.register("winded", () -> new TrialsEffect(MobEffectCategory.NEUTRAL, -3355444, "effect.trials.winded"));
	public static final RegistryObject<MobEffect> CURSED = REGISTRY.register("trial_curse", () -> new TrialsEffect(MobEffectCategory.HARMFUL, -16724788, "effect.trials.cursed"));
	public static final RegistryObject<MobEffect> OOZE = REGISTRY.register("oozing", () -> new TrialsEffect(MobEffectCategory.NEUTRAL, -10027162, "effect.trials.ooze"));
	public static final RegistryObject<MobEffect> INFESTED = REGISTRY.register("infested", () -> new TrialsEffect(MobEffectCategory.NEUTRAL, -10066330, "effect.trials.infested"));
	public static final RegistryObject<MobEffect> WEAVE = REGISTRY.register("weaving", () -> new TrialsEffect(MobEffectCategory.NEUTRAL, -6710887, "effect.trials.weave"));
}