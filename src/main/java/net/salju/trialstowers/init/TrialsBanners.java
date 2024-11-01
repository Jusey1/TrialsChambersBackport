package net.salju.trialstowers.init;

import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.core.registries.Registries;

public class TrialsBanners {
	public static final DeferredRegister<BannerPattern> REGISTRY = DeferredRegister.create(Registries.BANNER_PATTERN, Trials.MODID);
	public static final RegistryObject<BannerPattern> FLOW_BANNER = REGISTRY.register("flow", () -> new BannerPattern("flow"));
	public static final RegistryObject<BannerPattern> GUSTER_BANNER = REGISTRY.register("guster", () -> new BannerPattern("guster"));
}
