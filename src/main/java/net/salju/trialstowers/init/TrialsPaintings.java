package net.salju.trialstowers.init;

import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class TrialsPaintings {
	public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Trials.MODID);
	public static final RegistryObject<PaintingVariant> BACKYARD = REGISTRY.register("backyard", () -> new PaintingVariant(48, 64));
	public static final RegistryObject<PaintingVariant> BAROQUE = REGISTRY.register("baroque", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> BOUQUET = REGISTRY.register("bouquet", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> CAVEBIRD = REGISTRY.register("cavebird", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> CHANGING = REGISTRY.register("changing", () -> new PaintingVariant(64, 32));
	public static final RegistryObject<PaintingVariant> COTAN = REGISTRY.register("cotan", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> ENDBOSS = REGISTRY.register("endboss", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> FERN = REGISTRY.register("fern", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> FINDING = REGISTRY.register("finding", () -> new PaintingVariant(64, 32));
	public static final RegistryObject<PaintingVariant> HUMBLE = REGISTRY.register("humble", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> LOWMIST = REGISTRY.register("lowmist", () -> new PaintingVariant(64, 32));
	public static final RegistryObject<PaintingVariant> MEDITATIVE = REGISTRY.register("meditative", () -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> ORB = REGISTRY.register("orb", () -> new PaintingVariant(64, 64));
	public static final RegistryObject<PaintingVariant> OWLEMONS = REGISTRY.register("owlemons", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> PASSAGE = REGISTRY.register("passage", () -> new PaintingVariant(64, 32));
	public static final RegistryObject<PaintingVariant> POND = REGISTRY.register("pond", () -> new PaintingVariant(48, 64));
	public static final RegistryObject<PaintingVariant> PRAIRIERIDE = REGISTRY.register("prairieride", () -> new PaintingVariant(16, 32));
	public static final RegistryObject<PaintingVariant> SUNFLOWERS = REGISTRY.register("sunflowers", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> TIDES = REGISTRY.register("tides", () -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> UNPACKED = REGISTRY.register("unpacked", () -> new PaintingVariant(64, 64));
}