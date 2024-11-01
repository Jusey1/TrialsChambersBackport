package net.salju.trialstowers.worldgen;

import net.salju.trialstowers.init.TrialsStructures;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import java.util.Optional;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.Codec;

public class TrialsChambers extends Structure {
	public static final Codec<TrialsChambers> CODEC = RecordCodecBuilder.<TrialsChambers>mapCodec((codex) -> {
		return codex.group(settingsCodec(codex), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((temppool) -> {
			return temppool.startPool;
		}), HeightProvider.CODEC.fieldOf("start_height").forGetter((heighty) -> {
			return heighty.startHeight;
		}), Heightmap.Types.CODEC.optionalFieldOf("heightmap").forGetter((mapster) -> {
			return mapster.heightmap;
		})).apply(codex, TrialsChambers::new);
	}).codec();
	public final Holder<StructureTemplatePool> startPool;
	public final HeightProvider startHeight;
	public final Optional<Heightmap.Types> heightmap;

	public TrialsChambers(Structure.StructureSettings config, Holder<StructureTemplatePool> pool, HeightProvider height, Optional<Heightmap.Types> map) {
		super(config);
		this.startPool = pool;
		this.startHeight = height;
		this.heightmap = map;
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		BlockPos pos = new BlockPos(context.chunkPos().getMinBlockX(), this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor())), context.chunkPos().getMinBlockZ());
		return JigsawPlacement.addPieces(context, this.startPool, Optional.empty(), 7, pos, false, heightmap, 128);
	}

	@Override
	public StructureType<?> type() {
		return TrialsStructures.TRIALS.get();
	}
}
