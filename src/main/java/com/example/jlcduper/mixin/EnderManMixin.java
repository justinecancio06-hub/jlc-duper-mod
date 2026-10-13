package com.example.jlcduper.mixin;

import com.example.jlcduper.JLCModItems;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {
	@Inject(method = "isBeingStaredBy", at = @At("HEAD"), cancellable = true)
	private void jlc_duper$ignoreStareWhileHoldingHeart(Player player, CallbackInfoReturnable<Boolean> callback) {
		if (player.getMainHandItem().is(JLCModItems.ENDER_DRAGON_HEART)
				|| player.getOffhandItem().is(JLCModItems.ENDER_DRAGON_HEART)) {
			callback.setReturnValue(false);
		}
	}
}
