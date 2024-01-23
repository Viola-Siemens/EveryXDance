package com.hexagram2021.everyxdance.client.animation;

import net.minecraft.client.model.geom.ModelPart;

/**
 * Sealed model parts for an entity model. If no part is provided from the origin model, simply pass empty to modelParts.
 *
 * @param modelParts	All model parts related to a logical part of mob. For example, a rabbit's head contains four sub-parts: main head, nose, and two long ears.
 */
public record AnimatedModelPart(ModelPart... modelParts) {
	public void resetPose() {
		for(ModelPart modelPart: this.modelParts) {
			modelPart.resetPose();
		}
	}
}
