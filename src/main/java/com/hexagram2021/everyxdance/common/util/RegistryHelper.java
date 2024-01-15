package com.hexagram2021.everyxdance.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public interface RegistryHelper {
	static ResourceLocation getRegistryName(EntityType<?> entityType) {
		return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entityType));
	}
}
