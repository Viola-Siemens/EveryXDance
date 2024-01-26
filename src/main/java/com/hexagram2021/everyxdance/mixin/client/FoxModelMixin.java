package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.Fox;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxModel.class)
public abstract class FoxModelMixin<T extends Fox> implements IDanceableModel {
	@Shadow @Final
	public ModelPart head;
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

	@Shadow
	protected abstract Iterable<ModelPart> headParts();
	@Shadow
	protected abstract Iterable<ModelPart> bodyParts();

	@Unique
	private boolean everyxdance$reset = true;

	@Redirect(method = "createBodyLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/builders/CubeListBuilder;addBox(FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;", ordinal = 1))
	private static CubeListBuilder everyxdance$modifyCube(CubeListBuilder instance, float originX, float originY, float originZ, float dimensionX, float dimensionY, float dimensionZ, CubeDeformation deformation) {
		return instance.addBox(originX - 6.0F, originY, originZ, dimensionX, dimensionY, dimensionZ, deformation);
	}

	@Inject(method = "createBodyLayer", at = @At(value = "RETURN"))
	private static void everyxdance$modifyPivot(CallbackInfoReturnable<LayerDefinition> cir) {
		PartDefinition rightArm = cir.getReturnValue().mesh.getRoot().getChild("right_front_leg");
		rightArm.partPose.x = 1.0F;
		PartDefinition rightLeg = cir.getReturnValue().mesh.getRoot().getChild("right_hind_leg");
		rightLeg.partPose.x = 1.0F;
	}

	@Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Fox;FFF)V", at = @At(value = "TAIL"))
	private void everyxdance$fixRightArmAndLeg(T entity, float limbSwing, float limbSwingAmount, float tick, CallbackInfo ci) {
		this.rightFrontLeg.x = 1.0F;
		this.rightHindLeg.x = 1.0F;
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Fox;FFFFF)V", at = @At(value = "RETURN"))
	private void everyxdance$setupAnimIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			if(this.everyxdance$reset) {
				this.everyxdance$reset = false;
			}
			IDanceableModel.performDance(this, entity.isBaby(), danceableEntity, entity.tickCount);
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
		return new AnimatedModelPart(this.head.getChild("nose"));
	}

	@Override
	public void everyxdance$reset() {
		this.headParts().forEach(ModelPart::resetPose);
		this.everyxdance$getNose().resetPose();
		this.bodyParts().forEach(ModelPart::resetPose);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, boolean isBaby) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.body.xRot = 0.0F;
				if(isBaby) {
					this.head.y = 9.5F;
					this.head.z = 1.0F;
				} else {
					this.head.y = 4.5F;
					this.head.z = 6.0F;
				}
				this.body.y = 3.5F;
				this.body.z = 8.0F;
				this.leftFrontLeg.y = 10.0F;
				this.leftFrontLeg.z = 6.0F;
				this.rightFrontLeg.y = 10.0F;
				this.rightFrontLeg.z = 6.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
