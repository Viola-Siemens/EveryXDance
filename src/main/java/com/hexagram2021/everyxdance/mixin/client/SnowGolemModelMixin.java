package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.SnowGolemModel;
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

@Mixin(SnowGolemModel.class)
public abstract class SnowGolemModelMixin<T extends Entity> implements IDanceableModel {
	@Shadow @Final
	private ModelPart root;
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart upperBody;
	@Shadow @Final
	private ModelPart rightArm;
	@Shadow @Final
	private ModelPart leftArm;

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
		return new AnimatedModelPart(this.upperBody);
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
	@SuppressWarnings("SwitchStatementWithTooFewBranches")
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_CRAWL -> {
				ModelPart body = this.root.getChild("lower_body");
				body.xRot = Mth.HALF_PI;
				body.y = 18.0F;
				body.z = 4.0F;
				this.upperBody.xRot = Mth.HALF_PI;
				this.upperBody.y = 18.0F;
				this.upperBody.z = -7.0F;
				this.head.y = 22.0F;
				this.head.z = -19.0F;
				this.leftArm.y = this.rightArm.y = 14.0F;
				this.leftArm.z = this.rightArm.z = -13.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
