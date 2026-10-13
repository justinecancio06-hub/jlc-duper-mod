package com.example.jlcduper;

import com.example.jlcduper.block.JLCDuperBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.references.BlockItemId;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class JLCModBlocks {
	private static final SoundType JLC_DUPER_SOUND = new SoundType(
			1.0F,
			1.0F,
			SoundEvents.STONE_BREAK,
			SoundEvents.EMPTY,
			SoundEvents.EMPTY,
			SoundEvents.EMPTY,
			SoundEvents.EMPTY);

	public static final Block JLC_DUPER = registerBlock(BlockBehaviour.Properties.of()
			.sound(JLC_DUPER_SOUND)
			.destroyTime(5.0f)
			.explosionResistance(6.0f)
			.requiresCorrectToolForDrops());

	public static void register() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> {
			output.accept(new ItemStack(JLC_DUPER), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
	}

	private static Block registerBlock(BlockBehaviour.Properties properties) {
		BlockItemId id = BlockItemId.create(JLCDUPER.id("jlcduper"), JLCDUPER.id("jlcduper"));
		ResourceKey<Block> blockKey = id.block();
		ResourceKey<Item> itemKey = id.item();
		properties.setId(blockKey);
		Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, new JLCDuperBlock(properties));
		Registry.register(BuiltInRegistries.ITEM, itemKey,
				new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
		return block;
	}
}