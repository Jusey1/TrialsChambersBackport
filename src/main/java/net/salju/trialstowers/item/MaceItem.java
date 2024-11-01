package net.salju.trialstowers.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;

public class MaceItem extends TieredItem implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	private float damage;

	public MaceItem(Tier t, int i, float f, Item.Properties props) {
		super(t, props);
		this.attackDamage = (float) i + t.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) f, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return state.is(BlockTags.BEDS) ? 2.5F : 1.0F;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity target) {
		if (state.getDestroySpeed(world, pos) != 0.0F) {
			stack.hurtAndBreak(2, target, (user) -> {
				user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}
		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state) {
		return state.is(BlockTags.BEDS);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment ench) {
		return (ench instanceof DamageEnchantment || ench instanceof FireAspectEnchantment || super.canApplyAtEnchantingTable(stack, ench));
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
	}

	public float getMaceDamage(DamageSource source, LivingEntity target, int i) {
		float f1 = (1.0F - (i * 0.25F));
		this.damage = CombatRules.getDamageAfterAbsorb(this.damage, ((float) target.getArmorValue() * f1), ((float) target.getAttributeValue(Attributes.ARMOR_TOUGHNESS) * f1));
		if (target.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
			int e = (target.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
			this.damage = Math.max((this.damage * ((float) 25 - e)) / 25.0F, 0.0F);
		}
		this.damage = CombatRules.getDamageAfterMagicAbsorb(this.damage, ((float) EnchantmentHelper.getDamageProtection(target.getArmorSlots(), source)));
		return this.damage;
	}

	public void setMaceDamage(float f) {
		this.damage = f;
	}
}