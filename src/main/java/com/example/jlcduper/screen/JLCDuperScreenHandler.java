package com.example.jlcduper.screen;

import com.example.jlcduper.JLCModBlocks;
import com.example.jlcduper.JLCModItems;
import com.example.jlcduper.JLCModScreenHandlers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JLCDuperScreenHandler extends AbstractContainerMenu {
	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	private static final int CONTAINER_SIZE = 3;
	private static final int PLAYER_INVENTORY_START = CONTAINER_SIZE;

	private final Container container;
	private final ContainerData data;

	public JLCDuperScreenHandler(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, new SimpleContainer(CONTAINER_SIZE), new SimpleContainerData(2));
	}

	public JLCDuperScreenHandler(int syncId, Inventory playerInventory, Container container, ContainerData data) {
		super(JLCModScreenHandlers.JLC_DUPER_SCREEN_HANDLER, syncId);
		checkContainerSize(container, CONTAINER_SIZE);
		this.container = container;
		this.data = data;
		container.startOpen(playerInventory.player);

		this.addSlot(new InputSlot(container, INPUT_SLOT, 56, 17));
		this.addSlot(new FuelSlot(container, FUEL_SLOT, 56, 53));
		this.addSlot(new OutputOnlySlot(container, OUTPUT_SLOT, 116, 35));

		this.addStandardInventorySlots(playerInventory, 8, 84);

		this.addDataSlots(data);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			result = stack.copy();
			if (index < CONTAINER_SIZE) {
				if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_START + 36, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (stack.getItem() == JLCModItems.ENDER_DRAGON_HEART && this.container.getItem(FUEL_SLOT).isEmpty()) {
					if (!this.moveItemStackTo(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (this.container.getItem(INPUT_SLOT).isEmpty()) {
					if (!this.moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			if (stack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (stack.getCount() == result.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, stack);
		}
		return result;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}

	public Container getContainer() {
		return this.container;
	}

	public int getProgress() {
		return this.data.get(0);
	}

	public int getMaxProgress() {
		return this.data.get(1);
	}

	private static class OutputOnlySlot extends Slot {
		OutputOnlySlot(Container container, int index, int x, int y) {
			super(container, index, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}
	}

	private static class InputSlot extends Slot {
		InputSlot(Container container, int index, int x, int y) {
			super(container, index, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return stack.getItem() != JLCModBlocks.JLC_DUPER.asItem()
					&& stack.getItem() != JLCModItems.ENDER_DRAGON_HEART
					&& !stack.is(Items.DRAGON_EGG);
		}
	}

	private static class FuelSlot extends Slot {
		FuelSlot(Container container, int index, int x, int y) {
			super(container, index, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return stack.getItem() == JLCModItems.ENDER_DRAGON_HEART;
		}
	}
}