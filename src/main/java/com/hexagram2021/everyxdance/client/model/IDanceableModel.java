package com.hexagram2021.everyxdance.client.model;

import com.google.common.collect.Lists;
import com.hexagram2021.everyxdance.client.animation.DanceAnimation;
import com.hexagram2021.everyxdance.client.animation.EveryXDanceAnimations;
import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public interface IDanceableModel {
	ModelPart everyxdance$getHead();
	ModelPart everyxdance$getBody();
	@Nullable
	ModelPart everyxdance$getRightArm();
	@Nullable
	ModelPart everyxdance$getLeftArm();
	@Nullable
	ModelPart everyxdance$getRightLeg();
	@Nullable
	ModelPart everyxdance$getLeftLeg();
	@Nullable
	ModelPart everyxdance$getNose();

	void everyxdance$reset();

	/**
	 * Use this first to erase differences from humanoid models.
	 */
	void everyxdance$prepareDance(Preset.Preparation preparation);

	int everyxdance$getDanceIndex();

	List<Preset> PRESETS = Lists.newArrayList();

	static int getDancePresetIndex(RandomSource randomSource) {
		int index = EveryXDanceCommonConfig.MOB_DANCE_PRESET_INDEX.get();
		return index == -1 ? randomSource.nextInt(PRESETS.size()) : index;
	}

	static void performDance(IDanceableModel model, AnimationState animationState, float ageInTicks) {
		int presetIndex = model.everyxdance$getDanceIndex();
		if(presetIndex >= 0 && presetIndex < PRESETS.size()) {
			Preset preset = PRESETS.get(presetIndex);
			model.everyxdance$reset();
			model.everyxdance$prepareDance(preset.preparation());
			EveryXDanceAnimations.animate(model, animationState, preset.animation(), ageInTicks);
		}
	}

	record Preset(String name, Preparation preparation, DanceAnimation animation) implements Comparable<Preset> {
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
