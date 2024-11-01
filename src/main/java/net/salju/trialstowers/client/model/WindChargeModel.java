package net.salju.trialstowers.client.model;

import net.salju.trialstowers.entity.WindCharge;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class WindChargeModel<T extends WindCharge> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart innerwinds;
	private final ModelPart outerwinds;

	public WindChargeModel(ModelPart root) {
		this.body = root.getChild("body");
		this.innerwinds = root.getChild("innerwinds");
		this.outerwinds = root.getChild("outerwinds");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));
		PartDefinition innerwinds = partdefinition.addOrReplaceChild("innerwinds", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));
		PartDefinition outerwinds = partdefinition.addOrReplaceChild("outerwinds", CubeListBuilder.create().texOffs(64, 14).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 32);
	}

	@Override
	public void setupAnim(WindCharge target, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		int e = (int) (1 * 360f);
		float f = (Math.floorMod(target.level().getGameTime(), (long) e) + ageInTicks) / (float) e;
		this.body.yRot = ((float) (f * Math.PI * 25));
		this.innerwinds.yRot = ((float) (f * Math.PI * 25));
		this.outerwinds.yRot = ((float) (f * Math.PI * 25));
	}

	@Override
	public void renderToBuffer(PoseStack pose, VertexConsumer v, int l, int o, float red, float green, float blue, float alpha) {
		body.render(pose, v, l, o, red, green, blue, alpha);
		innerwinds.render(pose, v, l, o, red, green, blue, alpha);
		outerwinds.render(pose, v, l, o, red, green, blue, alpha);
	}
}