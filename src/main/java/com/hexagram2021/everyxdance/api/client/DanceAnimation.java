package com.hexagram2021.everyxdance.api.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Similar with AnimationDefinition.
 *
 * @param lengthInSeconds
 * @param animations
 *
 * @see net.minecraft.client.animation.AnimationDefinition
 */
@OnlyIn(Dist.CLIENT)
public record DanceAnimation(float lengthInSeconds, Map<DancePart, List<DanceAnimationChannel>> animations) {
	@OnlyIn(Dist.CLIENT)
	public enum DancePart implements IExtensibleEnum {
		HEAD(IDanceableModel::everyxdance$getHead),
		BODY(IDanceableModel::everyxdance$getBody),
		NOSE(IDanceableModel::everyxdance$getNose),
		LEFT_ARM(IDanceableModel::everyxdance$getLeftArm),
		RIGHT_ARM(IDanceableModel::everyxdance$getRightArm),
		LEFT_LEG(IDanceableModel::everyxdance$getLeftLeg),
		RIGHT_LEG(IDanceableModel::everyxdance$getRightLeg);

		private final Function<IDanceableModel, AnimatedModelPart> partGetter;

		DancePart(Function<IDanceableModel, AnimatedModelPart> partGetter) {
			this.partGetter = partGetter;
		}

		public AnimatedModelPart getPart(IDanceableModel model) {
			return this.partGetter.apply(model);
		}

		@SuppressWarnings("unused")
		public static DancePart create(String name, Function<IDanceableModel, ModelPart> partGetter) {
			throw new IllegalStateException("Enum not extended");
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Builder {
		private final float length;
		private final Map<DancePart, List<DanceAnimationChannel>> animations = Maps.newHashMap();

		public static Builder withLength(float length) {
			return new Builder(length);
		}

		private Builder(float length) {
			this.length = length;
		}

		public Builder addAnimation(DancePart part, DanceAnimationChannel animationChannel) {
			this.animations.computeIfAbsent(part, part1 -> Lists.newArrayList()).add(animationChannel);
			return this;
		}

		public DanceAnimation build() {
			return new DanceAnimation(this.length, this.animations);
		}
	}
}
