package com.hexagram2021.everyxdance;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(EveryXDance.MODID)
public class EveryXDance {
	public static final String MODID = "everyxdance";

	public EveryXDance() {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
