package net.salju.trialstowers.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

public class CrafterGuiScreen extends AbstractContainerScreen<CrafterGuiMenu> {
	public CrafterGuiScreen(CrafterGuiMenu menu, Inventory inv, Component text) {
		super(menu, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics ms, int x, int y, float ticks) {
		this.renderBackground(ms);
		super.render(ms, x, y, ticks);
		this.renderTooltip(ms, x, y);
	}

	@Override
	protected void renderBg(GuiGraphics ms, float ticks, int x, int y) {
		ms.blit(new ResourceLocation("trials:textures/gui/crafter.png"), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(int a, int b, int c) {
		if (a == 256) {
			this.onClose();
			return true;
		}
		return super.keyPressed(a, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics ms, int x, int y) {
		super.renderLabels(ms, x, y);
	}

	@Override
	public void init() {
		super.init();
	}
}