package net.salju.trialstowers.client.model;

import net.salju.trialstowers.entity.Breeze;
import net.minecraft.util.Mth;
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

public class BreezeWindModel<T extends Breeze> extends EntityModel<T> {
	private final ModelPart top;
	private final ModelPart middle;
	private final ModelPart bottom;

	public BreezeWindModel(ModelPart root) {
		this.top = root.getChild("top");
		this.middle = root.getChild("middle");
		this.bottom = root.getChild("bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -4.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(6, 6).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(105, 57).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));
		PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(74, 28).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(78, 32).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(49, 71).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));
		PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(1, 83).addBox(-2.5F, -3.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Breeze brezo, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		int e = (int) (1 * 360f);
		float f = (Math.floorMod(brezo.level().getGameTime(), (long) e) + ageInTicks) / (float) e;
		this.top.yRot = ((float) (f * Math.PI * 25));
		this.middle.yRot = ((float) (f * Math.PI * 25));
		this.bottom.yRot = ((float) (f * Math.PI * 25));
		float fml = ageInTicks * (float) Math.PI * -0.07F;
		this.top.x = Mth.cos(fml) * 1.0F;
		this.top.z = Mth.sin(fml) * 1.0F;
		this.middle.x = Mth.cos(fml) * 1.0F;
		this.middle.z = Mth.sin(fml) * 1.0F;
		this.bottom.x = Mth.cos(fml) * 1.0F;
		this.bottom.z = Mth.sin(fml) * 1.0F;
	}

	@Override
	public void renderToBuffer(PoseStack pose, VertexConsumer v, int l, int o, float red, float green, float blue, float alpha) {
		top.render(pose, v, l, o, red, green, blue, alpha);
		middle.render(pose, v, l, o, red, green, blue, alpha);
		bottom.render(pose, v, l, o, red, green, blue, alpha);
	}
}