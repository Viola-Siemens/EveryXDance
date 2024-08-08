package com.hexagram2021.everyxdance.common;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;
import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public final class ForgeEventHandler {
	@SubscribeEvent
	public static void onAnimalBreed(BabyEntitySpawnEvent event) {
		if(RandomSource.create(event.getParentA().level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY_BREED.get() &&
				EveryXDanceCommonConfig.DANCEABLE_MOB_TYPES.get().contains(getRegistryName(event.getParentA().getType()).toString())) {
			if(event.getParentA() instanceof IDanceableEntity danceableEntity) {
				danceableEntity.everyxdance$startDancing();
			}
			if(event.getParentB() instanceof IDanceableEntity danceableEntity) {
				danceableEntity.everyxdance$startDancing();
			}
			if(event.getChild() instanceof IDanceableEntity danceableEntity) {
				danceableEntity.everyxdance$startDancing();
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onMobChangeTarget(LivingChangeTargetEvent event) {
		if(event.getEntity() instanceof IDanceableEntity danceableEntity && event.getNewAboutToBeSetTarget() != null) {
			danceableEntity.everyxdance$stopDancing();
		}
	}
}
