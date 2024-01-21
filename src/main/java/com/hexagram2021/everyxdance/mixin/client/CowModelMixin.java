package com.hexagram2021.everyxdance.mixin.client;

import com.hexagram2021.everyxdance.client.event.CustomPrepareDanceEvent;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.client.model.IPrepareDanceModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CowModel.class)
public class CowModelMixin<T extends Entity> extends QuadrupedModel<T> implements IPrepareDanceModel {
	protected CowModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
		super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	@Override
	public void everyxdance$prepareDance(IDanceableModel.Preset.Preparation preparation, boolean isBaby) {
		switch (preparation) {
			case HUMANOID_STAND -> {
				this.body.xRot = 0.0F;
				if(isBaby) {
					this.head.y = -4.0F;
					this.head.z = 2.0F;
				} else {
					this.head.y = -8.5F;
					this.head.z = 8.0F;
				}
				this.body.y = 5.0F;
				this.body.z = 12.0F;
				this.leftFrontLeg.y = 0.0F;
				this.leftFrontLeg.z = 6.0F;
				this.rightFrontLeg.y = 0.0F;
				this.rightFrontLeg.z = 6.0F;
			}
			default -> MinecraftForge.EVENT_BUS.post(new CustomPrepareDanceEvent(this, preparation));
		}
	}
}
