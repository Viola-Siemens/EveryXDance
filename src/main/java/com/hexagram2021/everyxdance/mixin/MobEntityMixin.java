package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.nbt.CompoundTag;
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
	private void everyxdance$defineDanceData(CallbackInfo ci) {
		((Mob)(Object)this).getEntityData().define(Data.DATA_DANCE_TICK, 0);
	}

	@Unique
	private static final String REMAINING_TICKS_TAG = "everyxdance:remaining";
	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	private void everyxdance$addDanceData(CompoundTag nbt, CallbackInfo ci) {
		nbt.putInt(REMAINING_TICKS_TAG, this.everyxdance$getRemainingDanceTick());
	}
	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	private void everyxdance$readDanceData(CompoundTag nbt, CallbackInfo ci) {
		this.everyxdance$setRemainingDanceTick(nbt.getInt(REMAINING_TICKS_TAG));
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void everyxdance$tickDance(CallbackInfo ci) {
		int dancingTicks = this.everyxdance$getRemainingDanceTick();
		if(dancingTicks > 0) {
			this.everyxdance$setRemainingDanceTick(dancingTicks - 1);
		}
		Mob current = (Mob)(Object)this;
		LivingEntity target = current.getTarget();
		if(target != null && target.isDeadOrDying() &&
				RandomSource.create(current.level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY.get()) {
			this.everyxdance$startDancing();
		}
	}

	@Override
	public boolean everyxdance$isDancing() {
		return this.everyxdance$getRemainingDanceTick() > 0;
	}

	@Override
	public void everyxdance$startDancing() {
		this.everyxdance$setRemainingDanceTick(this.everyxdance$getDanceTotalTicks());
	}

	@Override
	public void everyxdance$stopDancing() {
		this.everyxdance$setRemainingDanceTick(0);
	}

	@Unique
	private int everyxdance$getRemainingDanceTick() {
		return ((Mob)(Object)this).getEntityData().get(Data.DATA_DANCE_TICK);
	}

	@Unique
	private void everyxdance$setRemainingDanceTick(int ticks) {
		((Mob)(Object)this).getEntityData().set(Data.DATA_DANCE_TICK, ticks);
	}

	@Unique
	private int everyxdance$getDanceTotalTicks() {
		//Different kind of mob?
		return EveryXDanceCommonConfig.MOB_DANCE_TOTAL_TICKS.get();
	}
}
