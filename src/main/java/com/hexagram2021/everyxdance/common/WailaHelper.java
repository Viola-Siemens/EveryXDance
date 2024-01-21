package com.hexagram2021.everyxdance.common;

import com.hexagram2021.everyxdance.common.compat.jade.MobDancingProvider;
import net.minecraft.world.entity.Mob;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class WailaHelper implements IWailaPlugin {
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(MobDancingProvider.INSTANCE, Mob.class);
	}
}
