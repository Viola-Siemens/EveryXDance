package com.hexagram2021.everyxdance.client.animation;

import com.hexagram2021.everyxdance.api.client.DanceAnimation;
import com.hexagram2021.everyxdance.api.client.DanceAnimationChannel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import static com.hexagram2021.everyxdance.api.client.DanceAnimationChannel.*;

@OnlyIn(Dist.CLIENT)
public class EveryXDanceAnimationPresets {
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

	public static final DanceAnimation SUBJECT3 = DanceAnimation.Builder.withLength(4.0F)
			.addAnimation(DanceAnimation.DancePart.BODY, rotation(
					1.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(0.0F, -10.0F, 3.0F)),
					keySoft(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, new Vector3f(0.0F, 10.0F, -3.0F))
			))
			.addAnimation(DanceAnimation.DancePart.HEAD, rotation(
					4.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.25F, new Vector3f(5.0F, 5.0F, 0.0F)),
					keySoft(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(0.75F, new Vector3f(5.0F, -5.0F, 0.0F)),
					keySoft(1.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(1.25F, new Vector3f(5.0F, 5.0F, 0.0F)),
					keySoft(1.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(1.75F, new Vector3f(5.0F, -5.0F, 0.0F)),
					keySoft(2.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(2.25F, new Vector3f(45.0F, 0.0F, 0.0F)),
					keySoft(2.5F, new Vector3f(30.0F, 0.0F, 0.0F)),
					keySoft(2.75F, new Vector3f(45.0F, 0.0F, 0.0F)),
					keySoft(3.0F, new Vector3f(30.0F, 0.0F, 0.0F)),
					keySoft(3.25F, new Vector3f(45.0F, 0.0F, 0.0F)),
					keySoft(3.5F, new Vector3f(30.0F, 0.0F, 0.0F)),
					keySoft(3.75F, new Vector3f(45.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.HEAD, rotation(
					4.0F,
					keySoft(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(2.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keySoft(2.25F, new Vector3f(-1.0F, 0.0F, 0.0F)),
					keySoft(2.625F, new Vector3f(-1.0F, 0.0F, 0.0F)),
					keySoft(3.0F, new Vector3f(-1.0F, 0.0F, 0.0F)),
					keySoft(3.375F, new Vector3f(-1.0F, 0.0F, 0.0F)),
					keySoft(3.625F, new Vector3f(-1.0F, 0.0F, 0.0F)),
					keySoft(3.75F, new Vector3f(-1.0F, 0.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_ARM, rotation(
					4.0F,
					keyHard(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.25F, new Vector3f(-20.0F, 0.0F, -40.0F)),
					keyHard(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.75F, new Vector3f(-10.0F, 0.0F, 20.0F)),
					keyHard(1.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(1.25F, new Vector3f(-20.0F, 0.0F, -40.0F)),
					keyHard(1.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(1.75F, new Vector3f(-10.0F, 0.0F, 20.0F)),
					keyHard(2.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(2.25F, new Vector3f(-20.0F, -10.0F, -60.0F)),
					keyHard(2.5F, new Vector3f(-60.0F, -10.0F, -90.0F)),
					keyHard(2.75F, new Vector3f(-20.0F, -10.0F, -60.0F)),
					keyHard(3.0F, new Vector3f(-60.0F, -10.0F, -90.0F)),
					keyHard(3.25F, new Vector3f(-20.0F, -10.0F, -60.0F)),
					keyHard(3.5F, new Vector3f(-60.0F, -10.0F, -90.0F)),
					keyHard(3.75F, new Vector3f(-20.0F, -10.0F, -60.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_ARM, rotation(
					4.0F,
					keyHard(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.25F, new Vector3f(-10.0F, 0.0F, -20.0F)),
					keyHard(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.75F, new Vector3f(-20.0F, 0.0F, 40.0F)),
					keyHard(1.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(1.25F, new Vector3f(-10.0F, 0.0F, -20.0F)),
					keyHard(1.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(1.75F, new Vector3f(-20.0F, 0.0F, 40.0F)),
					keyHard(2.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(2.25F, new Vector3f(-60.0F, 10.0F, 90.0F)),
					keyHard(2.5F, new Vector3f(-20.0F, 10.0F, 60.0F)),
					keyHard(2.75F, new Vector3f(-60.0F, 10.0F, 90.0F)),
					keyHard(3.0F, new Vector3f(-20.0F, 10.0F, 60.0F)),
					keyHard(3.25F, new Vector3f(-60.0F, 10.0F, 90.0F)),
					keyHard(3.5F, new Vector3f(-20.0F, 10.0F, 60.0F)),
					keyHard(3.75F, new Vector3f(-60.0F, 10.0F, 90.0F))
			))
			.addAnimation(DanceAnimation.DancePart.RIGHT_LEG, rotation(
					1.0F,
					keyHard(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.25F, new Vector3f(10.0F, 30.0F, 0.0F)),
					keyHard(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.75F, new Vector3f(10.0F, -30.0F, 0.0F))
			))
			.addAnimation(DanceAnimation.DancePart.LEFT_LEG, rotation(
					1.0F,
					keyHard(0.0F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.25F, new Vector3f(10.0F, 30.0F, 0.0F)),
					keyHard(0.5F, new Vector3f(0.0F, 0.0F, 0.0F)),
					keyHard(0.75F, new Vector3f(10.0F, -30.0F, 0.0F))
			))
			.build();

	public static KeyframeHolder keyHard(float timestamp, Vector3f target) {
		return new KeyframeHolder(timestamp, target, DanceAnimationChannel.Interpolations.LINEAR);
	}
	public static KeyframeHolder keySoft(float timestamp, Vector3f target) {
		return new KeyframeHolder(timestamp, target, DanceAnimationChannel.Interpolations.CATMULL_ROM);
	}
}