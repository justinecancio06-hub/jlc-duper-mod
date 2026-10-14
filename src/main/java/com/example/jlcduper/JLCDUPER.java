package com.example.jlcduper;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JLCDUPER implements ModInitializer {
	public static final String MOD_ID = "jlc_duper";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		JLCModItems.register();
		JLCModBlocks.register();
		JLCModBlockEntities.register();
		JLCModScreenHandlers.register();
		JLCModSounds.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}