package net.salju.trialstowers.client.renderer;

import net.salju.trialstowers.init.TrialsModels;
import net.salju.trialstowers.entity.WindCharge;
import net.salju.trialstowers.client.model.WindChargeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class WindChargeRenderer extends EntityRenderer<WindCharge> {
	private final WindChargeModel model;

	public WindChargeRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new WindChargeModel(context.bakeLayer(TrialsModels.WINDCHARGE));
	}

	@Override
	public void render(WindCharge target, float f1, float f2, PoseStack pose, MultiBufferSource buffer, int i) {
		this.model.renderToBuffer(pose, VertexMultiConsumer.create(buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(target)))), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		this.model.setupAnim(target, 0.0F, 0.0F, f2, 0.0F, 0.0F);
		super.render(target, f1, f2, pose, buffer, i);
	}

	@Override
	protected int getBlockLightLevel(WindCharge target, BlockPos pos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(WindCharge target) {
		return new ResourceLocation("trials:textures/entity/wind_charge.png");
	}
}