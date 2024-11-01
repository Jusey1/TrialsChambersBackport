package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsTags;
import net.salju.trialstowers.init.TrialsSounds;
import net.salju.trialstowers.init.TrialsItems;
import net.salju.trialstowers.init.TrialsEffects;
import net.salju.trialstowers.init.TrialsBlockEntities;
import net.salju.trialstowers.events.TrialsManager;
import net.salju.trialstowers.Trials;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Containers;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.Connection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.List;
import java.time.temporal.ChronoField;
import java.time.LocalDate;
import com.google.common.collect.Lists;

public class TrialSpawnerEntity extends BlockEntity {
	private String table;
	private ItemStack egg;
	private List<UUID> mobs = Lists.newArrayList();
	private boolean isActive;
	private int d;
	private int cd;
	private int e;
	private int k;
	private boolean disableEffects;

	public TrialSpawnerEntity(BlockPos pos, BlockState state) {
		super(TrialsBlockEntities.SPAWNER.get(), pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (this.table != null) {
			tag.putString("LootTable", this.table);
		}
		if (this.egg != null) {
			tag.put("SpawnEgg", this.egg.save(new CompoundTag()));
		}
		tag.putBoolean("DisableEffects", this.disableEffects);
		tag.putBoolean("isActive", this.isActive);
		tag.putInt("Difficulty", this.d);
		tag.putInt("Cooldown", this.cd);
		tag.putInt("Enemies", this.e);
		tag.putInt("Killed", this.k);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("LootTable")) {
			this.table = tag.getString("LootTable");
		}
		if (tag.contains("SpawnEgg")) {
			this.egg = ItemStack.of(tag.getCompound("SpawnEgg"));
		}
		this.disableEffects = tag.getBoolean("DisableEffects");
		this.isActive = tag.getBoolean("isActive");
		this.d = tag.getInt("Difficulty");
		this.cd = tag.getInt("Cooldown");
		this.e = tag.getInt("Enemies");
		this.k = tag.getInt("Killed");
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection queen, ClientboundBlockEntityDataPacket packet) {
		if (packet != null && packet.getTag() != null) {
			if (packet.getTag().contains("LootTable")) {
				this.table = packet.getTag().getString("LootTable");
			}
			if (packet.getTag().contains("SpawnEgg")) {
				this.egg = ItemStack.of(packet.getTag().getCompound("SpawnEgg"));
			}
			this.disableEffects = packet.getTag().getBoolean("DisableEffects");
			this.isActive = packet.getTag().getBoolean("isActive");
			this.d = packet.getTag().getInt("Difficulty");
			this.cd = packet.getTag().getInt("Cooldown");
			this.e = packet.getTag().getInt("Enemies");
			this.k = packet.getTag().getInt("Killed");
		}
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		if (this.table != null) {
			tag.putString("LootTable", this.table);
		}
		if (this.egg != null) {
			tag.put("SpawnEgg", this.egg.save(new CompoundTag()));
		}
		tag.putBoolean("DisableEffects", this.disableEffects);
		tag.putBoolean("isActive", this.isActive);
		tag.putInt("Difficulty", this.d);
		tag.putInt("Cooldown", this.cd);
		tag.putInt("Enemies", this.e);
		tag.putInt("Killed", this.k);
		return tag;
	}

