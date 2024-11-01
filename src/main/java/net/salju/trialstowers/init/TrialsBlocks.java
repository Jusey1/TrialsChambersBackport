package net.salju.trialstowers.init;

import net.salju.trialstowers.block.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.sounds.SoundEvents;
import java.util.function.ToIntFunction;

public class TrialsBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Trials.MODID);
	public static final RegistryObject<Block> TUFF_STAIRS = REGISTRY.register("tuff_stairs", () -> new StairBlock(Blocks.TUFF.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_SLAB = REGISTRY.register("tuff_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_WALL = REGISTRY.register("tuff_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> POLISHED_TUFF = REGISTRY.register("polished_tuff", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> POLISHED_TUFF_STAIRS = REGISTRY.register("polished_tuff_stairs", () -> new StairBlock(Blocks.TUFF.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> POLISHED_TUFF_SLAB = REGISTRY.register("polished_tuff_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> POLISHED_TUFF_WALL = REGISTRY.register("polished_tuff_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_BRICKS = REGISTRY.register("tuff_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_BRICKS_STAIRS = REGISTRY.register("tuff_brick_stairs", () -> new StairBlock(Blocks.TUFF.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_BRICKS_SLAB = REGISTRY.register("tuff_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> TUFF_BRICKS_WALL = REGISTRY.register("tuff_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> CHISELED_TUFF = REGISTRY.register("chiseled_tuff", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> CHISELED_TUFF_BRICKS = REGISTRY.register("chiseled_tuff_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
	public static final RegistryObject<Block> CHISELED_TUFF_BULB = REGISTRY.register("chiseled_tuff_bulb", () -> new TuffLightBlock(BlockBehaviour.Properties.copy(Blocks.TUFF).lightLevel(checkLight(15))));
	public static final RegistryObject<Block> CHISELED_COPPER = REGISTRY.register("chiseled_copper", () -> new WeatheringBlockBase(WeatherState.UNAFFECTED, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_NYLIUM).sound(SoundType.COPPER).strength(3.0F, 6.0F).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> EX_CHISELED_COPPER = REGISTRY.register("chiseled_copper_exposed", () -> new WeatheringBlockBase(WeatherState.EXPOSED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> W_CHISELED_COPPER = REGISTRY.register("chiseled_copper_weathered", () -> new WeatheringBlockBase(WeatherState.WEATHERED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> OXI_CHISELED_COPPER = REGISTRY.register("chiseled_copper_oxidized", () -> new WeatheringBlockBase(WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> WAXED_CHISELED_COPPER = REGISTRY.register("waxed_chiseled_copper", () -> new WaxedBlockBase(BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> WAXED_EX_CHISELED_COPPER = REGISTRY.register("waxed_chiseled_copper_exposed", () -> new WaxedBlockBase(BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> WAXED_W_CHISELED_COPPER = REGISTRY.register("waxed_chiseled_copper_weathered", () -> new WaxedBlockBase(BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> WAXED_OXI_CHISELED_COPPER = REGISTRY.register("waxed_chiseled_copper_oxidized", () -> new WaxedBlockBase(BlockBehaviour.Properties.copy(CHISELED_COPPER.get())));
	public static final RegistryObject<Block> COPPER_GRATE = REGISTRY.register("copper_grate", () -> new WeatheringBlockWater(WeatherState.UNAFFECTED, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_NYLIUM).sound(SoundType.COPPER).strength(3.0F, 6.0F).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<Block> EX_COPPER_GRATE = REGISTRY.register("copper_grate_exposed", () -> new WeatheringBlockWater(WeatherState.EXPOSED, BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> W_COPPER_GRATE = REGISTRY.register("copper_grate_weathered", () -> new WeatheringBlockWater(WeatherState.WEATHERED, BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> OXI_COPPER_GRATE = REGISTRY.register("copper_grate_oxidized", () -> new WeatheringBlockWater(WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> WAXED_COPPER_GRATE = REGISTRY.register("waxed_copper_grate", () -> new WaxedBlockWater(BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> WAXED_EX_COPPER_GRATE = REGISTRY.register("waxed_copper_grate_exposed", () -> new WaxedBlockWater(BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> WAXED_W_COPPER_GRATE = REGISTRY.register("waxed_copper_grate_weathered", () -> new WaxedBlockWater(BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> WAXED_OXI_COPPER_GRATE = REGISTRY.register("waxed_copper_grate_oxidized", () -> new WaxedBlockWater(BlockBehaviour.Properties.copy(COPPER_GRATE.get())));
	public static final RegistryObject<Block> COPPER_TRAPDOOR = REGISTRY.register("copper_trapdoor", () -> new WeatheringBlockTDoor(WeatherState.UNAFFECTED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> EX_COPPER_TRAPDOOR = REGISTRY.register("copper_trapdoor_exposed", () -> new WeatheringBlockTDoor(WeatherState.EXPOSED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> W_COPPER_TRAPDOOR = REGISTRY.register("copper_trapdoor_weathered", () -> new WeatheringBlockTDoor(WeatherState.WEATHERED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> OXI_COPPER_TRAPDOOR = REGISTRY.register("copper_trapdoor_oxidized", () -> new WeatheringBlockTDoor(WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_COPPER_TRAPDOOR = REGISTRY.register("waxed_copper_trapdoor", () -> new WaxedBlockTDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_EX_COPPER_TRAPDOOR = REGISTRY.register("waxed_copper_trapdoor_exposed", () -> new WaxedBlockTDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_W_COPPER_TRAPDOOR = REGISTRY.register("waxed_copper_trapdoor_weathered", () -> new WaxedBlockTDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_OXI_COPPER_TRAPDOOR = REGISTRY.register("waxed_copper_trapdoor_oxidized", () -> new WaxedBlockTDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> COPPER_DOOR = REGISTRY.register("copper_door", () -> new WeatheringBlockDoor(WeatherState.UNAFFECTED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> EX_COPPER_DOOR = REGISTRY.register("copper_door_exposed", () -> new WeatheringBlockDoor(WeatherState.EXPOSED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> W_COPPER_DOOR = REGISTRY.register("copper_door_weathered", () -> new WeatheringBlockDoor(WeatherState.WEATHERED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> OXI_COPPER_DOOR = REGISTRY.register("copper_door_oxidized", () -> new WeatheringBlockDoor(WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_COPPER_DOOR = REGISTRY.register("waxed_copper_door", () -> new WaxedBlockDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_EX_COPPER_DOOR = REGISTRY.register("waxed_copper_door_exposed", () -> new WaxedBlockDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_W_COPPER_DOOR = REGISTRY.register("waxed_copper_door_weathered", () -> new WaxedBlockDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> WAXED_OXI_COPPER_DOOR = REGISTRY.register("waxed_copper_door_oxidized", () -> new WaxedBlockDoor(BlockBehaviour.Properties.copy(COPPER_GRATE.get()), getCopper()));
	public static final RegistryObject<Block> COPPER_BULB = REGISTRY.register("copper_bulb", () -> new WeatheringBlockLight(WeatherState.UNAFFECTED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(15))));
	public static final RegistryObject<Block> EX_COPPER_BULB = REGISTRY.register("copper_bulb_exposed", () -> new WeatheringBlockLight(WeatherState.EXPOSED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(12))));
	public static final RegistryObject<Block> W_COPPER_BULB = REGISTRY.register("copper_bulb_weathered", () -> new WeatheringBlockLight(WeatherState.WEATHERED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(8))));
	public static final RegistryObject<Block> OXI_COPPER_BULB = REGISTRY.register("copper_bulb_oxidized", () -> new WeatheringBlockLight(WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(4))));
	public static final RegistryObject<Block> WAXED_COPPER_BULB = REGISTRY.register("waxed_copper_bulb", () -> new WaxedBlockLight(BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(15))));
	public static final RegistryObject<Block> WAXED_EX_COPPER_BULB = REGISTRY.register("waxed_copper_bulb_exposed", () -> new WaxedBlockLight(BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(12))));
	public static final RegistryObject<Block> WAXED_W_COPPER_BULB = REGISTRY.register("waxed_copper_bulb_weathered", () -> new WaxedBlockLight(BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(8))));
	public static final RegistryObject<Block> WAXED_OXI_COPPER_BULB = REGISTRY.register("waxed_copper_bulb_oxidized", () -> new WaxedBlockLight(BlockBehaviour.Properties.copy(CHISELED_COPPER.get()).lightLevel(checkLight(4))));
	public static final RegistryObject<Block> CRAFTER = REGISTRY.register("crafter", () -> new CrafterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1.5F, 3.5F)));
	public static final RegistryObject<Block> SPAWNER = REGISTRY.register("trial_spawner", () -> new TrialSpawnerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(200.0F, 2000.0F).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<Block> VAULT = REGISTRY.register("trial_vault", () -> new TrialVaultBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(200.0F, 2000.0F).requiresCorrectToolForDrops().noOcclusion(), false));
	public static final RegistryObject<Block> VAULT_OMNI = REGISTRY.register("trial_vault_ominous", () -> new TrialVaultBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(200.0F, 2000.0F).requiresCorrectToolForDrops().noOcclusion(), true));
	public static final RegistryObject<Block> HEAVY_CORE = REGISTRY.register("heavy_core", () -> new HeavyCoreBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(20.0F, 1000.0F).requiresCorrectToolForDrops().noOcclusion()));

	private static ToIntFunction<BlockState> checkLight(int i) {
		return (state) -> {
			return state.getValue(BlockStateProperties.LIT) ? i : 0;
		};
	}

	private static BlockSetType getCopper() {
		return new BlockSetType("copper", true, SoundType.METAL, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
	}
}