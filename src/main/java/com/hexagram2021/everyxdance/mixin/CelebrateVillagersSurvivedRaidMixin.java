package com.hexagram2021.everyxdance.mixin;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@Mixin(CelebrateVillagersSurvivedRaid.class)
public class CelebrateVillagersSurvivedRaidMixin {
	@Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", at = @At(value = "RETURN"))
	private void everyxdance$tick(ServerLevel serverLevel, Villager villager, long time, CallbackInfo ci) {
		if(villager instanceof IDanceableEntity danceableEntity &&
				EveryXDanceCommonConfig.DANCEABLE_MOB_TYPES.get().contains(getRegistryName(villager.getType()).toString()) &&
				!danceableEntity.everyxdance$isDancing()) {
			danceableEntity.everyxdance$startDancing();
		}
	}
}
