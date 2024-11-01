package net.salju.trialstowers.events;

import net.minecraftforge.fml.LogicalSide;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;
import javax.annotation.Nullable;

public class TrialsClientManager {
	@Nullable
	public static Player getPlayer(LogicalSide side) {
		if (side.isClient()) {
			return Minecraft.getInstance().player;
		} else {
			return null;
		}
	}

	public static void applyKnockback(Player player, double y) {
		if (player.getDeltaMovement().y() <= 0.0) {
			player.setDeltaMovement(new Vec3(player.getDeltaMovement().x(), (y * 0.15), player.getDeltaMovement().z()));
		} else if (player.getDeltaMovement().y() <= 5.0) {
			player.addDeltaMovement(new Vec3(player.getDeltaMovement().x(), (y * 0.15), player.getDeltaMovement().z()));
		}
	}
}