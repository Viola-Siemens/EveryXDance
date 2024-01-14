package com.hexagram2021.everyxdance.common.config;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import net.minecraftforge.common.ForgeConfigSpec;

public final class EveryXDanceCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.IntValue MOB_DANCE_TOTAL_TICKS;
	public static final ForgeConfigSpec.IntValue MOB_DANCE_POSSIBILITY;

	public static final ForgeConfigSpec.IntValue MOB_DANCE_PRESET_INDEX;

	private EveryXDanceCommonConfig() {
	}

	static {
		BUILDER.push("everyxdance-common-config");
		MOB_DANCE_TOTAL_TICKS = BUILDER.comment("How many ticks (1 sec = 20 ticks) will a dance last.").defineInRange("MOB_DANCE_TOTAL_TICKS", 200, 20, 72000);
		MOB_DANCE_POSSIBILITY = BUILDER.comment("How possible will a mob dance. 0: never, 100: always.").defineInRange("MOB_DANCE_POSSIBILITY", 25, 0, 100);
		MOB_DANCE_PRESET_INDEX = BUILDER.comment("Dancing animation preset index (only works in client side).").defineInRange("MOB_DANCE_PRESET_INDEX", -1, -1, IDanceableModel.PRESETS.size());
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
