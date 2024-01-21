package com.hexagram2021.everyxdance.client.animation;

import net.minecraft.client.model.geom.ModelPart;

public record AnimatedModelPart(ModelPart... modelParts) {
	public void resetPose() {
		for(ModelPart modelPart: this.modelParts) {
			modelPart.resetPose();
		}
	}
}
