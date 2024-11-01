package net.salju.trialstowers.init;

import net.salju.trialstowers.client.model.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidArmorModel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TrialsModels {
	public static final ModelLayerLocation BREEZE = new ModelLayerLocation(new ResourceLocation("trials", "breeze"), "main");
	public static final ModelLayerLocation BREEZE_WIND = new ModelLayerLocation(new ResourceLocation("trials", "breeze_wind"), "main");
	public static final ModelLayerLocation BOGGED = new ModelLayerLocation(new ResourceLocation("trials", "bogged"), "main");
	public static final ModelLayerLocation BOGGED_OUTER_LAYER = new ModelLayerLocation(new ResourceLocation("trials", "bogged_layer"), "main");
	public static final ModelLayerLocation BOGGED_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation("trials", "bogged_armor_1"), "main");
	public static final ModelLayerLocation BOGGED_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation("trials", "bogged_armor_2"), "main");
	public static final ModelLayerLocation WINDCHARGE = new ModelLayerLocation(new ResourceLocation("trials", "wind"), "main");

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(WINDCHARGE, WindChargeModel::createBodyLayer);
		event.registerLayerDefinition(BREEZE, BreezeModel::createBodyLayer);
		event.registerLayerDefinition(BREEZE_WIND, BreezeWindModel::createBodyLayer);
		event.registerLayerDefinition(BOGGED, BoggedModel::createBodyLayer);
		event.registerLayerDefinition(BOGGED_OUTER_LAYER, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32));
		event.registerLayerDefinition(BOGGED_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
		event.registerLayerDefinition(BOGGED_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32));
	}
}
