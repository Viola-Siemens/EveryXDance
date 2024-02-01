package com.hexagram2021.everyxdance.client.model;

import net.minecraft.world.entity.Entity;

public interface IPrepareDanceModel {
	/**
	 * Use this first to erase differences from humanoid models.
	 *
	 * @param preparation	Type to prepare.
	 * @param entity		Entity to dance.
	 */
	void everyxdance$prepareDance(IDanceableModel.Preset.Preparation preparation, Entity entity);
}
