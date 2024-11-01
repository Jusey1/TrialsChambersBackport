package net.salju.trialstowers.client.model;

import net.salju.trialstowers.entity.Bogged;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.HumanoidModel;

public class BoggedModel<T extends Bogged> extends SkeletonModel<T> {
	
	public BoggedModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_01", CubeListBuilder.create().texOffs(48, 29).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 4.0F, 0.0F, 0.0F, -0.7854F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_02", CubeListBuilder.create().texOffs(48, 29).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 4.0F, 0.0F, 0.0F, 0.7854F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_03", CubeListBuilder.create().texOffs(52, 23).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -7.0F, -3.0F, 0.0F, -0.7854F, 0.0F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_04", CubeListBuilder.create().texOffs(52, 23).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -7.0F, -3.0F, 0.0F, 0.7854F, 0.0F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_05", CubeListBuilder.create().texOffs(50, 16).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -7.0F, 3.0F, 0.0F, -0.7854F, 0.0F));
		partdefinition.getChild("hat").addOrReplaceChild("mush_06", CubeListBuilder.create().texOffs(50, 16).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -7.0F, 3.0F, 0.0F, 0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}