package com.example.jlcduper.client;

import com.example.jlcduper.JLCModScreenHandlers;
import com.example.jlcduper.client.screen.JLCDuperScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class JLCDUPERClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(JLCModScreenHandlers.JLC_DUPER_SCREEN_HANDLER, JLCDuperScreen::new);
	}
}