package net.salju.trialstowers.init;

import net.salju.trialstowers.events.potion.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffectInstance;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrialsPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, Trials.MODID);
	public static final RegistryObject<Potion> OOZE = REGISTRY.register("ooze", () -> new Potion(new MobEffectInstance(TrialsEffects.OOZE.get(), 3600, 0, false, true)));
	public static final RegistryObject<Potion> INFESTED = REGISTRY.register("infested", () -> new Potion(new MobEffectInstance(TrialsEffects.INFESTED.get(), 3600, 0, false, true)));
	public static final RegistryObject<Potion> WEAVE = REGISTRY.register("weave", () -> new Potion(new MobEffectInstance(TrialsEffects.WEAVE.get(), 3600, 0, false, true)));
	public static final RegistryObject<Potion> WINDED = REGISTRY.register("winded", () -> new Potion(new MobEffectInstance(TrialsEffects.WINDED.get(), 3600, 0, false, true)));

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new InfestedPotion()));
		event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new SlimePotion()));
		event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new WebPotion()));
		event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new WindPotion()));
	}
}
