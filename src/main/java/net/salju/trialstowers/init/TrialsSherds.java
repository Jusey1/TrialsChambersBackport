package net.salju.trialstowers.init;

import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class TrialsSherds {
	public static final DeferredRegister<String> REGISTRY = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, Trials.MODID);
	public static final RegistryObject<String> FLOW = REGISTRY.register("flow_pottery_pattern", () -> Trials.MODID + ":flow_pottery_pattern");
	public static final RegistryObject<String> GUSTER = REGISTRY.register("guster_pottery_pattern", () -> Trials.MODID + ":guster_pottery_pattern");
	public static final RegistryObject<String> SCRAPE = REGISTRY.register("scrape_pottery_pattern", () -> Trials.MODID + ":scrape_pottery_pattern");
}