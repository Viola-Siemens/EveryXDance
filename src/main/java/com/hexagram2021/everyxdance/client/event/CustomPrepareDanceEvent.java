package com.hexagram2021.everyxdance.client.event;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.client.model.IPrepareDanceModel;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fired in client side on Forge bus.
 * <br/>
 * This event is not cancellable, and does not have a result.
 */
public class CustomPrepareDanceEvent extends Event {
	private final IPrepareDanceModel model;
	private final IDanceableModel.Preset.Preparation preparation;

	public CustomPrepareDanceEvent(IPrepareDanceModel model, IDanceableModel.Preset.Preparation preparation) {
		this.model = model;
		this.preparation = preparation;
	}

	public IPrepareDanceModel getModel() {
		return this.model;
	}
	public IDanceableModel.Preset.Preparation getPreparation() {
		return this.preparation;
	}
}
