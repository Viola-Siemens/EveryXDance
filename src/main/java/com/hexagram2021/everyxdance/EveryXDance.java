package com.hexagram2021.everyxdance;

import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(EveryXDance.MODID)
public class EveryXDance {
	public static final String MODID = "everyxdance";

	public EveryXDance() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EveryXDanceCommonConfig.getConfig());
		MinecraftForge.EVENT_BUS.register(this);
	}
}
