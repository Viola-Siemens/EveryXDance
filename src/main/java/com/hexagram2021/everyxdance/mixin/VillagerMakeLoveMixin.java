package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@Mixin(VillagerMakeLove.class)
public class VillagerMakeLoveMixin {
	@ModifyReturnValue(method = "breed", at = @At(value = "RETURN", ordinal = 1))
	private Optional<Villager> everyxdance$villagerDanceAfterBreed(Optional<Villager> original,
																   @Local(argsOnly = true) ServerLevel serverLevel,
																   @Local(argsOnly = true, ordinal = 0) Villager parentA,
																   @Local(argsOnly = true, ordinal = 1) Villager parentB) {
		original.ifPresent(villager -> {
			if(RandomSource.create(serverLevel.getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY_BREED.get() &&
					EveryXDanceCommonConfig.DANCEABLE_MOB_TYPES.get().contains(getRegistryName(villager.getType()).toString())) {
				if (parentA instanceof IDanceableEntity danceableEntity) {
					danceableEntity.everyxdance$startDancing();
				}
				if (parentB instanceof IDanceableEntity danceableEntity) {
					danceableEntity.everyxdance$startDancing();
				}
				if (villager instanceof IDanceableEntity danceableEntity) {
					danceableEntity.everyxdance$startDancing();
				}
			}
		});
		return original;
	}
}
