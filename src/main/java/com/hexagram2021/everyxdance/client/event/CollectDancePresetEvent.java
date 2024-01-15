package com.hexagram2021.everyxdance.client.event;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.BiConsumer;

public class CollectDancePresetEvent extends Event implements IModBusEvent {

	protected final List<IDanceableModel.Preset> presets = Lists.newArrayList();

	public CollectDancePresetEvent() {
	}

	/**
	 * @param name		name of animation
	 * @param preset	animation preset
	 */
	public void register(ResourceLocation name, BiConsumer<IDanceableModel, Float> preset) {
		this.presets.add(new IDanceableModel.Preset(name.toString(), preset));
	}

	/**
	 * @deprecated 		use the resource location one.
	 * @see 			CollectDancePresetEvent#register(ResourceLocation, BiConsumer)
	 * @param name		name of animation
	 * @param preset	animation preset
	 */
	@Deprecated
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
