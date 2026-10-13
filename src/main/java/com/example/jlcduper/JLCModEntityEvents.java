package com.example.jlcduper;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.ItemStack;

public class JLCModEntityEvents {
	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof EnderDragon) {
				entity.drop(new ItemStack(JLCModItems.ENDER_DRAGON_HEART), false, false);
			}
		});
	}
}
