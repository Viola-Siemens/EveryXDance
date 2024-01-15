package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(RabbitModel.class)
public class RabbitModelMixin<T extends Rabbit> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart body;
	@Shadow @Final
	private ModelPart rightFrontLeg;
	@Shadow @Final
	private ModelPart leftFrontLeg;
	@Shadow @Final
	private ModelPart nose;

	@Unique
	private Backup everyxdance$backup = Backup.empty();
	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/Rabbit;FFFFF)V", at = @At(value = "RETURN"))
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
	@Override @Nullable
	public ModelPart everyxdance$getRightLeg() {
		return null;
	}
	@Override @Nullable
	public ModelPart everyxdance$getLeftLeg() {
		return null;
	}
	@Override
	public ModelPart everyxdance$getNose() {
		return this.nose;
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
