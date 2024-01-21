package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;

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
	private float HEAD_Y = 8.0F;
	private float HEAD_Z = 10.0F;
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, boolean isBaby) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.body.xRot = -Mth.HALF_PI;
				this.head.y = HEAD_Y;
				this.head.z = HEAD_Z;
				this.body.y = 9.5F;
				this.body.z = -4.0F;
				this.leftFrontLeg.y = 3.0F;
				this.leftFrontLeg.z = 3.0F;
				this.rightFrontLeg.y = 3.0F;
				this.rightFrontLeg.z = 3.0F;
			}
			default -> MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
		}
	}
}
