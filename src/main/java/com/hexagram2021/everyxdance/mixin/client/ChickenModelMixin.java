package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenModel.class)
public abstract class ChickenModelMixin<T extends Entity> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart rightWing;
	@Shadow @Final
	private ModelPart leftWing;
	@Shadow @Final
	private ModelPart rightLeg;
	@Shadow @Final
	private ModelPart leftLeg;
	@Shadow @Final
	private ModelPart beak;
	@Shadow @Final
	private ModelPart redThing;

	@Shadow
	protected abstract Iterable<ModelPart> headParts();
	@Shadow
	protected abstract Iterable<ModelPart> bodyParts();

	@Unique
	private boolean everyxdance$reset = true;
	@Unique
	private int everyxdance$index = 0;

	@Inject(method = "setupAnim", at = @At(value = "RETURN"))
	private void everyxdance$setupAnimIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			if(this.everyxdance$reset) {
				this.everyxdance$reset = false;
				this.everyxdance$index = IDanceableModel.getDancePresetIndex(entity.level().getRandom());
			}
			IDanceableModel.performDance(this, entity instanceof LivingEntity living && living.isBaby(), danceableEntity.everyxdance$getAnimationState(), entity.tickCount);
		} else if(!this.everyxdance$reset) {
			this.everyxdance$reset();
			this.everyxdance$reset = true;
		}
	}

	@Override
	public AnimatedModelPart everyxdance$getHead() {
		return new AnimatedModelPart(this.head, this.redThing, this.beak);
	}
	@Override
	public AnimatedModelPart everyxdance$getBody() {
		return new AnimatedModelPart(this.body);
	}
	@Override
	public AnimatedModelPart everyxdance$getRightArm() {
		return new AnimatedModelPart(this.rightWing);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftArm() {
		return new AnimatedModelPart(this.leftWing);
	}
	@Override
	public AnimatedModelPart everyxdance$getRightLeg() {
		return new AnimatedModelPart(this.rightLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart(this.leftLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getNose() {
		return new AnimatedModelPart(this.beak);
	}

	@Override
	public void everyxdance$reset() {
		this.headParts().forEach(ModelPart::resetPose);
		this.bodyParts().forEach(ModelPart::resetPose);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, boolean isBaby) {
	}
	@Override
	public int everyxdance$getDanceIndex() {
		return this.everyxdance$index;
	}
}
