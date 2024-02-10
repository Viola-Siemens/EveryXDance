package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfModel.class)
public abstract class WolfModelMixin<T extends Wolf> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart realHead;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart upperBody;
	@Shadow @Final
	private ModelPart rightFrontLeg;
	@Shadow @Final
	private ModelPart leftFrontLeg;
	@Shadow @Final
	private ModelPart rightHindLeg;
	@Shadow @Final
	private ModelPart leftHindLeg;

	@Shadow
	protected abstract Iterable<ModelPart> headParts();
	@Shadow
	protected abstract Iterable<ModelPart> bodyParts();

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Wolf;FFFFF)V", at = @At(value = "RETURN"))
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
		return new AnimatedModelPart(this.body, this.upperBody);
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
		return new AnimatedModelPart(this.realHead);
	}

	@Override
	public void everyxdance$reset() {
		this.headParts().forEach(ModelPart::resetPose);
		this.realHead.resetPose();
		this.bodyParts().forEach(ModelPart::resetPose);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> this.everyxdance$prepareUpperBody(entity instanceof LivingEntity living && living.isBaby());
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = -Mth.PI * 2.0F / 5.0F;
				this.leftHindLeg.yRot = -Mth.PI / 10.0F;
				this.rightHindLeg.yRot = Mth.PI / 10.0F;
				this.everyxdance$prepareUpperBody(entity instanceof LivingEntity living && living.isBaby());
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody(boolean isBaby) {
		this.body.xRot = this.upperBody.xRot = 0.0F;
		this.upperBody.y = 4.5F;
		this.upperBody.z = 6.5F;
		if(isBaby) {
			this.head.y = 6.0F;
			this.head.z = 0.0F;
		} else {
			this.head.y = -0.5F;
			this.head.z = 4.0F;
		}
		this.body.y = 9.0F;
		this.body.z = 7.0F;
		this.leftFrontLeg.y = 4.0F;
		this.leftFrontLeg.z = 6.0F;
		this.rightFrontLeg.y = 4.0F;
		this.rightFrontLeg.z = 6.0F;
	}
}
