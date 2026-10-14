package com.example.jlcduper;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class JLCModItems {
	public static final Item ENDER_DRAGON_HEART = registerItem("ender_dragon_heart");

	public static void register() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
			output.accept(new ItemStack(ENDER_DRAGON_HEART), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
	}

	private static Item registerItem(String name) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, JLCDUPER.id(name));
		return Registry.register(BuiltInRegistries.ITEM, key,
				new Item(new Item.Properties().useItemDescriptionPrefix().durability(5).fireResistant().setId(key)));
	}
}
