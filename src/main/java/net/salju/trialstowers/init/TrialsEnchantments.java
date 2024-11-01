package net.salju.trialstowers.init;

import net.salju.trialstowers.enchantment.*;
import net.salju.trialstowers.Trials;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.item.enchantment.Enchantment;

public class TrialsEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Trials.MODID);
	public static final RegistryObject<Enchantment> DENSITY = REGISTRY.register("density", () -> new MaceDamageEnchantment(Enchantment.Rarity.COMMON));
	public static final RegistryObject<Enchantment> BREACH = REGISTRY.register("breach", () -> new MaceArmorEnchantment(Enchantment.Rarity.UNCOMMON));
	public static final RegistryObject<Enchantment> WIND = REGISTRY.register("wind_burst", () -> new MaceSpecialEnchantment(Enchantment.Rarity.RARE));
}