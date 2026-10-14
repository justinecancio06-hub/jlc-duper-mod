package com.example.jlcduper.mixin;

import com.example.jlcduper.JLCModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
	private static final int MIN_OFFSET = 5;
	private static final int MAX_OFFSET = 15;

	@Inject(method = "tickDeath", at = @At("HEAD"))
	private void jlc_duper$dropHeartNearIslandCenter(CallbackInfo callback) {
		EnderDragon self = (EnderDragon) (Object) this;
		if (self.dragonDeathTime != 0) {
			return;
		}
		if (!(self.level() instanceof ServerLevel level)) {
			return;
		}

		RandomSource random = self.getRandom();
		int offsetX = (MIN_OFFSET + random.nextInt(MAX_OFFSET - MIN_OFFSET + 1)) * (random.nextBoolean() ? 1 : -1);
		int offsetZ = (MIN_OFFSET + random.nextInt(MAX_OFFSET - MIN_OFFSET + 1)) * (random.nextBoolean() ? 1 : -1);

		BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(offsetX, 0, offsetZ));
		Vec3 spawnPos = Vec3.atBottomCenterOf(surface);

		ItemEntity heart = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z,
				new ItemStack(JLCModItems.ENDER_DRAGON_HEART));
		level.addFreshEntity(heart);
	}
}
