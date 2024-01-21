package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity implements IDanceableEntity {
	@Unique
	private final AnimationState everyXDance$danceAnimationState = new AnimationState();

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@SuppressWarnings("WrongEntityDataParameterClass")
	@Inject(method = "<clinit>", at = @At(value = "TAIL"))
	private static void everyxdance$defineEntityDataAccessor(CallbackInfo ci) {
		Data.DATA_DANCE_TICK = SynchedEntityData.defineId(Mob.class, EntityDataSerializers.INT);
	}

	@Inject(method = "defineSynchedData", at = @At(value = "TAIL"))
	private void everyxdance$defineDanceData(CallbackInfo ci) {
		this.getEntityData().define(Data.DATA_DANCE_TICK, 0);
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
		LivingEntity target;
		if(current.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
			target = current.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(current.getTarget());
		} else {
			target = current.getTarget();
		}
		if(target != null && target.isDeadOrDying() && EveryXDanceCommonConfig.DANCEABLE_MOB_TYPES.get().contains(getRegistryName(current.getType()).toString()) &&
				RandomSource.create(current.level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY_ATTACK.get()) {
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
	@Override
	public AnimationState everyxdance$getAnimationState() {
		return this.everyXDance$danceAnimationState;
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if(entityDataAccessor.equals(Data.DATA_DANCE_TICK)) {
			if(this.everyxdance$isDancing()) {
				this.everyXDance$danceAnimationState.startIfStopped(this.tickCount);
			} else {
				this.everyXDance$danceAnimationState.stop();
			}
		}

		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public int everyxdance$getRemainingDanceTick() {
		return this.getEntityData().get(Data.DATA_DANCE_TICK);
	}
	@Unique
	private void everyxdance$setRemainingDanceTick(int ticks) {
		this.getEntityData().set(Data.DATA_DANCE_TICK, ticks);
	}
	@Unique
	private int everyxdance$getDanceTotalTicks() {
		//Different kind of mob?
		return EveryXDanceCommonConfig.MOB_DANCE_TOTAL_TICKS.get();
	}
}
