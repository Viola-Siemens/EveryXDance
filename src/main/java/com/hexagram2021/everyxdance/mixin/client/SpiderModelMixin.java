package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderModel.class)
public abstract class SpiderModelMixin<T extends Entity> implements IDanceableModel {
	@Shadow @Final
	private static String BODY_0;
	@Shadow @Final
	private static String BODY_1;
	@Shadow @Final
	private ModelPart root;
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart rightFrontLeg;
	@Shadow @Final
	private ModelPart leftFrontLeg;
	@Shadow @Final
	private ModelPart rightMiddleFrontLeg;
	@Shadow @Final
	private ModelPart leftMiddleFrontLeg;
	@Shadow @Final
	private ModelPart rightMiddleHindLeg;
	@Shadow @Final
	private ModelPart leftMiddleHindLeg;
	@Shadow @Final
	private ModelPart rightHindLeg;
	@Shadow @Final
	private ModelPart leftHindLeg;

	@Shadow
	public abstract ModelPart root();

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim", at = @At(value = "RETURN"))
	private void everyxdance$setupAnimIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			if(this.everyxdance$reset) {
				this.everyxdance$reset = false;
			}
			IDanceableModel.performDance(this, entity, danceableEntity, entity.tickCount);
		} else if(!this.everyxdance$reset) {
			this.everyxdance$reset();
			this.everyxdance$reset = true;
		}
	}

	@Override
	public AnimatedModelPart everyxdance$getHead() {
		return new AnimatedModelPart(this.head);
	}
	@Override
	public AnimatedModelPart everyxdance$getBody() {
		return new AnimatedModelPart(
				this.root.getChild(BODY_0), this.root.getChild(BODY_1),
				this.leftMiddleFrontLeg, this.rightMiddleFrontLeg,
				this.leftMiddleHindLeg, this.rightMiddleHindLeg
		);
	}
	@Override
	public AnimatedModelPart everyxdance$getRightArm() {
		return new AnimatedModelPart(this.rightFrontLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftArm() {
		return new AnimatedModelPart(this.leftFrontLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getRightLeg() {
		return new AnimatedModelPart(this.rightHindLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart(this.leftHindLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getNose() {
		return new AnimatedModelPart();
	}

	@Override
	public void everyxdance$reset() {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.leftHindLeg.zRot = Mth.HALF_PI;
				this.rightHindLeg.zRot = -Mth.HALF_PI;
				this.leftFrontLeg.yRot = this.rightFrontLeg.yRot = -Mth.PI;
				this.leftFrontLeg.zRot = -Mth.HALF_PI;
				this.rightFrontLeg.zRot = Mth.HALF_PI;
				this.everyxdance$prepareUpperBody();
			}
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = Mth.PI / 10.0F;
				this.leftHindLeg.yRot = -Mth.PI * 2.0F / 5.0F;
				this.rightHindLeg.yRot = Mth.PI * 2.0F / 5.0F;
				this.leftHindLeg.zRot = Mth.PI * 2.0F / 5.0F;
				this.rightHindLeg.zRot = -Mth.PI * 2.0F / 5.0F;
				this.everyxdance$prepareUpperBody();
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody() {
		ModelPart body0 = this.root.getChild(BODY_0);
		ModelPart body1 = this.root.getChild(BODY_1);
		this.leftFrontLeg.yRot = this.rightFrontLeg.yRot = -Mth.PI;
		this.leftFrontLeg.zRot = -Mth.HALF_PI;
		this.rightFrontLeg.zRot = Mth.HALF_PI;
		body0.xRot = body1.xRot = -Mth.HALF_PI;
		body0.y = 0.0F;
		body0.z = 4.0F;
		this.head.y = -7.0F;
		this.head.z = 6.0F;
		body1.y = 9.0F;
		body1.z = 4.0F;
		this.leftFrontLeg.y = 4.0F;
		this.leftFrontLeg.z = 2.0F;
		this.rightFrontLeg.y = 4.0F;
		this.rightFrontLeg.z = 2.0F;
		this.leftMiddleFrontLeg.y = 8.0F;
		this.leftMiddleFrontLeg.z = 2.0F;
		this.rightMiddleFrontLeg.y = 8.0F;
		this.rightMiddleFrontLeg.z = 2.0F;
		this.leftMiddleFrontLeg.yRot = 25.0F * Mth.DEG_TO_RAD;
		this.rightMiddleFrontLeg.yRot = -25.0F * Mth.DEG_TO_RAD;
		this.leftMiddleFrontLeg.zRot = -15.0F * Mth.DEG_TO_RAD;
		this.rightMiddleFrontLeg.zRot = 15.0F * Mth.DEG_TO_RAD;
		this.leftMiddleHindLeg.y = 12.0F;
		this.leftMiddleHindLeg.z = 2.0F;
		this.rightMiddleHindLeg.y = 12.0F;
		this.rightMiddleHindLeg.z = 2.0F;
		this.leftMiddleHindLeg.yRot = 25.0F * Mth.DEG_TO_RAD;
		this.rightMiddleHindLeg.yRot = -25.0F * Mth.DEG_TO_RAD;
		this.leftMiddleHindLeg.zRot = 15.0F * Mth.DEG_TO_RAD;
		this.rightMiddleHindLeg.zRot = -15.0F * Mth.DEG_TO_RAD;
	}
}
