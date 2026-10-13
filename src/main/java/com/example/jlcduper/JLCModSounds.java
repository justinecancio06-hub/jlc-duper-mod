package com.example.jlcduper;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class JLCModSounds {
	public static final SoundEvent JLC_DUPER_PLACE = register("jlc_duper_place");

	public static void register() {
	}

	private static SoundEvent register(String name) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, JLCDUPER.id(name),
				SoundEvent.createVariableRangeEvent(JLCDUPER.id(name)));
	}
}
