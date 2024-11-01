package net.salju.trialstowers.block;

import net.salju.trialstowers.init.TrialsTags;
import net.salju.trialstowers.init.TrialsBlockEntities;
import net.salju.trialstowers.gui.CrafterGuiMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Containers;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.Connection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import java.util.Optional;
import java.util.List;

public class CrafterEntity extends BaseContainerBlockEntity implements CraftingContainer {
	public NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(10, ItemStack.EMPTY);

	public CrafterEntity(BlockPos pos, BlockState state) {
		super(TrialsBlockEntities.CRAFTER.get(), pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		ContainerHelper.saveAllItems(tag, this.stacks);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.stacks);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection queen, ClientboundBlockEntityDataPacket packet) {
		if (packet != null && packet.getTag() != null) {
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
			ContainerHelper.loadAllItems(packet.getTag(), this.stacks);
		}
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		ContainerHelper.saveAllItems(tag, this.stacks);
		return tag;
	}

	@Override
	public Component getDefaultName() {
		return Component.translatable("block.trials.crafter");
	}

	@Override
	public AbstractContainerMenu createMenu(int i, Inventory bag) {
		return new CrafterGuiMenu(i, bag, this);
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		return 3;
	}

	@Override
	public boolean isEmpty() {
		return this.getResultItem().isEmpty();
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public ItemStack getItem(int i) {
		if (this.isResultItem(this.stacks.get(i))) {
			return ItemStack.EMPTY;
		}
		return this.stacks.get(i);
	}

	@Override
	public boolean canPlaceItem(int i, ItemStack stack) {
		int c = 64;
		for (int e = 0; e < 9; ++e) {
			if (this.getItem(e).isEmpty() && !this.isLocked()) {
				i = e;
				break;
			} else if (this.getItem(e).getItem() == stack.getItem()) {
				if (this.getItem(e).getCount() == this.getItem(e).getMaxStackSize()) {
					continue;
				} else if (this.getItem(e).getCount() <= 1) {
					i = e;
					break;
				} else if (this.getItem(e).getCount() < c) {
					i = e;
					c = this.getItem(e).getCount();
				}
			}
		}
		if (this.getItem(i).isEmpty() && !this.isLocked()) {
			ItemStack copy = stack.copy();
			copy.setCount(1);
			this.setItem(i, copy);
			stack.shrink(1);
		} else if (this.getItem(i).getItem() == stack.getItem() && this.getItem(i).getCount() < this.getItem(i).getMaxStackSize()) {
			this.getItem(i).setCount(this.getItem(i).getCount() + 1);
			stack.shrink(1);
		}
		return false;
	}

	@Override
	public ItemStack removeItemNoUpdate(int i) {
		return ContainerHelper.takeItem(this.stacks, i);
	}

	@Override
	public ItemStack removeItem(int i, int e) {
		this.updateBlock();
		return ContainerHelper.removeItem(this.stacks, i, e);
	}

	@Override
	public void setItem(int i, ItemStack stack) {
		this.stacks.set(i, stack.copy());
		this.updateBlock();
	}

	@Override
	public void clearContent() {
		double x = (double) this.getBlockPos().getX();
		double y = ((double) this.getBlockPos().getY() + 0.5);
		double z = (double) this.getBlockPos().getZ();
		for (ItemStack stack : this.stacks) {
			if (!stack.isEmpty() && !this.isResultItem(stack)) {
				Containers.dropItemStack(this.getLevel(), x, y, z, stack);
			}
		}
		this.updateBlock();
	}

	@Override
	public List<ItemStack> getItems() {
		return List.copyOf(this.stacks);
	}

	@Override
	public void fillStackedContents(StackedContents conts) {
		for (ItemStack stack : this.stacks) {
			if (!stack.isEmpty() && !this.isResultItem(stack)) {
				conts.accountSimpleStack(stack);
			}
		}
	}

	public ItemStack getResultItem() {
		return this.stacks.get(9);
	}

	public void setResultItem(ItemStack stack) {
		this.stacks.set(9, stack.copy());
		this.updateBlock();
	}

	public int getPower() {
		int i = 0;
		for (ItemStack stack : this.stacks) {
			if (!stack.isEmpty() && !this.isResultItem(stack)) {
				i++;
			}
		}
		return i;
	}

	public boolean isResultItem(ItemStack stack) {
		return (stack == this.getResultItem());
	}

	public boolean canCraftItem() {
		if (this.isLocked()) {
			for (ItemStack stack : this.stacks) {
				if (stack.getCount() == 1 && !this.isResultItem(stack)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isLocked() {
		return this.getLevel().getBlockState(this.getBlockPos().below()).is(TrialsTags.LOCKED);
	}

	public void craftItem(Direction dir) {
		double x = ((double) this.getBlockPos().getX() + 0.5);
		double y = ((double) this.getBlockPos().getY() + 0.5);
		double z = ((double) this.getBlockPos().getZ() + 0.5);
		if (dir == Direction.EAST) {
			x = (x + 0.65);
		} else if (dir == Direction.WEST) {
			x = (x - 0.65);
		} else if (dir == Direction.NORTH) {
			z = (z - 0.65);
		} else if (dir == Direction.SOUTH) {
			z = (z + 0.65);
		}
		if (this.getLevel() instanceof ServerLevel lvl) {
			lvl.sendParticles(ParticleTypes.SMOKE, x, y, z, 6, 0.12, 0.12, 0.12, 0);
		}
		Containers.dropItemStack(this.getLevel(), x, y, z, this.getResultItem());
		for (ItemStack stack : this.stacks) {
			if (!stack.isEmpty() && !this.isResultItem(stack)) {
				if (stack.hasCraftingRemainingItem()) {
					Containers.dropItemStack(this.getLevel(), x, y, z, stack.getCraftingRemainingItem());
				}
				stack.shrink(1);
			}
		}
	}

	public void updateBlock() {
		this.setChanged();
		this.getLevel().updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public static void tick(Level world, BlockPos pos, BlockState state, CrafterEntity target) {
		if (world instanceof ServerLevel lvl) {
			Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, target, world);
			if (optional.isPresent()) {
				ItemStack crafted = optional.get().assemble(target, world.registryAccess());
				if (crafted.isItemEnabled(world.enabledFeatures())) {
					target.setResultItem(crafted);
				}
			} else {
				target.setResultItem(ItemStack.EMPTY);
			}
		}
	}
}