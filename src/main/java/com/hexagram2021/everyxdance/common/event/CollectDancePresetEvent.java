package com.hexagram2021.everyxdance.common.event;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.BiConsumer;

public class CollectDancePresetEvent extends Event implements IModBusEvent {

	protected final List<IDanceableModel.Preset> presets = Lists.newArrayList();

	public CollectDancePresetEvent() {
	}

	public void register(String name, BiConsumer<IDanceableModel, Float> preset) {
		this.presets.add(new IDanceableModel.Preset(name, preset));
	}
	public void remove(String name) {
		this.presets.removeIf(preset -> preset.name().equals(name));
	}

	public List<IDanceableModel.Preset> getPresets() {
		return this.presets;
	}
}
