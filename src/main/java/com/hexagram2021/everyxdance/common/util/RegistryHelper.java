package com.hexagram2021.everyxdance.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Objects;

public interface RegistryHelper {
	static ResourceLocation getRegistryName(EntityType<?> entityType) {
		return Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
	}
}
