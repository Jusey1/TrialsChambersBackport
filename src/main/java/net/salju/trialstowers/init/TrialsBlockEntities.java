package net.salju.trialstowers.init;

import net.salju.trialstowers.block.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

public class TrialsBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Trials.MODID);
	public static final RegistryObject<BlockEntityType<TrialSpawnerEntity>> SPAWNER = REGISTRY.register("trials_spawner", () -> BlockEntityType.Builder.of(TrialSpawnerEntity::new, TrialsBlocks.SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<TrialVaultEntity>> VAULT = REGISTRY.register("trials_vault", () -> BlockEntityType.Builder.of(TrialVaultEntity::new, findBlocks(TrialVaultBlock.class)).build(null));
	public static final RegistryObject<BlockEntityType<CrafterEntity>> CRAFTER = REGISTRY.register("crafter", () -> BlockEntityType.Builder.of(CrafterEntity::new, TrialsBlocks.CRAFTER.get()).build(null));

	public static Block[] findBlocks(Class<?> targets) {
		return ForgeRegistries.BLOCKS.getValues().stream().filter(targets::isInstance).toArray(Block[]::new);
	}
}
