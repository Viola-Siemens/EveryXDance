package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemModel.class)
public abstract class IronGolemModelMixin<T extends IronGolem> implements IDanceableModel {
	@Shadow @Final
	private ModelPart root;
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart rightArm;
	@Shadow @Final
	private ModelPart leftArm;
	@Shadow @Final
	private ModelPart rightLeg;
	@Shadow @Final
	private ModelPart leftLeg;

	@Shadow
	public abstract ModelPart root();

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "createBodyLayer", at = @At("RETURN"))
	private static void everyxdance$modifyPivot(CallbackInfoReturnable<LayerDefinition> cir) {
		PartDefinition leftArm = cir.getReturnValue().mesh.getRoot().getChild("left_arm");
		leftArm.partPose.x = 10.0F;
		leftArm.cubes.forEach(cubeDefinition -> cubeDefinition.origin.add(-10.0F, 0.0F, 0.0F));
		PartDefinition rightArm = cir.getReturnValue().mesh.getRoot().getChild("right_arm");
		rightArm.partPose.x = -10.0F;
		rightArm.cubes.forEach(cubeDefinition -> cubeDefinition.origin.add(10.0F, 0.0F, 0.0F));
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/IronGolem;FFFFF)V", at = @At(value = "RETURN"))
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
		return new AnimatedModelPart(this.root.getChild("body"));
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
		return new AnimatedModelPart(this.rightLeg);
	}
	@Override
	public AnimatedModelPart everyxdance$getLeftLeg() {
		return new AnimatedModelPart(this.leftLeg);
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
			case HUMANOID_SIT -> {
				this.leftLeg.xRot = this.rightLeg.xRot = Mth.PI * 2.0F / 5.0F;
				this.leftLeg.yRot = -Mth.PI / 10.0F;
				this.rightLeg.yRot = Mth.PI / 10.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
