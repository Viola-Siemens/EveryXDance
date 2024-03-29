package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxolotlModel.class)
public abstract class AxolotlModelMixin<T extends Axolotl> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart rightFrontLeg;
	@Shadow @Final
	private ModelPart leftFrontLeg;
	@Shadow @Final
	private ModelPart rightHindLeg;
	@Shadow @Final
	private ModelPart leftHindLeg;
	@Shadow @Final
	private ModelPart topGills;
	@Shadow @Final
	private ModelPart tail;

	@Shadow
	protected abstract Iterable<ModelPart> headParts();
	@Shadow
	protected abstract Iterable<ModelPart> bodyParts();

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/axolotl/Axolotl;FFFFF)V", at = @At(value = "RETURN"))
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
		return new AnimatedModelPart(this.rightHindLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart(this.leftHindLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getNose() {
		return new AnimatedModelPart(this.topGills);
	}

	@Override
	public void everyxdance$reset() {
		this.headParts().forEach(ModelPart::resetPose);
		this.everyxdance$getHead().resetPose();
		this.everyxdance$getNose().resetPose();
		this.bodyParts().forEach(ModelPart::resetPose);
		this.everyxdance$getRightArm().resetPose();
		this.everyxdance$getLeftArm().resetPose();
		this.everyxdance$getRightLeg().resetPose();
		this.everyxdance$getLeftLeg().resetPose();
		this.everyxdance$getNose().resetPose();
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = Mth.HALF_PI;
				this.leftHindLeg.yRot = this.rightHindLeg.yRot = 0.0F;
				this.leftHindLeg.zRot = this.rightHindLeg.zRot = 0.0F;
				this.everyxdance$prepareUpperBody();
			}
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = -Mth.PI / 10.0F;
				this.leftHindLeg.yRot = Mth.PI / 10.0F;
				this.rightHindLeg.yRot = -Mth.PI / 10.0F;
				this.everyxdance$prepareUpperBody();
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody() {
		this.body.xRot = -Mth.HALF_PI;
		this.head.xRot = this.tail.xRot = Mth.HALF_PI;
		this.leftHindLeg.yRot = this.rightHindLeg.yRot = 0.0F;
		this.leftFrontLeg.xRot = this.rightFrontLeg.xRot = Mth.HALF_PI;
		this.leftFrontLeg.zRot = this.rightFrontLeg.zRot = Mth.PI;
		this.body.y = 20.0F;
	}
}
