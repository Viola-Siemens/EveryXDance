package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TurtleModel.class)
public abstract class TurtleModelMixin<T extends Turtle> extends QuadrupedModel<T> implements IDanceableModel {
	@Shadow @Final
	private ModelPart eggBelly;

	protected TurtleModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Turtle;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/QuadrupedModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", shift = At.Shift.AFTER), cancellable = true)
	private void everyxdance$ignoreIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			ci.cancel();
		}
	}

	@Override
	public AnimatedModelPart everyxdance$getBody() {
		return new AnimatedModelPart(this.body, this.eggBelly);
	}

	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = -Mth.HALF_PI;
				this.everyxdance$prepareUpperBody();
			}
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = -Mth.PI * 9.0F / 10.0F;
				this.leftHindLeg.yRot = -Mth.PI / 10.0F;
				this.rightHindLeg.yRot = Mth.PI / 10.0F;
				this.everyxdance$prepareUpperBody();
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody() {
		this.body.xRot = 0.0F;
		this.eggBelly.xRot = 0.0F;
		this.head.y = -4.0F;
		this.head.z = 4.0F;
		this.body.y = -4.5F;
		this.body.z = 16.0F;
		this.eggBelly.y = -4.5F;
		this.eggBelly.z = 16.0F;
		this.leftFrontLeg.yRot = this.rightFrontLeg.yRot = -Mth.PI;
		this.leftFrontLeg.zRot = -Mth.HALF_PI;
		this.rightFrontLeg.zRot = Mth.HALF_PI;
		this.leftFrontLeg.y = 4.0F;
		this.leftFrontLeg.z = 6.0F;
		this.rightFrontLeg.y = 4.0F;
		this.rightFrontLeg.z = 6.0F;
		this.leftHindLeg.y = this.rightHindLeg.y = 17.0F;
	}
}
