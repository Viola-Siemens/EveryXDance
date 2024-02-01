package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Panda;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GoatModel.class)
public abstract class GoatModelMixin<T extends Panda> extends QuadrupedModel<T> implements IDanceableModel {
	protected GoatModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Override
	public void everyxdance$reset() {
		this.headParts().forEach(ModelPart::resetPose);
		this.head.getChild("nose").resetPose();
		this.bodyParts().forEach(ModelPart::resetPose);
	}
	@Override
	public AnimatedModelPart everyxdance$getNose() {
		return new AnimatedModelPart(this.head.getChild("nose"));
	}

	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, Entity entity) {
		switch (preparation) {
			case HUMANOID_STAND -> this.everyxdance$prepareUpperBody();
			case HUMANOID_SIT -> {
				this.leftHindLeg.xRot = this.rightHindLeg.xRot = Mth.PI * 2.0F / 5.0F;
				this.leftHindLeg.yRot = -Mth.PI / 10.0F;
				this.rightHindLeg.yRot = Mth.PI / 10.0F;
				this.everyxdance$prepareUpperBody();
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}

	@Unique
	private void everyxdance$prepareUpperBody() {
		this.body.xRot = -Mth.HALF_PI;
		this.head.y = 8.0F;
		this.head.z = 10.0F;
		this.body.y = 9.5F;
		this.body.z = -4.0F;
		this.leftFrontLeg.y = 3.0F;
		this.leftFrontLeg.z = 3.0F;
		this.rightFrontLeg.y = 3.0F;
		this.rightFrontLeg.z = 3.0F;
	}
}
