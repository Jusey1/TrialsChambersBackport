package net.salju.trialstowers.network;

import net.salju.trialstowers.Trials;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkSetup {
	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Trials.addNetworkMessage(ApplyKnockback.class, ApplyKnockback::buffer, ApplyKnockback::reader, ApplyKnockback::handler);
	}
}