package net.salju.trialstowers.init;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TrialsProperties {
	public static final BooleanProperty ACTIVE = BooleanProperty.create("trials_active");
	public static final BooleanProperty EJECT = BooleanProperty.create("trials_ejecting");
	public static final BooleanProperty CURSED = BooleanProperty.create("trials_cursed");
	public static final BooleanProperty CRAFTING = BooleanProperty.create("trials_crafting");
}