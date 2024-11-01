package net.salju.trialstowers.init;

import net.salju.trialstowers.entity.WindCharge;
import net.salju.trialstowers.item.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.DispenserBlock;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrialsItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Trials.MODID);
	public static final RegistryObject<Item> TUFF_STAIRS = block(TrialsBlocks.TUFF_STAIRS);
	public static final RegistryObject<Item> TUFF_SLAB = block(TrialsBlocks.TUFF_SLAB);
	public static final RegistryObject<Item> TUFF_WALL = block(TrialsBlocks.TUFF_WALL);
	public static final RegistryObject<Item> POLISHED_TUFF = block(TrialsBlocks.POLISHED_TUFF);
	public static final RegistryObject<Item> POLISHED_TUFF_STAIRS = block(TrialsBlocks.POLISHED_TUFF_STAIRS);
	public static final RegistryObject<Item> POLISHED_TUFF_SLAB = block(TrialsBlocks.POLISHED_TUFF_SLAB);
	public static final RegistryObject<Item> POLISHED_TUFF_WALL = block(TrialsBlocks.POLISHED_TUFF_WALL);
	public static final RegistryObject<Item> TUFF_BRICKS = block(TrialsBlocks.TUFF_BRICKS);
	public static final RegistryObject<Item> TUFF_BRICKS_STAIRS = block(TrialsBlocks.TUFF_BRICKS_STAIRS);
	public static final RegistryObject<Item> TUFF_BRICKS_SLAB = block(TrialsBlocks.TUFF_BRICKS_SLAB);
	public static final RegistryObject<Item> TUFF_BRICKS_WALL = block(TrialsBlocks.TUFF_BRICKS_WALL);
	public static final RegistryObject<Item> CHISELED_TUFF = block(TrialsBlocks.CHISELED_TUFF);
	public static final RegistryObject<Item> CHISELED_TUFF_BRICKS = block(TrialsBlocks.CHISELED_TUFF_BRICKS);
	public static final RegistryObject<Item> CHISELED_TUFF_BULB = block(TrialsBlocks.CHISELED_TUFF_BULB);
	public static final RegistryObject<Item> CHISELED_COPPER = block(TrialsBlocks.CHISELED_COPPER);
	public static final RegistryObject<Item> EX_CHISELED_COPPER = block(TrialsBlocks.EX_CHISELED_COPPER);
	public static final RegistryObject<Item> W_CHISELED_COPPER = block(TrialsBlocks.W_CHISELED_COPPER);
	public static final RegistryObject<Item> OXI_CHISELED_COPPER = block(TrialsBlocks.OXI_CHISELED_COPPER);
	public static final RegistryObject<Item> WAXED_CHISELED_COPPER = block(TrialsBlocks.WAXED_CHISELED_COPPER);
	public static final RegistryObject<Item> WAXED_EX_CHISELED_COPPER = block(TrialsBlocks.WAXED_EX_CHISELED_COPPER);
	public static final RegistryObject<Item> WAXED_W_CHISELED_COPPER = block(TrialsBlocks.WAXED_W_CHISELED_COPPER);
	public static final RegistryObject<Item> WAXED_OXI_CHISELED_COPPER = block(TrialsBlocks.WAXED_OXI_CHISELED_COPPER);
	public static final RegistryObject<Item> COPPER_GRATE = block(TrialsBlocks.COPPER_GRATE);
	public static final RegistryObject<Item> EX_COPPER_GRATE = block(TrialsBlocks.EX_COPPER_GRATE);
	public static final RegistryObject<Item> W_COPPER_GRATE = block(TrialsBlocks.W_COPPER_GRATE);
	public static final RegistryObject<Item> OXI_COPPER_GRATE = block(TrialsBlocks.OXI_COPPER_GRATE);
	public static final RegistryObject<Item> WAXED_COPPER_GRATE = block(TrialsBlocks.WAXED_COPPER_GRATE);
	public static final RegistryObject<Item> WAXED_EX_COPPER_GRATE = block(TrialsBlocks.WAXED_EX_COPPER_GRATE);
	public static final RegistryObject<Item> WAXED_W_COPPER_GRATE = block(TrialsBlocks.WAXED_W_COPPER_GRATE);
	public static final RegistryObject<Item> WAXED_OXI_COPPER_GRATE = block(TrialsBlocks.WAXED_OXI_COPPER_GRATE);
	public static final RegistryObject<Item> COPPER_TRAPDOOR = block(TrialsBlocks.COPPER_TRAPDOOR);
	public static final RegistryObject<Item> EX_COPPER_TRAPDOOR = block(TrialsBlocks.EX_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> W_COPPER_TRAPDOOR = block(TrialsBlocks.W_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> OXI_COPPER_TRAPDOOR = block(TrialsBlocks.OXI_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> WAXED_COPPER_TRAPDOOR = block(TrialsBlocks.WAXED_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> WAXED_EX_COPPER_TRAPDOOR = block(TrialsBlocks.WAXED_EX_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> WAXED_W_COPPER_TRAPDOOR = block(TrialsBlocks.WAXED_W_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> WAXED_OXI_COPPER_TRAPDOOR = block(TrialsBlocks.WAXED_OXI_COPPER_TRAPDOOR);
	public static final RegistryObject<Item> COPPER_DOOR = block(TrialsBlocks.COPPER_DOOR);
	public static final RegistryObject<Item> EX_COPPER_DOOR = block(TrialsBlocks.EX_COPPER_DOOR);
	public static final RegistryObject<Item> W_COPPER_DOOR = block(TrialsBlocks.W_COPPER_DOOR);
	public static final RegistryObject<Item> OXI_COPPER_DOOR = block(TrialsBlocks.OXI_COPPER_DOOR);
	public static final RegistryObject<Item> WAXED_COPPER_DOOR = block(TrialsBlocks.WAXED_COPPER_DOOR);
	public static final RegistryObject<Item> WAXED_EX_COPPER_DOOR = block(TrialsBlocks.WAXED_EX_COPPER_DOOR);
	public static final RegistryObject<Item> WAXED_W_COPPER_DOOR = block(TrialsBlocks.WAXED_W_COPPER_DOOR);
	public static final RegistryObject<Item> WAXED_OXI_COPPER_DOOR = block(TrialsBlocks.WAXED_OXI_COPPER_DOOR);
	public static final RegistryObject<Item> COPPER_BULB = block(TrialsBlocks.COPPER_BULB);
	public static final RegistryObject<Item> EX_COPPER_BULB = block(TrialsBlocks.EX_COPPER_BULB);
	public static final RegistryObject<Item> W_COPPER_BULB = block(TrialsBlocks.W_COPPER_BULB);
	public static final RegistryObject<Item> OXI_COPPER_BULB = block(TrialsBlocks.OXI_COPPER_BULB);
	public static final RegistryObject<Item> WAXED_COPPER_BULB = block(TrialsBlocks.WAXED_COPPER_BULB);
	public static final RegistryObject<Item> WAXED_EX_COPPER_BULB = block(TrialsBlocks.WAXED_EX_COPPER_BULB);
	public static final RegistryObject<Item> WAXED_W_COPPER_BULB = block(TrialsBlocks.WAXED_W_COPPER_BULB);
	public static final RegistryObject<Item> WAXED_OXI_COPPER_BULB = block(TrialsBlocks.WAXED_OXI_COPPER_BULB);
	public static final RegistryObject<Item> CRAFTER = block(TrialsBlocks.CRAFTER);
	public static final RegistryObject<Item> SPAWNER = block(TrialsBlocks.SPAWNER);
	public static final RegistryObject<Item> VAULT = block(TrialsBlocks.VAULT);
	public static final RegistryObject<Item> VAULT_OMNI = block(TrialsBlocks.VAULT_OMNI);
	public static final RegistryObject<Item> HEAVY_CORE = block(TrialsBlocks.HEAVY_CORE);
	public static final RegistryObject<Item> MACE = REGISTRY.register("mace", () -> new MaceItem(TrialsItemTiers.MACE, 2, -2.8F, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> TRIAL_KEY = REGISTRY.register("trial_key", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TRIAL_KEY_OMNI = REGISTRY.register("trial_key_ominous", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BREEZE_ROD = REGISTRY.register("breeze_rod", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> WIND_CHARGE = REGISTRY.register("wind_charge", () -> new WindItem(new Item.Properties()));
	public static final RegistryObject<Item> TRIAL_BOTTLE = REGISTRY.register("ominous_bottle", () -> new OmniItem(new Item.Properties()));
	public static final RegistryObject<Item> BOGGED_SPAWN_EGG = REGISTRY.register("bogged_spawn_egg", () -> new ForgeSpawnEggItem(TrialsMobs.BOGGED, 5596200, 1646340, new Item.Properties()));
	public static final RegistryObject<Item> BREEZE_SPAWN_EGG = REGISTRY.register("breeze_spawn_egg", () -> new ForgeSpawnEggItem(TrialsMobs.BREEZE, 12562377, 4087431, new Item.Properties()));
	public static final RegistryObject<Item> FLOW_SHERD = REGISTRY.register("flow_pottery_sherd", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GUSTER_SHERD = REGISTRY.register("guster_pottery_sherd", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SCRAPE_SHERD = REGISTRY.register("scrape_pottery_sherd", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BOLT_TEMPLATE = REGISTRY.register("bolt_template", () -> SmithingTemplateItem.createArmorTrimTemplate(new ResourceLocation("bolt")));
	public static final RegistryObject<Item> FLOW_TEMPLATE = REGISTRY.register("flow_template", () -> SmithingTemplateItem.createArmorTrimTemplate(new ResourceLocation("flow")));
	public static final RegistryObject<Item> MUSIC_DISC_CREATOR = REGISTRY.register("music_disc_creator", () -> new RecordItem(0, () -> TrialsSounds.MUSIC_DISC_CREATOR.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3540));
	public static final RegistryObject<Item> MUSIC_DISC_CREATOR_BOX = REGISTRY.register("music_disc_creator_box", () -> new RecordItem(0, () -> TrialsSounds.MUSIC_DISC_CREATOR_BOX.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1480));
	public static final RegistryObject<Item> MUSIC_DISC_PRECIPICE = REGISTRY.register("music_disc_precipice", () -> new RecordItem(0, () -> TrialsSounds.MUSIC_DISC_PRECIPICE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 5980));
	public static final RegistryObject<Item> BANNER_PATTERN_FLOW = REGISTRY.register("banner_pattern_flow", () -> new BannerPatternItem(TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Trials.MODID, "pattern_for_flow")), (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> BANNER_PATTERN_GUSTER = REGISTRY.register("banner_pattern_guster", () -> new BannerPatternItem(TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Trials.MODID, "pattern_for_guster")), (new Item.Properties()).stacksTo(1)));

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().rarity(block == TrialsBlocks.HEAVY_CORE ? Rarity.EPIC : Rarity.COMMON)));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			DispenserBlock.registerBehavior(WIND_CHARGE.get(), new DefaultDispenseItemBehavior() {
				public ItemStack execute(BlockSource blok, ItemStack stack) {
					Direction dir = blok.getBlockState().getValue(DispenserBlock.FACING);
					WindCharge windy = new WindCharge(TrialsMobs.WIND.get(), blok.getLevel());
					DispenseItemBehavior.setEntityPokingOutOfBlock(blok, windy, dir);
					BlockPos pos = blok.getPos().offset(dir.getStepX(), dir.getStepY(), dir.getStepZ());
					windy.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5); // Center it in the block
					windy.shoot(dir.getStepX(), dir.getStepY(), dir.getStepZ(), 1.45F, 1.0F);
					blok.getLevel().addFreshEntity(windy);
					stack.shrink(1);
					return stack;
				}
				protected void playSound(BlockSource blok) {
					blok.getLevel().levelEvent(1004, blok.getPos(), 0);
				}
			});
		});
	}
}