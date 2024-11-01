package net.salju.trialstowers.client.model;

import net.salju.trialstowers.entity.Breeze;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;
import java.util.Arrays;

public class BreezeModel<T extends Breeze> extends HierarchicalModel<T> {
	private final ModelPart head;
	private final ModelPart[] rods;
	private final ModelPart root;

	public BreezeModel(ModelPart root) {
		this.head = root.getChild("head");
		this.rods = new ModelPart[3];
		this.root = root;
		Arrays.setAll(this.rods, (i) -> {
			return root.getChild(getPartName(i));
		});
	}

	private static String getPartName(int i) {
		return "rod" + i;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(4, 24).addBox(-5.0F, -5.0F, -4.25F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));
		partdefinition.addOrReplaceChild(getPartName(0), CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));
		partdefinition.addOrReplaceChild(getPartName(1), CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));
		partdefinition.addOrReplaceChild(getPartName(2), CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Breeze brezo, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.yRot = headYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.rods[0].yRot = -2.0944F;
		this.rods[1].yRot = 2.0944F;
		this.rods[2].yRot = 0.0F;
		int e = (int) (1 * 360f);
		float f = (Math.floorMod(brezo.level().getGameTime(), (long) e) + ageInTicks) / (float) e;
		for (int i = 0; i < 3; ++i) {
			this.rods[i].xRot = (brezo.isCharging() ? 0.1746F : 0.3491F);
			this.rods[i].yRot = (this.rods[i].yRot + ((float) (f * Math.PI * (brezo.isCharging() ? 45 : 25))));
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}