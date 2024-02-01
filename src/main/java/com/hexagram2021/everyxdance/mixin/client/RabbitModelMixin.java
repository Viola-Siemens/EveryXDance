package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RabbitModel.class)
public class RabbitModelMixin<T extends Rabbit> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart leftEar;
	@Shadow @Final
	private ModelPart rightEar;
	@Shadow @Final
	private ModelPart leftFrontLeg;
	@Shadow @Final
	private ModelPart rightFrontLeg;
	@Shadow @Final
	private ModelPart leftRearFoot;
	@Shadow @Final
	private ModelPart rightRearFoot;
	@Shadow @Final
	private ModelPart leftHaunch;
	@Shadow @Final
	private ModelPart rightHaunch;
	@Shadow @Final
	private ModelPart nose;

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Rabbit;FFFFF)V", at = @At(value = "RETURN"))
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
		return new AnimatedModelPart(this.head, this.nose, this.rightEar, this.leftEar);
	}
	@Override
	public AnimatedModelPart everyxdance$getBody() {
		return new AnimatedModelPart(this.body);
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
		return new AnimatedModelPart(this.rightHaunch, this.rightRearFoot);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart(this.leftHaunch, this.leftRearFoot);
	}
	@Override
	public AnimatedModelPart everyxdance$getNose() {
		return new AnimatedModelPart(this.nose);
	}

	@Override
	public void everyxdance$reset() {
		this.head.getAllParts().forEach(ModelPart::resetPose);
		this.rightEar.getAllParts().forEach(ModelPart::resetPose);
		this.leftEar.getAllParts().forEach(ModelPart::resetPose);
		this.body.getAllParts().forEach(ModelPart::resetPose);
		this.rightFrontLeg.getAllParts().forEach(ModelPart::resetPose);
		this.leftFrontLeg.getAllParts().forEach(ModelPart::resetPose);
		this.nose.getAllParts().forEach(ModelPart::resetPose);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
