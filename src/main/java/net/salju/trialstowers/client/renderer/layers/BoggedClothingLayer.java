package net.salju.trialstowers.client.renderer.layers;

import net.salju.trialstowers.init.TrialsModels;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;

public class BoggedClothingLayer<T extends Mob & RangedAttackMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final SkeletonModel<T> model;

	public BoggedClothingLayer(RenderLayerParent<T, M> parent, EntityModelSet set) {
		super(parent);
		this.model = new SkeletonModel<>(set.bakeLayer(TrialsModels.BOGGED_OUTER_LAYER));
	}

	@Override
	public void render(PoseStack pose, MultiBufferSource buffer, int i, T t, float f1, float f2, float f3, float f4, float f5, float f6) {
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, new ResourceLocation("trials:textures/entity/bogged_overlay.png"), pose, buffer, i, t, f1, f2, f4, f5, f6, f3, 1.0F, 1.0F, 1.0F);
	}
}