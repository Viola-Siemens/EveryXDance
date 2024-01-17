package com.hexagram2021.everyxdance.client.event;

import com.hexagram2021.everyxdance.client.animation.DanceAnimation;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class CollectDancePresetEvent extends Event implements IModBusEvent {

	protected final List<IDanceableModel.Preset> presets = Lists.newArrayList();

	public CollectDancePresetEvent() {
	}

	/**
	 * @param name			name of animation
	 * @param preparation	animation preparation
	 * @param animation		animation preset
	 */
	public void register(ResourceLocation name, IDanceableModel.Preset.Preparation preparation, DanceAnimation animation) {
		this.presets.add(new IDanceableModel.Preset(name.toString(), preparation, animation));
	}

	/**
	 * @deprecated 		use the resource location one.
	 * @see 			CollectDancePresetEvent#register(ResourceLocation, IDanceableModel.Preset.Preparation, DanceAnimation)
	 * @param name		name of animation
	 * @param preparation	animation preparation
	 * @param animation		animation preset
	 */
	@Deprecated
	public void register(String name, IDanceableModel.Preset.Preparation preparation, DanceAnimation animation) {
		this.presets.add(new IDanceableModel.Preset(name, preparation, animation));
	}
	@SuppressWarnings("unused")
	public void remove(String name) {
		this.presets.removeIf(preset -> preset.name().equals(name));
	}

	public List<IDanceableModel.Preset> getPresets() {
		return this.presets;
	}
}
