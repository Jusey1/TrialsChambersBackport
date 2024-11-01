package net.salju.trialstowers.events;

import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;

public class TrialsVillagerManager {
	static class TreasureMapForEmeralds implements VillagerTrades.ItemListing {
		private final int c;
		private final TagKey<Structure> d;
		private final String name;
		private final MapDecoration.Type type;
		private final int m;
		private final int x;

		public TreasureMapForEmeralds(int e, TagKey<Structure> tag, String str, MapDecoration.Type type, int u, int p) {
			this.c= e;
			this.d = tag;
			this.name = str;
			this.type = type;
			this.m = u;
			this.x = p;
		}

		@Nullable
		public MerchantOffer getOffer(Entity target, RandomSource rng) {
			if (!(target.level() instanceof ServerLevel)) {
				return null;
			} else {
				ServerLevel lvl = (ServerLevel) target.level();
				BlockPos pos = lvl.findNearestMapStructure(this.d, target.blockPosition(), 100, true);
				if (pos != null) {
					ItemStack stack = MapItem.create(lvl, pos.getX(), pos.getZ(), (byte) 2, true, true);
					MapItem.renderBiomePreviewMap(lvl, stack);
					MapItemSavedData.addTargetDecoration(stack, pos, "+", this.type);
					stack.setHoverName(Component.translatable(this.name));
					return new MerchantOffer(new ItemStack(Items.EMERALD, this.c), new ItemStack(Items.COMPASS), stack, this.m, this.x, 0.2F);
				} else {
					return null;
				}
			}
		}
	}
}