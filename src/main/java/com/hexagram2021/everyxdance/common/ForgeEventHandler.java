package com.hexagram2021.everyxdance.common;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;
import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

	@SubscribeEvent
	public static void onMobChangeTarget(LivingChangeTargetEvent event) {
		if(event.getEntity() instanceof IDanceableEntity danceableEntity && event.getOriginalTarget() != null) {
			danceableEntity.everyxdance$stopDancing();
		}
	}
}
