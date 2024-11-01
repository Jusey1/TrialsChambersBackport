package net.salju.trialstowers.client.renderer;

import net.salju.trialstowers.init.TrialsModels;
import net.salju.trialstowers.entity.Breeze;
import net.salju.trialstowers.client.renderer.layers.BreezeWindsLayer;
import net.salju.trialstowers.client.renderer.layers.BreezeEyesLayer;
import net.salju.trialstowers.client.model.BreezeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BreezeRenderer extends MobRenderer<Breeze, BreezeModel<Breeze>> {
	public BreezeRenderer(EntityRendererProvider.Context context) {
		super(context, new BreezeModel<>(context.bakeLayer(TrialsModels.BREEZE)), 0.5F);
		this.addLayer(new BreezeWindsLayer<>(this, context.getModelSet()));
		this.addLayer(new BreezeEyesLayer<>(this));
	}

	@Override
	protected int getBlockLightLevel(Breeze brezo, BlockPos pos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(Breeze brezo) {
		return new ResourceLocation("trials:textures/entity/breeze.png");
	}
}