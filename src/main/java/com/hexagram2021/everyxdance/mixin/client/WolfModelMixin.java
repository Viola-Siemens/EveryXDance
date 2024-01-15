package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(WolfModel.class)
public class WolfModelMixin<T extends Wolf> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
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

	@Unique
	private Backup everyxdance$backup = Backup.empty();
	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Wolf;FFFFF)V", at = @At(value = "RETURN"))
	private void everyxdance$setupAnimIfDancing(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof IDanceableEntity danceableEntity && danceableEntity.everyxdance$isDancing()) {
			if(this.everyxdance$reset) {
				this.everyxdance$reset = false;
				IDanceableModel.createBackup(this);
			}
			IDanceableModel.performDance(IDanceableModel.getDancePresetIndex(entity.level().getRandom()), this, ageInTicks);
		} else if(!this.everyxdance$reset) {
			IDanceableModel.reset(this);
			this.everyxdance$reset = true;
		}
	}

	@Override
	public ModelPart everyxdance$getHead() {
		return this.head;
	}
	@Override
	public ModelPart everyxdance$getBody() {
		return this.body;
	}
	@Override
	public ModelPart everyxdance$getRightArm() {
		return this.rightFrontLeg;
	}
	@Override
	public ModelPart everyxdance$getLeftArm() {
		return this.leftFrontLeg;
	}
	@Override
	public ModelPart everyxdance$getRightLeg() {
		return this.rightHindLeg;
	}
	@Override
	public ModelPart everyxdance$getLeftLeg() {
		return this.leftHindLeg;
	}
	@Override @Nullable
	public ModelPart everyxdance$getNose() {
		return null;
	}

	@Override
	public Backup everyxdance$getBackup() {
		return this.everyxdance$backup;
	}
	@Override
	public void everyxdance$setBackup(Backup backup) {
		this.everyxdance$backup = backup;
	}
}