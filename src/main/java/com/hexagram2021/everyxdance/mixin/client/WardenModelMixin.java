package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenModel.class)
public class WardenModelMixin<T extends Warden> implements IDanceableModel {
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

	@Unique
	private Backup everyxdance$backup = Backup.empty();
	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/warden/Warden;FFFFF)V", at = @At(value = "RETURN"))
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
		return this.rightArm;
	}
	@Override
	public ModelPart everyxdance$getLeftArm() {
		return this.leftArm;
	}
	@Override
	public ModelPart everyxdance$getRightLeg() {
		return this.rightLeg;
	}
	@Override
	public ModelPart everyxdance$getLeftLeg() {
		return this.leftLeg;
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
