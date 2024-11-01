package net.salju.trialstowers.network;

import net.salju.trialstowers.events.TrialsClientManager;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;

public class ApplyKnockback {
	public static double getY;
	
	public ApplyKnockback(double y) {
		this.getY = y;
	}

	public static ApplyKnockback reader(FriendlyByteBuf buffer) {
		return new ApplyKnockback(buffer.readDouble());
	}

	public static void buffer(ApplyKnockback message, FriendlyByteBuf buffer) {
		buffer.writeDouble(getY);
	}

	public static void handler(ApplyKnockback message, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Player player = TrialsClientManager.getPlayer(context.get().getDirection().getReceptionSide());
			if (player != null) {
				TrialsClientManager.applyKnockback(player, getY);
			}
		});
		context.get().setPacketHandled(true);
	}
}