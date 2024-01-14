package com.hexagram2021.everyxdance.client.model;

import com.google.common.collect.Lists;
import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

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

	List<Preset> PRESETS = Lists.newArrayList();

	static int getDancePresetIndex(RandomSource randomSource) {
		int index = EveryXDanceCommonConfig.MOB_DANCE_PRESET_INDEX.get();
		return index == -1 ? randomSource.nextInt(PRESETS.size()) : index;
	}

	static void performDance(int presetIndex, IDanceableModel model, float ageInTicks) {
		if(presetIndex >= 0 && presetIndex < PRESETS.size()) {
			PRESETS.get(presetIndex).animation().accept(model, ageInTicks);
		}
	}

	static void reset(IDanceableModel model) {
		model.everyxdance$getHead().x = 0.0F;
		model.everyxdance$getHead().y = 0.0F;
		model.everyxdance$getHead().z = 0.0F;
		model.everyxdance$getHead().xRot = 0.0F;
		model.everyxdance$getHead().yRot = 0.0F;
		model.everyxdance$getHead().zRot = 0.0F;
		model.everyxdance$getBody().x = 0.0F;
		model.everyxdance$getBody().y = 0.0F;
		model.everyxdance$getBody().z = 0.0F;
		model.everyxdance$getBody().xRot = 0.0F;
		model.everyxdance$getBody().yRot = 0.0F;
		model.everyxdance$getBody().zRot = 0.0F;
		ModelPart rightArm = model.everyxdance$getRightArm();
		if(rightArm != null) {
			rightArm.x = 0.0F;
			rightArm.y = 0.0F;
			rightArm.z = 0.0F;
			rightArm.xRot = 0.0F;
			rightArm.yRot = 0.0F;
			rightArm.zRot = 0.0F;
		}
		ModelPart leftArm = model.everyxdance$getLeftArm();
		if(leftArm != null) {
			leftArm.x = 0.0F;
			leftArm.y = 0.0F;
			leftArm.z = 0.0F;
			leftArm.xRot = 0.0F;
			leftArm.yRot = 0.0F;
			leftArm.zRot = 0.0F;
		}
		ModelPart rightLeg = model.everyxdance$getRightLeg();
		if(rightLeg != null) {
			rightLeg.x = 0.0F;
			rightLeg.y = 0.0F;
			rightLeg.z = 0.0F;
			rightLeg.xRot = 0.0F;
			rightLeg.yRot = 0.0F;
			rightLeg.zRot = 0.0F;
		}
		ModelPart leftLeg = model.everyxdance$getLeftLeg();
		if(leftLeg != null) {
			leftLeg.x = 0.0F;
			leftLeg.y = 0.0F;
			leftLeg.z = 0.0F;
			leftLeg.xRot = 0.0F;
			leftLeg.yRot = 0.0F;
			leftLeg.zRot = 0.0F;
		}
	}

	record Preset(String name, BiConsumer<IDanceableModel, Float> animation) implements Comparable<Preset> {
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
