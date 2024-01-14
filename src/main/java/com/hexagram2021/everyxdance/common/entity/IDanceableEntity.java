package com.hexagram2021.everyxdance.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;

public interface IDanceableEntity {
	boolean everyxdance$isDancing();

	//Make sure be called in server side.
	void everyxdance$startDancing();
	//Make sure be called in server side.
	void everyxdance$stopDancing();


	final class Data {
		@SuppressWarnings("NotNullFieldNotInitialized")
		public static EntityDataAccessor<Integer> DATA_DANCE_TICK;
	}
}
