package net.salju.trialstowers.init;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class TrialsTags {
	public static final TagKey<Structure> TRIALS_MAPS = TagKey.create(Registries.STRUCTURE, new ResourceLocation("trials:on_trials_maps"));
	public static final TagKey KOBOLDS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("kobolds:kobolds"));
	public static final TagKey ARMORABLE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("trials:armorable"));
	public static final TagKey<Block> LOCKED = BlockTags.create(new ResourceLocation("trials:locks_crafter"));
}