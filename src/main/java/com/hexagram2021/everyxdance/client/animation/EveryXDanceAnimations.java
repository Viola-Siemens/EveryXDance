package com.hexagram2021.everyxdance.client.animation;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class EveryXDanceAnimations {
	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	public static final DanceAnimation PIGLIN_DANCE = DanceAnimation.Builder.withLength(2.0F)
			.addAnimation(DanceAnimation.DancePart.HEAD, position(
					2.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.5F, new Vector3f(1.0F, 0.0F, 0.0F)),
					keySoft(1.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(1.5F, new Vector3f(-1.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.HEAD, position(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 1.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, new Vector3f(0.0F, -1.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.HEAD, rotation(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, new Vector3f(-15.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, new Vector3f(15.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.BODY, position(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.35F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, new Vector3f(0.0F, -0.35F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.BODY, rotation(
					1.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 3.0F)),
					keySoft(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, new Vector3f(0.0F, 0.0F, -3.0F))
			))
			.addAnimation(DanceAnimation.DancePart.NOSE, rotation(
					1.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 10.0F)),
					keySoft(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, new Vector3f(0.0F, 0.0F, -10.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_ARM, position(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.5F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, new Vector3f(0.0F, -0.5F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_ARM, rotation(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 80.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.0F, 70.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 60.0F)),
					keySoft(0.375F, new Vector3f(0.0F, 0.0F, 70.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_ARM, position(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.5F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, new Vector3f(0.0F, -0.5F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_ARM, rotation(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, -80.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.0F, -70.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, -60.0F)),
					keySoft(0.375F, new Vector3f(0.0F, 0.0F, -70.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_LEG, rotation(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 5.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, -5.0F)),
					keySoft(0.375F, new Vector3f(0.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_LEG, rotation(
					0.5F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 5.0F)),
					keySoft(0.125F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, 0.0F, -5.0F)),
					keySoft(0.375F, new Vector3f(0.0F, 0.0F, 0.0F))
			))
			.build();


	@SuppressWarnings("SameParameterValue")
	private static void animate(IDanceableModel model, DanceAnimation danceAnimation, long accumulatedTime, float speed, Vector3f cache) {
		float timeStamp = getElapsedSeconds(danceAnimation, accumulatedTime);

		for(Map.Entry<DanceAnimation.DancePart, List<DanceAnimationChannel>> entry : danceAnimation.animations().entrySet()) {
			for(ModelPart modelPart: entry.getKey().getPart(model).modelParts()) {
				List<DanceAnimationChannel> list = entry.getValue();
				list.forEach(animationChannel -> {
					DanceAnimationChannel.Keyframes keyframes = animationChannel.keyframes();
					float currentTimeStamp = timeStamp % keyframes.lengthInSeconds();
					Keyframe[] frames = keyframes.frames();
					int i = Math.max(0, Mth.binarySearch(0, frames.length, ind -> currentTimeStamp <= frames[ind].timestamp()) - 1);
					Keyframe currentFrame = frames[i];
					float nextTimeStamp;
					int j;
					if(i + 1 >= frames.length) {
						nextTimeStamp = keyframes.lengthInSeconds();
						j = 0;
					} else {
						nextTimeStamp = 0.0F;
						j = i + 1;
					}
					Keyframe nextFrame = frames[j];
					float diff = currentTimeStamp - currentFrame.timestamp();

					nextFrame.interpolation().apply(
							cache,
							Mth.clamp(diff / (nextFrame.timestamp() - currentFrame.timestamp() + nextTimeStamp), 0.0F, 1.0F),
							frames, i, j, speed
					);
					animationChannel.target().apply(modelPart, cache);
				});
			}
		}
	}

	private static float getElapsedSeconds(DanceAnimation danceAnimation, long accumulatedTime) {
		return ((float)accumulatedTime / 1000.0F) % danceAnimation.lengthInSeconds();
	}

	private EveryXDanceAnimations() {
	}

	public static void animate(IDanceableModel danceableModel, AnimationState animationState, DanceAnimation danceAnimation, float timestamp) {
		animate(danceableModel, animationState, danceAnimation, timestamp, 1.0F);
	}
	@SuppressWarnings("SameParameterValue")
	private static void animate(IDanceableModel danceableModel, AnimationState animationState, DanceAnimation danceAnimation, float timestamp, float multiplier) {
		animationState.updateTime(timestamp, multiplier);
		animationState.ifStarted(animationState1 -> animate(danceableModel, danceAnimation, animationState1.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
	}

	static KeyframeHolder keyHard(float timestamp, Vector3f target) {
		return new KeyframeHolder(timestamp, target, DanceAnimationChannel.Interpolations.LINEAR);
	}
	static KeyframeHolder keySoft(float timestamp, Vector3f target) {
		return new KeyframeHolder(timestamp, target, DanceAnimationChannel.Interpolations.CATMULL_ROM);
	}
	static DanceAnimationChannel position(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(Targets.POSITION, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(EveryXDanceAnimations::posVec)).toArray(Keyframe[]::new)
		));
	}
	static DanceAnimationChannel rotation(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(Targets.ROTATION, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(EveryXDanceAnimations::degreeVec)).toArray(Keyframe[]::new)
		));
	}
	static DanceAnimationChannel scale(float lengthInSeconds, KeyframeHolder... keyframes) {
		return new DanceAnimationChannel(Targets.SCALE, new DanceAnimationChannel.Keyframes(
				lengthInSeconds, Arrays.stream(keyframes).map(k -> k.build(EveryXDanceAnimations::scaleVec)).toArray(Keyframe[]::new)
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

	interface VecTransformer {
		Vector3f transform(float x, float y, float z);
	}

	record KeyframeHolder(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
		public Keyframe build(VecTransformer transformer) {
			return new Keyframe(this.timestamp, transformer.transform(this.target.x, this.target.y, this.target.z), this.interpolation);
		}
	}
}
