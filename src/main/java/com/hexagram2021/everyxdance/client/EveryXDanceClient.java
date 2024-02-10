package com.hexagram2021.everyxdance.client;

import com.hexagram2021.everyxdance.api.client.event.CollectDancePresetEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.util.EveryXDanceLogger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;
import static com.hexagram2021.everyxdance.client.animation.EveryXDanceAnimationPresets.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class EveryXDanceClient {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		CollectDancePresetEvent collectDancePresetEvent = new CollectDancePresetEvent();
		ModLoader.get().postEventWrapContainerInModOrder(collectDancePresetEvent);
		IDanceableModel.PRESETS.clear();
		IDanceableModel.PRESETS.addAll(collectDancePresetEvent.getPresets());
		IDanceableModel.PRESETS.sort(IDanceableModel.Preset::compareTo);
		printPresets();
	}
	@SubscribeEvent
	public static void onCollectDancePreset(CollectDancePresetEvent event) {
		event.register(new ResourceLocation(MODID, "piglin_dance"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, PIGLIN_DANCE);
		event.register(new ResourceLocation(MODID, "subject3"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, SUBJECT3);
		event.register(new ResourceLocation(MODID, "gangnam_style"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, GANGNAM_STYLE);
		event.register(new ResourceLocation(MODID, "california_gurls"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, CALIFORNIA_GURLS);
		event.register(new ResourceLocation(MODID, "groove_battle"), IDanceableModel.Preset.Preparation.HUMANOID_SIT, GROOVE_BATTLE);
	}

	public static void printPresets() {
		EveryXDanceLogger.info("Dancing Animations (size %d):".formatted(IDanceableModel.PRESETS.size()));
		for(int i = 0; i < IDanceableModel.PRESETS.size(); ++i) {
			EveryXDanceLogger.info("(%d) - %s".formatted(i, IDanceableModel.PRESETS.get(i).name()));
		}
	}

	public static int getRandomDanceIndex(RandomSource randomSource) {
		return IDanceableModel.getDancePresetIndex(randomSource);
	}
}
