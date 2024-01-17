package com.hexagram2021.everyxdance.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.AnimationState;

public interface IDanceableEntity {
	boolean everyxdance$isDancing();

	//Make sure be called in server side.
	void everyxdance$startDancing();
	//Make sure be called in server side.
	void everyxdance$stopDancing();

	AnimationState everyxdance$getAnimationState();


	final class Data {
		@SuppressWarnings("NotNullFieldNotInitialized")
		public static EntityDataAccessor<Integer> DATA_DANCE_TICK;
	}
}
