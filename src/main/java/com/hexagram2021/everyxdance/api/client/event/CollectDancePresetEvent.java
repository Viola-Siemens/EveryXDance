package com.hexagram2021.everyxdance.api.client.event;

import com.google.common.collect.Lists;
import com.hexagram2021.everyxdance.api.client.DanceAnimation;
import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.List;

/**
 * Fired in client side, mod bus.
 * <br/>
 * You can create dance preset when subscribing this event, and also create dance preparation there.
 *
 * @see com.hexagram2021.everyxdance.client.model.IDanceableModel.Preset
 * @see com.hexagram2021.everyxdance.client.model.IDanceableModel.Preset.Preparation#create(String)
 * @see com.hexagram2021.everyxdance.client.animation.EveryXDanceAnimationPresets
 */
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
