package com.hexagram2021.everyxdance.client.animation;

import com.google.common.collect.Maps;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

		private final Function<IDanceableModel, ModelPart> partGetter;

		DancePart(Function<IDanceableModel, ModelPart> partGetter) {
			this.partGetter = partGetter;
		}

		@Nullable
		public ModelPart getPart(IDanceableModel model) {
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
