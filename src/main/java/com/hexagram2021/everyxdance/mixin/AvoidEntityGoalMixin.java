package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@Mixin(AvoidEntityGoal.class)
public class AvoidEntityGoalMixin<T extends LivingEntity> {
	@Shadow @Final
	protected PathfinderMob mob;
	@Shadow @Nullable
	protected T toAvoid;

	@Inject(method = "canUse", at = @At(value = "HEAD"))
	private void everyxdance$celebrateIfTargetDead(CallbackInfoReturnable<Boolean> cir) {
		if(this.toAvoid != null && this.toAvoid.isDeadOrDying() && this.mob instanceof IDanceableEntity danceableEntity &&
				EveryXDanceCommonConfig.DANCEABLE_MOB_TYPES.get().contains(getRegistryName(this.mob.getType()).toString()) &&
				RandomSource.create(this.mob.level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY_ATTACK.get()) {
			danceableEntity.everyxdance$startDancing();
		}
	}
}
