package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.animation.AnimatedModelPart;
import com.hexagram2021.everyxdance.api.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TurtleModel.class)
public abstract class TurtleModelMixin<T extends Turtle> extends QuadrupedModel<T> implements IDanceableModel {
	@Shadow @Final
	private ModelPart eggBelly;

	protected TurtleModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Override
	public AnimatedModelPart everyxdance$getBody() {
		return new AnimatedModelPart(this.body, this.eggBelly);
	}
	@Override
	public void everyxdance$prepareDance(Preset.Preparation preparation, boolean isBaby) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.body.xRot = 0.0F;
				this.eggBelly.xRot = 0.0F;
				this.head.y = 0.0F;
				this.head.z = 4.0F;
				this.body.y = -0.5F;
				this.body.z = 16.0F;
				this.eggBelly.y = -0.5F;
				this.eggBelly.z = 16.0F;
				this.leftFrontLeg.y = 4.0F;
				this.leftFrontLeg.z = 6.0F;
				this.rightFrontLeg.y = 4.0F;
				this.rightFrontLeg.z = 6.0F;
			}
		}
		MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
	}
}
