package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobEntityMixin implements IDanceableEntity {
	@Inject(method = "<clinit>", at = @At(value = "TAIL"))
	private static void everyxdance$defineEntityDataAccessor(CallbackInfo ci) {
		Data.DATA_DANCE_TICK = SynchedEntityData.defineId(Mob.class, EntityDataSerializers.INT);
	}

	@Inject(method = "defineSynchedData", at = @At(value = "TAIL"))
	public void everyxdance$defineDanceData(CallbackInfo ci) {
		((Mob)(Object)this).getEntityData().define(Data.DATA_DANCE_TICK, 0);
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void everyxdance$tickDance(CallbackInfo ci) {
		Mob current = (Mob)(Object)this;
		SynchedEntityData entityData = current.getEntityData();
		int dancingTicks = entityData.get(Data.DATA_DANCE_TICK);
		if(dancingTicks > 0) {
			entityData.set(Data.DATA_DANCE_TICK, dancingTicks - 1);
		}
		LivingEntity target = current.getTarget();
		if(target != null && target.isDeadOrDying() &&
				RandomSource.create(current.level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY.get()) {
			this.everyxdance$startDancing();
		}
	}

	@Override
	public boolean everyxdance$isDancing() {
		return ((Mob)(Object)this).getEntityData().get(Data.DATA_DANCE_TICK) > 0;
	}

	@Override
	public void everyxdance$startDancing() {
		((Mob)(Object)this).getEntityData().set(Data.DATA_DANCE_TICK, this.everyxdance$getDanceTotalTicks());
	}

	@Override
	public void everyxdance$stopDancing() {
		((Mob)(Object)this).getEntityData().set(Data.DATA_DANCE_TICK, 0);
	}

	@Unique
	private int everyxdance$getDanceTotalTicks() {
		//Different kind of mob?
		return EveryXDanceCommonConfig.MOB_DANCE_TOTAL_TICKS.get();
	}
}
