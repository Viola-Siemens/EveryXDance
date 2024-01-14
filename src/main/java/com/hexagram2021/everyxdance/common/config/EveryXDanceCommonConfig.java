package com.hexagram2021.everyxdance.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class EveryXDanceCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.IntValue MOB_DANCE_TOTAL_TICKS;
	public static final ForgeConfigSpec.IntValue MOB_DANCE_POSSIBILITY;

	private EveryXDanceCommonConfig() {
	}

	static {
		BUILDER.push("everyxdance-common-config");
		MOB_DANCE_TOTAL_TICKS = BUILDER.comment("How many ticks (1 sec = 20 ticks) will a dance last.").defineInRange("MOB_DANCE_TOTAL_TICKS", 120, 20, 72000);
		MOB_DANCE_POSSIBILITY = BUILDER.comment("How possible will a mob dance. 0: never, 100: always.").defineInRange("MOB_DANCE_POSSIBILITY", 25, 0, 100);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
