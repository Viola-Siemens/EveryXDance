package com.hexagram2021.everyxdance.common;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventHandler {
	@SubscribeEvent
	public static void onAnimalBreed(BabyEntitySpawnEvent event) {
		if(RandomSource.create(event.getParentA().level().getGameTime()).nextInt(100) < EveryXDanceCommonConfig.MOB_DANCE_POSSIBILITY.get()) {
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
}
