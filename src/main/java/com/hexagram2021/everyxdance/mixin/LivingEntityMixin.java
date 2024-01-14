package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
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

	@Inject(method = "hurt", at = @At(value = "RETURN"))
	private void everyxdance$stopDanceIfHurt(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> cir) {
		if(this instanceof IDanceableEntity danceableEntity && cir.getReturnValue()) {
			danceableEntity.everyxdance$stopDancing();
		}
	}
}
