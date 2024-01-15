package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(VillagerModel.class)
public class VillagerModelMixin<T extends Entity> implements IDanceableModel {
	@Shadow @Final
	private ModelPart head;
	@Shadow @Final
	private ModelPart root;
	@Shadow @Final
	private ModelPart rightLeg;
	@Shadow @Final
	private ModelPart leftLeg;
	@Shadow @Final
	protected ModelPart nose;

	@Unique
	private Backup everyxdance$backup = Backup.empty();
	@Unique
	private boolean everyxdance$reset = true;

	@Inject(method = "setupAnim", at = @At(value = "RETURN"))
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
		return this.root.getChild("body");
	}
	@Override @Nullable
	public ModelPart everyxdance$getRightArm() {
		return null;
	}
	@Override @Nullable
	public ModelPart everyxdance$getLeftArm() {
		return null;
	}
	@Override
	public ModelPart everyxdance$getRightLeg() {
		return this.rightLeg;
	}
	@Override
	public ModelPart everyxdance$getLeftLeg() {
		return this.leftLeg;
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
