package com.hexagram2021.everyxdance.client.animation;

import com.hexagram2021.everyxdance.api.client.DanceAnimation;
import com.hexagram2021.everyxdance.api.client.DanceAnimationChannel;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
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

					currentFrame.interpolation().apply(
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
}
