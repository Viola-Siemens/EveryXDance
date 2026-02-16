package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(value = "RETURN"), cancellable = true)
	private void everyxdance$ignoreIfDancing(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
		if(this instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			cir.setReturnValue(false);
		}
	}

	@ModifyReturnValue(method = "hurt", at = @At(value = "RETURN"))
	private boolean everyxdance$stopDanceIfHurt(boolean original,
												 @Local(argsOnly = true) DamageSource damageSource,
												 @Local(argsOnly = true) float damage) {
		if(this instanceof IDanceableEntity danceableEntity && original) {
			danceableEntity.everyxdance$stopDancing();
		}
		return original;
	}
}
