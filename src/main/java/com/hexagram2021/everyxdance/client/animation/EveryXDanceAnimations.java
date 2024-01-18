package com.hexagram2021.everyxdance.client.animation;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class EveryXDanceAnimations {
	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	public static final DanceAnimation PIGLIN_DANCE = DanceAnimation.Builder.withLength(2.0F)
			.addAnimation(DanceAnimation.DancePart.HEAD, position(
					2.0F,
					keySoft(0.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.5F, posVec(1.0F, 0.0F, 0.0F)),
					keySoft(1.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(1.5F, posVec(-1.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.HEAD, position(
					0.5F,
					keySoft(0.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, posVec(0.0F, 1.0F, 0.0F)),
					keySoft(0.25F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, posVec(0.0F, -1.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.BODY, position(
					0.5F,
					keySoft(0.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, posVec(0.0F, 0.35F, 0.0F)),
					keySoft(0.25F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, posVec(0.0F, -0.35F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.BODY, rotation(
					1.0F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, 3.0F)),
					keySoft(0.5F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, degreeVec(0.0F, 0.0F, -3.0F))
			))
			.addAnimation(DanceAnimation.DancePart.NOSE, rotation(
					1.0F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, 10.0F)),
					keySoft(0.5F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, degreeVec(0.0F, 0.0F, -10.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_ARM, position(
					0.5F,
					keySoft(0.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, posVec(0.0F, 0.5F, 0.0F)),
					keySoft(0.25F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, posVec(0.0F, -0.5F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_ARM, rotation(
					0.5F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, 80.0F)),
					keySoft(0.125F, degreeVec(0.0F, 0.0F, 70.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, 60.0F)),
					keySoft(0.375F, degreeVec(0.0F, 0.0F, 70.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_ARM, position(
					0.5F,
					keySoft(0.0F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.125F, posVec(0.0F, 0.5F, 0.0F)),
					keySoft(0.25F, posVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.375F, posVec(0.0F, -0.5F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_ARM, rotation(
					0.5F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, -80.0F)),
					keySoft(0.125F, degreeVec(0.0F, 0.0F, -70.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, -60.0F)),
					keySoft(0.375F, degreeVec(0.0F, 0.0F, -70.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_LEG, rotation(
					0.5F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, 5.0F)),
					keySoft(0.125F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, -5.0F)),
					keySoft(0.375F, degreeVec(0.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_LEG, rotation(
					0.5F,
					keySoft(0.0F, degreeVec(0.0F, 0.0F, 5.0F)),
					keySoft(0.125F, degreeVec(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, degreeVec(0.0F, 0.0F, -5.0F)),
					keySoft(0.375F, degreeVec(0.0F, 0.0F, 0.0F))
			))
			.build();


	@SuppressWarnings("SameParameterValue")
	private static void animate(IDanceableModel model, DanceAnimation danceAnimation, long accumulatedTime, float speed, Vector3f cache) {
		float timeStamp = getElapsedSeconds(danceAnimation, accumulatedTime);

		for(Map.Entry<DanceAnimation.DancePart, List<DanceAnimationChannel>> entry : danceAnimation.animations().entrySet()) {
			ModelPart modelPart = entry.getKey().getPart(model);
			if(modelPart != null) {
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

	static Keyframe keyHard(float timestamp, Vector3f target) {
		return new Keyframe(timestamp, target, DanceAnimationChannel.Interpolations.LINEAR);
	}
	static Keyframe keySoft(float timestamp, Vector3f target) {
		return new Keyframe(timestamp, target, DanceAnimationChannel.Interpolations.CATMULL_ROM);
	}
	static DanceAnimationChannel rotation(float lengthInSeconds, Keyframe... keyframes) {
		return new DanceAnimationChannel(Targets.ROTATION, new DanceAnimationChannel.Keyframes(lengthInSeconds, keyframes));
	}
	static DanceAnimationChannel position(float lengthInSeconds, Keyframe... keyframes) {
		return new DanceAnimationChannel(Targets.POSITION, new DanceAnimationChannel.Keyframes(lengthInSeconds, keyframes));
	}
	static DanceAnimationChannel scale(float lengthInSeconds, Keyframe... keyframes) {
		return new DanceAnimationChannel(Targets.SCALE, new DanceAnimationChannel.Keyframes(lengthInSeconds, keyframes));
	}

	@SuppressWarnings("SameParameterValue")
	static Vector3f posVec(float x, float y, float z) {
		return new Vector3f(x, -y, z);
	}
	@SuppressWarnings("SameParameterValue")
	static Vector3f degreeVec(float xRot, float yRot, float p_254397_) {
		return new Vector3f(xRot * Mth.DEG_TO_RAD, yRot * Mth.DEG_TO_RAD, p_254397_ * Mth.DEG_TO_RAD);
	}
	@SuppressWarnings("SameParameterValue")
	static Vector3f scaleVec(float xScale, float yScale, float zScale) {
		return new Vector3f(xScale - 1.0F, yScale - 1.0F, zScale - 1.0F);
	}
}
