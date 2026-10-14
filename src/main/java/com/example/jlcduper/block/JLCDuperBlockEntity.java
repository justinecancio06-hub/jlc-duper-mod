package com.example.jlcduper.block;

import com.example.jlcduper.ImplementedContainer;
import com.example.jlcduper.JLCModBlockEntities;
import com.example.jlcduper.JLCModBlocks;
import com.example.jlcduper.JLCModItems;
import com.example.jlcduper.screen.JLCDuperScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class JLCDuperBlockEntity extends BlockEntity implements MenuProvider, ImplementedContainer {
	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	private static final int CONTAINER_SIZE = 3;
	public static final int MAX_PROGRESS = 600;

	private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
	private int progress;

	private final ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> progress;
				case 1 -> MAX_PROGRESS;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			if (index == 0) {
				progress = value;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public JLCDuperBlockEntity(BlockPos pos, BlockState state) {
		super(JLCModBlockEntities.JLC_DUPER, pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, JLCDuperBlockEntity blockEntity) {
		if (level.isClientSide()) {
			return;
		}

		boolean changed = false;
		ItemStack input = blockEntity.items.get(INPUT_SLOT);
		ItemStack fuel = blockEntity.items.get(FUEL_SLOT);
		ItemStack output = blockEntity.items.get(OUTPUT_SLOT);

		boolean isForbiddenInput = input.getItem() == JLCModBlocks.JLC_DUPER.asItem()
				|| input.getItem() == JLCModItems.ENDER_DRAGON_HEART
				|| input.is(Items.DRAGON_EGG);
		boolean canProgress = !input.isEmpty()
				&& !isForbiddenInput
				&& fuel.getItem() == JLCModItems.ENDER_DRAGON_HEART
				&& canAcceptOutput(input, output);

		if (canProgress) {
			blockEntity.progress++;
			if (blockEntity.progress >= MAX_PROGRESS) {
				blockEntity.progress = 0;
				if (output.isEmpty()) {
					blockEntity.items.set(OUTPUT_SLOT, input.copyWithCount(2));
				} else {
					output.grow(2);
				}
				input.shrink(1);
				if (input.isEmpty()) {
					blockEntity.items.set(INPUT_SLOT, ItemStack.EMPTY);
				}
				if (fuel.isDamageableItem() && !fuel.nextDamageWillBreak()) {
					fuel.setDamageValue(fuel.getDamageValue() + 1);
				} else {
					blockEntity.items.set(FUEL_SLOT, ItemStack.EMPTY);
				}
			}
			setLit(level, pos, true);
			changed = true;
		} else {
			if (blockEntity.progress > 0) {
				blockEntity.progress = 0;
				changed = true;
			}
			setLit(level, pos, false);
		}

		if (changed) {
			blockEntity.setChanged();
		}
	}

	private static void setLit(Level level, BlockPos pos, boolean lit) {
		BlockState state = level.getBlockState(pos);
		if (state.getValue(JLCDuperBlock.LIT) != lit) {
			level.setBlockAndUpdate(pos, state.setValue(JLCDuperBlock.LIT, lit));
		}
	}

	private static boolean canAcceptOutput(ItemStack input, ItemStack output) {
		if (output.isEmpty()) {
			return true;
		}
		return output.is(input.getItem()) && output.getCount() + 2 <= output.getMaxStackSize();
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		ContainerHelper.saveAllItems(output, this.items);
		output.putInt("JlcDuperProgress", this.progress);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		ContainerHelper.loadAllItems(input, this.items);
		this.progress = input.getIntOr("JlcDuperProgress", 0);
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return this.saveWithoutMetadata(registries);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.jlc_duper.jlcduper");
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
		return new JLCDuperScreenHandler(syncId, playerInventory, this, this.data);
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		return this.items;
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}
}