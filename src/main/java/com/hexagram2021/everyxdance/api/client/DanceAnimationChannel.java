package com.hexagram2021.everyxdance.api.client;

import com.hexagram2021.everyxdance.api.EveryXDanceMath;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Arrays;

/**
 * Similar with AnimationChannel. It is used to animate a single part of model. But you do not need to know how it works.
 * <br/>
 * Use EveryXDanceAnimationPresets.position/rotation/scale to construct.
 *
 * @see com.hexagram2021.everyxdance.api.client.DanceAnimationChannel#position(float, com.hexagram2021.everyxdance.api.client.DanceAnimationChannel.KeyframeHolder...)
 * @see com.hexagram2021.everyxdance.api.client.DanceAnimationChannel#rotation(float, com.hexagram2021.everyxdance.api.client.DanceAnimationChannel.KeyframeHolder...)
 * @see com.hexagram2021.everyxdance.api.client.DanceAnimationChannel#scale(float, com.hexagram2021.everyxdance.api.client.DanceAnimationChannel.KeyframeHolder...)
 *
 * @param target		Position, rotation or scale to be animated.
 * @param keyframes		Keyframes for the target of a single part of model.
 *
 * @see net.minecraft.client.animation.AnimationChannel
 */
@SuppressWarnings("unused")
public record DanceAnimationChannel(AnimationChannel.Target target, Keyframes keyframes) {
	public record Keyframes(float lengthInSeconds, Keyframe... frames) {
	}

	@OnlyIn(Dist.CLIENT)
	public static class Interpolations {
		/**
		 * Each frame is dispersed, and no transition between them.<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x	<br/>
		 * <br/>
		 * x&nbsp;&nbsp;x&nbsp;&nbsp;x&nbsp;&nbsp;x
		 */
		public static final AnimationChannel.Interpolation DISPERSED = (cache, rate, frames, left, right, speed) -> {
			cache.set(frames[left].target());
			return cache.mul(speed);
		};
		/**
		 * Linear transition between two frames. It may look hard and sharp.<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x	<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x							<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x													<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;x																			<br/>
		 * x
		 */
		public static final AnimationChannel.Interpolation LINEAR = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, rate, cache).mul(speed);
		};
		/**
		 * Square transition between two frames. It's fast at first and gets slower and slower then. For example, leaping to hanging in the air phase of a jump.<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x	<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x														<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x																						<br/>
		 * &nbsp;&nbsp;x																										<br/>
		 * x
		 */
		public static final AnimationChannel.Interpolation FAST_TO_SLOW_SQUARE = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, (2 - rate) * rate, cache).mul(speed);
		};
		/**
		 * Square transition between two frames. It's slow at first and gets faster and faster then. For example, hanging in the air to landing phase of a jump.<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x	<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x				<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x								<br/>
		 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x																<br/>
		 * x
		 */
		public static final AnimationChannel.Interpolation SLOW_TO_FAST_SQUARE = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, rate * rate, cache).mul(speed);
		};
		/**
		 * Similar with FAST_TO_SLOW_SQUARE. But it is cubic, so it's faster at first, and gets much slower then.
		 */
		public static final AnimationChannel.Interpolation FAST_TO_SLOW_CUBIC = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, (3 + (rate - 3) * rate) * rate, cache).mul(speed);
		};
		/**
		 * Similar with SLOW_TO_FAST_SQUARE. But it is cubic, so it's slower at first, and gets much faster then.
		 */
		public static final AnimationChannel.Interpolation SLOW_TO_FAST_CUBIC = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, rate * rate * rate, cache).mul(speed);
		};
		/**
		 * A sharp smooth function, similar to the dispersed one.
		 */
		public static final AnimationChannel.Interpolation SMOOTH_DISPERSED_6 = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, (float)EveryXDanceMath.factSmooth(rate, 6.0D, 0.5D), cache).mul(speed);
		};
		/**
		 * A sharp smooth function (not as sharp as the above one).
		 */
		public static final AnimationChannel.Interpolation SMOOTH_DISPERSED_3 = (cache, rate, frames, left, right, speed) -> {
			Vector3f from = frames[left].target();
			Vector3f to = frames[right].target();
			return from.lerp(to, (float)EveryXDanceMath.factSmooth(rate, 3.0D, 0.5D), cache).mul(speed);
		};
		/**
		 * Catmull-rom spline. This is a cubic spline and makes the entire animation smooth and soft.
		 */
		public static final AnimationChannel.Interpolation CATMULL_ROM = (cache, rate, frames, left, right, speed) -> {
			Vector3f v0 = frames[(left + frames.length - 1) % frames.length].target();
			Vector3f v1 = frames[left].target();
			Vector3f v2 = frames[right].target();
			Vector3f v3 = frames[(right + 1) % frames.length].target();
			cache.set(Mth.catmullrom(rate, v0.x(), v1.x(), v2.x(), v3.x()) * speed, Mth.catmullrom(rate, v0.y(), v1.y(), v2.y(), v3.y()) * speed, Mth.catmullrom(rate, v0.z(), v1.z(), v2.z(), v3.z()) * speed);
			return cache;
		};
	}
	public static DanceAnimationChannel position(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(AnimationChannel.Targets.POSITION, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(DanceAnimationChannel::posVec)).toArray(Keyframe[]::new)
		));
	}
	public static DanceAnimationChannel rotation(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(AnimationChannel.Targets.ROTATION, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(DanceAnimationChannel::degreeVec)).toArray(Keyframe[]::new)
		));
	}
	public static DanceAnimationChannel scale(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(AnimationChannel.Targets.SCALE, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(DanceAnimationChannel::scaleVec)).toArray(Keyframe[]::new)
		));
	}

	static Vector3f posVec(float x, float y, float z) {
		return new Vector3f(x, -y, z);
	}
	static Vector3f degreeVec(float xRot, float yRot, float zRot) {
		return new Vector3f(xRot * Mth.DEG_TO_RAD, yRot * Mth.DEG_TO_RAD, zRot * Mth.DEG_TO_RAD);
	}
	static Vector3f scaleVec(float xScale, float yScale, float zScale) {
		return new Vector3f(xScale - 1.0F, yScale - 1.0F, zScale - 1.0F);
	}

	public interface VecTransformer {
		Vector3f transform(float x, float y, float z);
	}

	/**
	 * Sealed Keyframe.
	 *
	 * @param timestamp		Timestamp in seconds of a keyframe.
	 * @param target		Animation target. Can only be position offset, rotation offset in degrees (not radian!), scale offset. The transformation will be done automatically.
	 * @param interpolation	Animation transition.
	 *
	 * @see com.hexagram2021.everyxdance.api.client.DanceAnimationChannel.Interpolations
	 */
	public record KeyframeHolder(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
		public Keyframe build(VecTransformer transformer) {
			return new Keyframe(this.timestamp, transformer.transform(this.target.x, this.target.y, this.target.z), this.interpolation);
		}
	}
}
