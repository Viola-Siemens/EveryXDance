package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenModel.class)
public abstract class WardenModelMixin<T extends Warden> implements IDanceableModel {
	@Shadow @Final
	protected ModelPart head;
	@Shadow @Final
	protected ModelPart body;
	@Shadow @Final
	protected ModelPart rightArm;
	@Shadow @Final
	protected ModelPart leftArm;
	@Shadow @Final
	protected ModelPart rightLeg;
	@Shadow @Final
	protected ModelPart leftLeg;

	@Shadow
	public abstract ModelPart root();

	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/warden/Warden;FFFFF)V", at = @At(value = "RETURN"))
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