	public static void tick(Level world, BlockPos pos, BlockState state, TrialSpawnerEntity target) {
		if (state.getBlock() instanceof TrialSpawnerBlock block && target.getSpawnType() != null && world.getDifficulty() != Difficulty.PEACEFUL) {
			target.updateBlock();
			if (world instanceof ServerLevel lvl) {
				if (block.isActive(state)) {
					lvl.sendParticles(block.isCursed(state) ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, (pos.getX() + 0.5), (pos.getY() + 0.5), (pos.getZ() + 0.5), 1, 0.12, 0.12, 0.12, 0);
					if (target.isActivelySpawning()) {
						if (target.getRemainingEnemies() != 0) {
							checkPlayer(lvl, pos, state, target);
							if (target.getTotalEnemies() > 0) {
								if (target.getCd() != 0) {
									target.setCd(target.getCd() - 1);
								} else if (target.getRemainingEnemies() == target.getTotalEnemies()) {
									target.setCd(80);
									int e = (target.getDifficulty() >= 101 ? 3 : 2);
									target.setTotalEnemies(target.getTotalEnemies() - e);
									lvl.playSound(null, pos, TrialsSounds.SPAWNER_SUMMON.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
									for (int i = 0; i != e; ++i) {
										setUpMob(target, target.getSpawnType(), lvl, target.getDifficulty(), target.findSpawnPositionNear(target.getSpawnType(), lvl, pos.above(), 2, world.getRandom()), target.egg);
									}
								}
							}
						} else if (block.isEjecting(state)) {
							if (target.getCd() != 0) {
								target.setCd(target.getCd() - 1);
							} else {
								target.setActivity(false);
								target.setCd(36000);
								lvl.playSound(null, pos, TrialsSounds.SPAWNER_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
								world.setBlock(pos, state.setValue(TrialSpawnerBlock.ACTIVE, Boolean.valueOf(false)).setValue(TrialSpawnerBlock.EJECT, Boolean.valueOf(false)).setValue(TrialSpawnerBlock.CURSED, Boolean.valueOf(false)), 3);
							}
						} else {
							target.setCd(40);
							Trials.queueServerWork(20, () -> {
								lvl.playSound(null, pos, TrialsSounds.SPAWNER_ITEM.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
								for (ItemStack stack : TrialsManager.getLoot(target, world, target.getLootTable(block.isCursed(state)))) {
									Containers.dropItemStack(world, pos.getX(), (pos.getY() + 1.0), pos.getZ(), stack);
								}
							});
							lvl.playSound(null, pos, TrialsSounds.SPAWNER_OPEN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
							world.setBlock(pos, state.setValue(TrialSpawnerBlock.EJECT, Boolean.valueOf(true)), 3);
						}
					} else {
						Player player = lvl.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 8, false);
						if (player != null) {
							if (!player.isCreative() && !player.isSpectator() && canSpawn(lvl, pos, player)) {
								int i = 0;
								if (player.hasEffect(MobEffects.BAD_OMEN)) {
									player.addEffect(new MobEffectInstance(TrialsEffects.CURSED.get(), 12000, player.getEffect(MobEffects.BAD_OMEN).getAmplifier()));
									player.removeEffect(MobEffects.BAD_OMEN);
								}
								for (Player players : lvl.getPlayers(LivingEntity::isAlive)) {
									if (players.isCloseEnough(player, 32) && !players.isCreative() && !players.isSpectator()) {
										i++;
									}
								}
								lvl.playSound(null, pos, TrialsSounds.SPAWNER_DETECT_PLAYER.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
								if (player.hasEffect(TrialsEffects.CURSED.get())) {
									target.setDifficulty(Mth.nextInt(world.getRandom(), 101, 200));
									i = (6 + (i * 3) + (player.getEffect(TrialsEffects.CURSED.get()).getAmplifier() * 3));
									world.setBlock(pos, state.setValue(TrialSpawnerBlock.CURSED, Boolean.valueOf(true)), 3);
									lvl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), (pos.getY() + 1.0), pos.getZ(), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, (pos.getX() + 1.0), (pos.getY() + 1.0), pos.getZ(), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, (pos.getX() + 1.0), (pos.getY() + 1.0), (pos.getZ() + 1.0), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), (pos.getY() + 1.0), (pos.getZ() + 1.0), 8, 0.1, 0.1, 0.1, 0);
								} else {
									i = (4 + (i * 2));
									target.setDifficulty(Mth.nextInt(world.getRandom(), 1, 100));
									lvl.sendParticles(ParticleTypes.FLAME, pos.getX(), (pos.getY() + 1.0), pos.getZ(), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.FLAME, (pos.getX() + 1.0), (pos.getY() + 1.0), pos.getZ(), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.FLAME, (pos.getX() + 1.0), (pos.getY() + 1.0), (pos.getZ() + 1.0), 8, 0.1, 0.1, 0.1, 0);
									lvl.sendParticles(ParticleTypes.FLAME, pos.getX(), (pos.getY() + 1.0), (pos.getZ() + 1.0), 8, 0.1, 0.1, 0.1, 0);
								}
								target.setActivity(true);
								target.setTotalEnemies(i);
								target.setRemainingEnemies(i);
								target.setCd(20);
							}
						}
					}
				} else {
					lvl.sendParticles(ParticleTypes.SMOKE, (pos.getX() + 0.5), (pos.getY() + 0.95), (pos.getZ() + 0.5), 1, 0.12, 0.12, 0.12, 0);
					if (target.getCd() != 0) {
						target.setCd(target.getCd() - 1);
					} else {
						world.setBlock(pos, state.setValue(TrialSpawnerBlock.ACTIVE, Boolean.valueOf(true)), 3);
					}
				}
			}
		}
	}

	public static boolean canSpawn(ServerLevel lvl, BlockPos pos, Player player) {
		boolean check = true;
		for (BlockPos checkpos : BlockPos.betweenClosed(pos.above(), player.blockPosition().above())) {
			BlockState checkstate = lvl.getBlockState(checkpos);
			if (checkstate.canOcclude()) {
				check = false;
				break;
			}
		}
		return check;
	}

	public static void checkPlayer(ServerLevel lvl, BlockPos pos, BlockState state, TrialSpawnerEntity target) {
		Player player = lvl.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 64, false);
		if (player != null) {
			List<UUID> list = target.getMobs();
			if (!list.isEmpty()) {
				for (int i =0; i < list.size(); i++) {
					if (lvl.getEntity(list.get(i)) == null || (lvl.getEntity(list.get(i)) instanceof Mob mobster && !mobster.isAlive())) {
						target.setRemainingEnemies(target.getRemainingEnemies() - 1);
						target.removeUUID(i);
					}
				}
			}
		} else {
			shutOff(lvl, pos, state, target);
		}
	}

