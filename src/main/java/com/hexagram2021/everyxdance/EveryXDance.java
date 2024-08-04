package com.hexagram2021.everyxdance;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(EveryXDance.MODID)
public class EveryXDance {
	public static final String MODID = "everyxdance";

	public EveryXDance() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EveryXDanceCommonConfig.getConfig());
	}
}
