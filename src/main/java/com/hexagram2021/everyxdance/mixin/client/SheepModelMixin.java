package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.client.model.IPrepareDanceModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SheepModel.class)
public class SheepModelMixin<T extends Sheep> extends QuadrupedModel<T> implements IPrepareDanceModel {
	protected SheepModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Override
	public void everyxdance$prepareDance(IDanceableModel.Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> this.everyxdance$prepareUpperBody(entity instanceof LivingEntity living && living.isBaby());
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = -Mth.PI * 2.0F / 5.0F;
				this.leftHindLeg.yRot = -Mth.PI / 10.0F;
				this.rightHindLeg.yRot = Mth.PI / 10.0F;
				this.everyxdance$prepareUpperBody(entity instanceof LivingEntity living && living.isBaby());
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody(boolean isBaby) {
		this.body.xRot = 0.0F;
		if(isBaby) {
			this.head.y = -1.0F;
			this.head.z = 0.0F;
		} else {
			this.head.y = -6.5F;
			this.head.z = 6.0F;
		}
		this.body.y = 5.0F;
		this.body.z = 12.0F;
		this.leftFrontLeg.y = 0.0F;
		this.leftFrontLeg.z = 6.0F;
		this.rightFrontLeg.y = 0.0F;
		this.rightFrontLeg.z = 6.0F;
	}
}
