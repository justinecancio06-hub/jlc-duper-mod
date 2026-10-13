package com.example.jlcduper;

import com.example.jlcduper.block.JLCDuperBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class JLCModBlockEntities {
	public static final BlockEntityType<JLCDuperBlockEntity> JLC_DUPER = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			JLCDUPER.id("jlcduper"),
			FabricBlockEntityTypeBuilder.create(JLCDuperBlockEntity::new, JLCModBlocks.JLC_DUPER).build());

	public static void register() {
	}
}