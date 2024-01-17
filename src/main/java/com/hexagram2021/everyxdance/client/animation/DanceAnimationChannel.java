package com.hexagram2021.everyxdance.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

public record DanceAnimationChannel(AnimationChannel.Target target, Keyframes keyframes) {
	public record Keyframes(float lengthInSeconds, Keyframe... frames) {
	}

	@OnlyIn(Dist.CLIENT)
	public static class Interpolations {
		public static final AnimationChannel.Interpolation LINEAR = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, rate, cache).mul(speed);
		};
		public static final AnimationChannel.Interpolation CATMULL_ROM = (cache, rate, frames, left, right, speed) -> {
			Vector3f v0 = frames[(left + frames.length - 1) % frames.length].target();
			Vector3f v1 = frames[left].target();
			Vector3f v2 = frames[right].target();
			Vector3f v3 = frames[(right + 1) % frames.length].target();
			cache.set(Mth.catmullrom(rate, v0.x(), v1.x(), v2.x(), v3.x()) * speed, Mth.catmullrom(rate, v0.y(), v1.y(), v2.y(), v3.y()) * speed, Mth.catmullrom(rate, v0.z(), v1.z(), v2.z(), v3.z()) * speed);
			return cache;
		};
	}
}
