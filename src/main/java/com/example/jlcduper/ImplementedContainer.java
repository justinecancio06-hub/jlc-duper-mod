package com.example.jlcduper;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ImplementedContainer extends Container {
	NonNullList<ItemStack> getInventory();

	@Override
	default int getContainerSize() {
		return getInventory().size();
	}

	@Override
	default boolean isEmpty() {
		for (ItemStack stack : getInventory()) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	default ItemStack getItem(int slot) {
		return getInventory().get(slot);
	}

	@Override
	default ItemStack removeItem(int slot, int amount) {
		ItemStack result = ContainerHelper.removeItem(getInventory(), slot, amount);
		if (!result.isEmpty()) {
			this.setChanged();
		}
		return result;
	}

	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(getInventory(), slot);
	}

	@Override
	default void setItem(int slot, ItemStack stack) {
		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize(stack)) {
			stack.setCount(this.getMaxStackSize(stack));
		}
		getInventory().set(slot, stack);
		this.setChanged();
	}

	@Override
	default void clearContent() {
		getInventory().clear();
	}

	@Override
	default boolean stillValid(Player player) {
		return true;
	}
}