package net.salju.trialstowers.init;

import net.salju.trialstowers.entity.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrialsMobs {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Trials.MODID);
	public static final RegistryObject<EntityType<Bogged>> BOGGED = register("bogged", EntityType.Builder.<Bogged>of(Bogged::new, MobCategory.MONSTER).sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<Breeze>> BREEZE = register("breeze", EntityType.Builder.<Breeze>of(Breeze::new, MobCategory.MONSTER).sized(0.56f, 1.76f));
	public static final RegistryObject<EntityType<WindCharge>> WIND = register("wind_charge", EntityType.Builder.<WindCharge>of(WindCharge::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			Bogged.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(BOGGED.get(), Bogged.createAttributes().build());
		event.put(BREEZE.get(), Breeze.createAttributes().build());
	}
}