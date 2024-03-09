package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.client.model.IPrepareDanceModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanModel.class)
public abstract class EndermanModelMixin<T extends LivingEntity> extends HumanoidModel<T> implements IPrepareDanceModel {
	protected EndermanModelMixin(ModelPart root) {
		super(root);
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", shift = At.Shift.AFTER), cancellable = true)
	private void everyxdance$ignoreIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			ci.cancel();
		}
	}

	@Override
	public void everyxdance$prepareDance(IDanceableModel.Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_SIT -> {
				this.leftLeg.xRot = this.rightLeg.xRot = -Mth.PI * 2.0F / 5.0F;
				this.leftLeg.yRot = -Mth.PI / 10.0F;
				this.rightLeg.yRot = Mth.PI / 10.0F;
			}
			case HUMANOID_CRAWL -> {
				this.body.xRot = Mth.HALF_PI;
				this.body.y = -4.0F;
				this.body.z = -11.0F;
				this.head.y = -2.0F;
				this.head.z = -14.5F;
				this.hat.y = -2.0F;
				this.hat.z = -14.5F;
				this.leftArm.xRot = this.rightArm.xRot = 0.0F;
				this.leftArm.y = this.rightArm.y = -4.0F;
				this.leftArm.z = this.rightArm.z = -10.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
