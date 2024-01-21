package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VexModel.class)
public abstract class VexModelMixin implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart rightArm;
	@Shadow @Final
	private ModelPart leftArm;

	@Shadow
	public abstract ModelPart root();

	@Unique
	private boolean everyxdance$reset = true;
	@Unique
	private int everyxdance$index = 0;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/Vex;FFFFF)V", at = @At(value = "RETURN"))
	private void everyxdance$setupAnimIfDancing(Vex entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			if(this.everyxdance$reset) {
				this.everyxdance$reset = false;
				this.everyxdance$index = IDanceableModel.getDancePresetIndex(entity.level().getRandom());
			}
			IDanceableModel.performDance(this, entity.isBaby(), danceableEntity.everyxdance$getAnimationState(), entity.tickCount);
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
		return new AnimatedModelPart(this.rightArm);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftArm() {
		return new AnimatedModelPart(this.leftArm);
	}
	@Override
	public AnimatedModelPart everyxdance$getRightLeg() {
		return new AnimatedModelPart();
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart();
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
	public void everyxdance$prepareDance(Preset.Preparation preparation, boolean isBaby) {
	}
	@Override
	public int everyxdance$getDanceIndex() {
		return this.everyxdance$index;
	}
}
