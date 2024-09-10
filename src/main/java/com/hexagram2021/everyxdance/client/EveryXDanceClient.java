package com.hexagram2021.everyxdance.client;

import com.hexagram2021.everyxdance.api.client.event.CollectDancePresetEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.util.EveryXDanceLogger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;
import static com.hexagram2021.everyxdance.client.animation.EveryXDanceAnimationPresets.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MODID)
public class EveryXDanceClient {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		CollectDancePresetEvent collectDancePresetEvent = new CollectDancePresetEvent();
		ModLoader.postEventWrapContainerInModOrder(collectDancePresetEvent);
		IDanceableModel.PRESETS.clear();
		IDanceableModel.PRESETS.addAll(collectDancePresetEvent.getPresets());
		IDanceableModel.PRESETS.sort(IDanceableModel.Preset::compareTo);
		printPresets();
	}
	@SubscribeEvent
	public static void onCollectDancePreset(CollectDancePresetEvent event) {
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "piglin_dance"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, PIGLIN_DANCE);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "subject3"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, SUBJECT3);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "gangnam_style"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, GANGNAM_STYLE);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "california_gurls"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, CALIFORNIA_GURLS);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "groove_battle"), IDanceableModel.Preset.Preparation.HUMANOID_SIT, GROOVE_BATTLE);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "chippy_chippy_chappa_chappa"), IDanceableModel.Preset.Preparation.HUMANOID_CRAWL, CHIPPY_CHIPPY_CHAPPA_CHAPPA);
		event.register(ResourceLocation.fromNamespaceAndPath(MODID, "toothless"), IDanceableModel.Preset.Preparation.HUMANOID_STAND, TOOTHLESS);
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
