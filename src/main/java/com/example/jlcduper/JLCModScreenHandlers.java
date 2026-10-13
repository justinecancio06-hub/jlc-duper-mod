package com.example.jlcduper;

import com.example.jlcduper.screen.JLCDuperScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class JLCModScreenHandlers {
	public static final MenuType<JLCDuperScreenHandler> JLC_DUPER_SCREEN_HANDLER = Registry.register(
			BuiltInRegistries.MENU,
			JLCDUPER.id("jlcduper"),
			new MenuType<>(JLCDuperScreenHandler::new, FeatureFlagSet.of()));

	public static void register() {
	}
}