package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.client.model.IPrepareDanceModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearModel.class)
public class PolarBearModelMixin<T extends PolarBear> extends QuadrupedModel<T> implements IPrepareDanceModel {
	protected PolarBearModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/PolarBear;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/QuadrupedModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", shift = At.Shift.AFTER), cancellable = true)
	private void everyxdance$ignoreIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			ci.cancel();
		}
	}

	@Override
	public void everyxdance$prepareDance(IDanceableModel.Preset.Preparation preparation, boolean isBaby) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.body.xRot = 0.0F;
				if(isBaby) {
					this.head.y = -11.0F;
					this.head.z = 0.0F;
				} else {
					this.head.y = -16.0F;
					this.head.z = 6.0F;
				}
				this.body.y = 13.0F;
				this.body.z = 12.0F;
				this.leftFrontLeg.y = -8.0F;
				this.leftFrontLeg.z = 6.0F;
				this.rightFrontLeg.y = -8.0F;
				this.rightFrontLeg.z = 6.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
