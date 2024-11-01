package net.salju.trialstowers.init;

import net.salju.trialstowers.worldgen.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.core.registries.Registries;
import com.mojang.serialization.Codec;

public class TrialsStructures {
	public static final DeferredRegister<StructureType<?>> REGISTRY = DeferredRegister.create(Registries.STRUCTURE_TYPE, Trials.MODID);
	public static final RegistryObject<StructureType<TrialsChambers>> TRIALS = REGISTRY.register("trialschambers", () -> stuff(TrialsChambers.CODEC));

	private static <T extends Structure> StructureType<T> stuff(Codec<T> codec) {
		return () -> codec;
	}
}