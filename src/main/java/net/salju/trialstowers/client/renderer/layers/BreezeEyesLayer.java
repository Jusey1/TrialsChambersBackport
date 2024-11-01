package net.salju.trialstowers.client.renderer.layers;

import net.salju.trialstowers.entity.Breeze;
import net.salju.trialstowers.client.model.BreezeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;

public class BreezeEyesLayer<T extends Breeze, M extends BreezeModel<T>> extends EyesLayer<T, M> {
	public BreezeEyesLayer(RenderLayerParent<T, M> parent) {
		super(parent);
	}

	@Override
	public void render(PoseStack pose, MultiBufferSource buffer, int i, Breeze brezo, float f1, float f2, float f3, float f4, float f5, float f6) {
		this.getParentModel().renderToBuffer(pose, buffer.getBuffer(this.renderType()), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public RenderType renderType() {
		return RenderType.eyes(new ResourceLocation("trials:textures/entity/breeze_eyes.png"));
	}
}