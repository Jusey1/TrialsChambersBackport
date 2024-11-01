package net.salju.trialstowers;

import net.salju.trialstowers.init.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractMap;

@Mod("trials")
public class Trials {
	public static final Logger LOGGER = LogManager.getLogger(Trials.class);
	public static final String MODID = "trials";

	public Trials() {
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		TrialsStructures.REGISTRY.register(bus);
		TrialsMenus.REGISTRY.register(bus);
		TrialsSounds.REGISTRY.register(bus);
		TrialsMobs.REGISTRY.register(bus);
		TrialsBlocks.REGISTRY.register(bus);
		TrialsBlockEntities.REGISTRY.register(bus);
		TrialsItems.REGISTRY.register(bus);
		TrialsSherds.REGISTRY.register(bus);
		TrialsBanners.REGISTRY.register(bus);
		TrialsPaintings.REGISTRY.register(bus);
		TrialsEnchantments.REGISTRY.register(bus);
		TrialsEffects.REGISTRY.register(bus);
		TrialsPotions.REGISTRY.register(bus);
		TrialsTabs.REGISTRY.register(bus);
	}

	private static final String V = "1";
	public static final SimpleChannel PACKET = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> V, V::equals, V::equals);
	private static int id = 0;

	public static <T> void addNetworkMessage(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> supply) {
		PACKET.registerMessage(id, type, encoder, decoder, supply);
		id++;
	}

	public static <MSG> void sendToClientPlayer(MSG msg, ServerPlayer ply) {
		PACKET.sendTo(msg, ply.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new AbstractMap.SimpleEntry(action, tick));
	}

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
			workQueue.forEach(work -> {
				work.setValue(work.getValue() - 1);
				if (work.getValue() == 0)
					actions.add(work);
			});
			actions.forEach(e -> e.getKey().run());
			workQueue.removeAll(actions);
		}
	}
}