	public static void shutOff(ServerLevel lvl, BlockPos pos, BlockState state, TrialSpawnerEntity target) {
		target.setActivity(false);
		target.setRemainingEnemies(0);
		target.setTotalEnemies(0);
		target.setCd(12000);
		lvl.setBlock(pos, state.setValue(TrialSpawnerBlock.ACTIVE, Boolean.valueOf(false)).setValue(TrialSpawnerBlock.CURSED, Boolean.valueOf(false)), 3);
	}

	public static void setUpMob(TrialSpawnerEntity spawner, EntityType<?> type, ServerLevel lvl, int i, BlockPos pos, ItemStack egg) {
		Entity entity = type.spawn(lvl, egg, null, pos, MobSpawnType.SPAWNER, false, false);
		lvl.sendParticles(i >= 101 ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, pos.getX(), (pos.getY() + 1.05), pos.getZ(), 12, 0.45, 0.25, 0.45, 0);
		entity.moveTo(Vec3.atBottomCenterOf(pos));
		if (entity instanceof Mob target) {
			spawner.addUUID(target.getUUID());
			target.setPersistenceRequired();
			target.getPersistentData().putBoolean("TrialSpawned", true);
			target.setCanPickUpLoot(false);
			target.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
			target.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
			target.setDropChance(EquipmentSlot.HEAD, 0.0F);
			target.setDropChance(EquipmentSlot.CHEST, 0.0F);
			target.setDropChance(EquipmentSlot.LEGS, 0.0F);
			target.setDropChance(EquipmentSlot.FEET, 0.0F);
			if (!spawner.disableEffects) {
				if (target.getType().is(TrialsTags.ARMORABLE) && i > 65) {
					if (LocalDate.now().get(ChronoField.DAY_OF_MONTH) == 31 && LocalDate.now().get(ChronoField.MONTH_OF_YEAR) == 10) {
						target.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Math.random() <= 0.1 ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
					} else if (Math.random() >= 0.25) {
						target.setItemSlot(EquipmentSlot.HEAD, new ItemStack(i >= 101 ? Items.DIAMOND_HELMET : Items.IRON_HELMET));
					}
					if (Math.random() >= 0.65) {
						target.setItemSlot(EquipmentSlot.CHEST, new ItemStack(i >= 101 ? Items.DIAMOND_CHESTPLATE : Items.IRON_CHESTPLATE));
					}
					if (Math.random() >= 0.54) {
						target.setItemSlot(EquipmentSlot.LEGS, new ItemStack(i >= 101 ? Items.DIAMOND_LEGGINGS : Items.IRON_LEGGINGS));
					}
					if (Math.random() >= 0.45) {
						target.setItemSlot(EquipmentSlot.FEET, new ItemStack(i >= 101 ? Items.DIAMOND_BOOTS : Items.IRON_BOOTS));
					}
					Holder.Reference<TrimMaterial> m = getTrim(lvl);
					for (ItemStack armor : target.getArmorSlots()) {
						if (armor.is(ItemTags.TRIMMABLE_ARMOR)) {
							ArmorTrim.setTrim(lvl.registryAccess(), armor, new ArmorTrim(m, TrimPatterns.getFromTemplate(lvl.registryAccess(), new ItemStack(i <= 1 ? TrialsItems.FLOW_TEMPLATE.get() : TrialsItems.BOLT_TEMPLATE.get())).get()));
						}
					}
				}
				if (target instanceof Zombie) {
					if ((i > 76 || i < 21) && Math.random() >= 0.45) {
						target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
					}
					if (i >= 101) {
						if (target instanceof Drowned && Math.random() >= 0.45) {
							target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.TRIDENT));
						} else if (Math.random() >= 0.65) {
							target.addEffect(new MobEffectInstance(TrialsEffects.OOZE.get(), 12000, 0));
						}
					}
				} else if (target instanceof AbstractSkeleton) {
					target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
					if (i >= 101) {
						if (Math.random() >= 0.65) {
							target.addEffect(new MobEffectInstance(TrialsEffects.INFESTED.get(), 12000, 0));
						}
					}
				} else if (target instanceof Spider) {
					if (i >= 101) {
						if (Math.random() >= 0.65) {
							target.addEffect(new MobEffectInstance(TrialsEffects.WEAVE.get(), 12000, 0));
						}
						target.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 12000, 0));
						target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0));
						target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 0));
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0));
					} else if (i > 90) {
						target.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 12000, 0));
					} else if (i > 75) {
						target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0));
					} else if (i < 10) {
						target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 0));
					} else if (i < 25) {
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0));
					}
				} else if (target instanceof AbstractIllager) {
					((GroundPathNavigation) target.getNavigation()).setCanOpenDoors(true);
					if (i >= 101) {
						if (Math.random() >= 0.65) {
							target.addEffect(new MobEffectInstance(TrialsEffects.WINDED.get(), 12000, 0));
						}
						target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0));
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0));
					} else if (i > 75) {
						target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0));
					} else if (i < 45) {
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0));
					}
					if (target instanceof Pillager) {
						target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.CROSSBOW));
					} else if (target instanceof Vindicator) {
						target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
					}
				} else if (target instanceof Vex) {
					target.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
					target.setLeftHanded(false);
				} else if (target instanceof Slime slym) {
					if (i >= 101) {
						slym.setSize(3, true);
					} else {
						slym.setSize(2, true);
					}
				} else {
					target.finalizeSpawn(lvl, new DifficultyInstance(lvl.getDifficulty(), 5L, 5L, 1.0F), MobSpawnType.SPAWNER, null, null);
				}
			}
		}
	}

	public static Holder.Reference<TrimMaterial> getTrim(ServerLevel lvl) {
		int i = Mth.nextInt(lvl.getRandom(), 0, 7);
		List<Item> list = getMats();
		return TrimMaterials.getFromIngredient(lvl.registryAccess(), new ItemStack(list.get(i))).get();
	}

	private static List<Item> getMats() {
		List<Item> list = Lists.newArrayList();
		list.add(Items.COPPER_INGOT);
		list.add(Items.IRON_INGOT);
		list.add(Items.GOLD_INGOT);
		list.add(Items.LAPIS_LAZULI);
		list.add(Items.REDSTONE);
		list.add(Items.AMETHYST_SHARD);
		list.add(Items.EMERALD);
		list.add(Items.DIAMOND);
		return list;
	}

	@Nullable
	public EntityType<?> getSpawnType() {
		if (this.egg != null && this.egg.getItem() instanceof SpawnEggItem item) {
			return item.getType(this.egg.getTag());
		}
		return null;
	}

	public String getLootTable(boolean check) {
		if (this.table != null) {
			return this.table;
		}
		return (check ? "trials:gameplay/spawner_special_loot" : "trials:gameplay/spawner_loot");
	}

	public BlockPos findSpawnPositionNear(EntityType<?> type, ServerLevel lvl, BlockPos old, int range, RandomSource random) {
		BlockPos pos = old;
		for (int i = 0; i < 25; ++i) {
			int x = old.getX() + random.nextInt(range * 2) - range;
			int y = old.getY() + random.nextInt(range * 2) - range;
			int z = old.getZ() + random.nextInt(range * 2) - range;
			BlockPos test = new BlockPos(x, y, z);
			if (lvl.noCollision(type.getAABB(test.getX(), test.getY(), test.getZ()))) {
				pos = test;
				break;
			}
		}
		return BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
	}

	public void addUUID(UUID id) {
		this.mobs.add(id);
	}

	public void removeUUID(int i) {
		this.mobs.remove(i);
	}

	public void setEgg(ItemStack stack) {
		this.egg = stack;
	}

	public List<UUID> getMobs() {
		return this.mobs;
	}

	public boolean isActivelySpawning() {
		return this.isActive;
	}

	public int getDifficulty() {
		return this.d;
	}

	public int getCd() {
		return this.cd;
	}

	public int getTotalEnemies() {
		return this.e;
	}

	public int getRemainingEnemies() {
		return this.k;
	}

	public void setActivity(boolean check) {
		this.isActive = check;
	}

	public void setDifficulty(int i) {
		this.d = i;
	}

	public void setCd(int i) {
		this.cd = i;
	}

	public void setTotalEnemies(int i) {
		this.e = i;
	}

	public void setRemainingEnemies(int i) {
		this.k = i;
	}

	public void updateBlock() {
		this.setChanged();
		this.getLevel().updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}
}