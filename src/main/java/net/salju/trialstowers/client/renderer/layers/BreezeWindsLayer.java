package net.salju.trialstowers.client.renderer.layers;

import net.salju.trialstowers.init.TrialsModels;
import net.salju.trialstowers.entity.Breeze;
import net.salju.trialstowers.client.model.BreezeWindModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class BreezeWindsLayer<T extends Breeze, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final BreezeWindModel<T> model;

	public BreezeWindsLayer(RenderLayerParent<T, M> parent, EntityModelSet set) {
		super(parent);
		this.model = new BreezeWindModel<>(set.bakeLayer(TrialsModels.BREEZE_WIND));
	}

	@Override
	public void render(PoseStack pose, MultiBufferSource buffer, int i, Breeze brezo, float f1, float f2, float f3, float f4, float f5, float f6) {
		float f = (float) brezo.tickCount + f3;
		this.model.renderToBuffer(pose, VertexMultiConsumer.create(buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(brezo)))), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		this.model.setupAnim(brezo, f1, f2, f4, f5, f6);
	}

	public ResourceLocation getTextureLocation(Breeze brezo) {
		return new ResourceLocation("trials:textures/entity/breeze_wind.png");
	}
}