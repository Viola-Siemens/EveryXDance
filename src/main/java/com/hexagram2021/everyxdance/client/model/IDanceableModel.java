package com.hexagram2021.everyxdance.client.model;

import com.google.common.collect.Lists;
import com.hexagram2021.everyxdance.api.client.DanceAnimation;
import com.hexagram2021.everyxdance.client.EveryXDanceClient;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.animation.EveryXDanceAnimations;
import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import com.hexagram2021.everyxdance.common.util.EveryXDanceLogger;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public interface IDanceableModel extends IPrepareDanceModel {
	AnimatedModelPart everyxdance$getHead();
	AnimatedModelPart everyxdance$getBody();
	AnimatedModelPart everyxdance$getRightArm();
	AnimatedModelPart everyxdance$getLeftArm();
	AnimatedModelPart everyxdance$getRightLeg();
	AnimatedModelPart everyxdance$getLeftLeg();
	AnimatedModelPart everyxdance$getNose();

	void everyxdance$reset();

	List<Preset> PRESETS = Lists.newArrayList();

	static int getDancePresetIndex(RandomSource randomSource) {
		if(!Preset.removeDisabled) {
			int originLength = PRESETS.size();
			List<? extends String> disabledPresetNames = EveryXDanceCommonConfig.DISABLED_DANCE_PRESETS.get();
			PRESETS.removeIf(p -> disabledPresetNames.stream().anyMatch(s -> s.equals(p.name())));
			Preset.removeDisabled = true;
			EveryXDanceLogger.debug("Removed %d disabled animations.".formatted(originLength - IDanceableModel.PRESETS.size()));
			EveryXDanceClient.printPresets();
		}
		return randomSource.nextInt(PRESETS.size());
	}

	static void performDance(IDanceableModel model, boolean isBaby, IDanceableEntity danceableEntity, float ageInTicks) {
		int presetIndex = danceableEntity.everyxdance$getDanceIndex();
		if(presetIndex >= 0 && presetIndex < PRESETS.size()) {
			Preset preset = PRESETS.get(presetIndex);
			model.everyxdance$reset();
			model.everyxdance$prepareDance(preset.preparation(), isBaby);
			EveryXDanceAnimations.animate(model, danceableEntity.everyxdance$getAnimationState(), preset.animation(), ageInTicks);
		}
	}

	record Preset(String name, Preparation preparation, DanceAnimation animation) implements Comparable<Preset> {
		private static boolean removeDisabled = false;

		public enum Preparation implements IExtensibleEnum {
			HUMANOID_STAND,
			HUMANOID_SIT,
			HUMANOID_CRAWL;

			@SuppressWarnings("unused")
			public static Preparation create(String name) {
				throw new IllegalStateException("Enum not extended");
			}
		}

		@Override
		public int compareTo(IDanceableModel.Preset o) {
			return this.name.compareTo(o.name);
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof IDanceableModel.Preset preset) {
				return this.name.equals(preset.name);
			}
			return false;
		}
		@Override
		public int hashCode() {
			return this.name.hashCode();
		}
	}
}
