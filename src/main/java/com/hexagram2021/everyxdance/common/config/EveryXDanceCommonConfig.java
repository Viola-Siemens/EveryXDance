package com.hexagram2021.everyxdance.common.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

import static com.hexagram2021.everyxdance.common.util.RegistryHelper.getRegistryName;

public final class EveryXDanceCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.IntValue MOB_DANCE_TOTAL_TICKS;
	public static final ForgeConfigSpec.IntValue MOB_DANCE_POSSIBILITY_ATTACK;
	public static final ForgeConfigSpec.IntValue MOB_DANCE_POSSIBILITY_BREED;

	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_DANCE_PRESETS;

	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DANCEABLE_MOB_TYPES;

	private EveryXDanceCommonConfig() {
	}

	static {
		BUILDER.push("everyxdance-common-config");
		MOB_DANCE_TOTAL_TICKS = BUILDER.comment("How many ticks (1 sec = 20 ticks) will a dance last.").defineInRange("MOB_DANCE_TOTAL_TICKS", 200, 20, 72000);
		MOB_DANCE_POSSIBILITY_ATTACK = BUILDER.comment("How possible will a mob dance after killing target or survive from attacking. 0: never, 100: always.").defineInRange("MOB_DANCE_POSSIBILITY_ATTACK", 25, 0, 100);
		MOB_DANCE_POSSIBILITY_BREED = BUILDER.comment("How possible will a mob dance after breed. 0: never, 100: always.").defineInRange("MOB_DANCE_POSSIBILITY_BREED", 100, 0, 100);
		DISABLED_DANCE_PRESETS = BUILDER.comment("Dance animations that won't show in your client (only works in client side). See client latest.log and search for \"Dancing Animations\" for getting all dancing animations.").defineListAllowEmpty("DISABLED_DANCE_PRESETS", List.of(), o -> o instanceof String);
		DANCEABLE_MOB_TYPES = BUILDER.comment("Entity types for those who can dance (only works in server side). During their dance, they will not attack other entities.").defineList("DANCEABLE_MOB_TYPES", List.of(
				getRegistryName(EntityType.AXOLOTL).toString(),
				getRegistryName(EntityType.CAT).toString(),
				getRegistryName(EntityType.CAVE_SPIDER).toString(),
				getRegistryName(EntityType.CHICKEN).toString(),
				getRegistryName(EntityType.COW).toString(),
				getRegistryName(EntityType.DONKEY).toString(),
				getRegistryName(EntityType.DROWNED).toString(),
				getRegistryName(EntityType.ENDERMAN).toString(),
				getRegistryName(EntityType.EVOKER).toString(),
				getRegistryName(EntityType.FOX).toString(),
				getRegistryName(EntityType.GOAT).toString(),
				getRegistryName(EntityType.HOGLIN).toString(),
				getRegistryName(EntityType.HORSE).toString(),
				getRegistryName(EntityType.HUSK).toString(),
				getRegistryName(EntityType.ILLUSIONER).toString(),
				getRegistryName(EntityType.IRON_GOLEM).toString(),
				getRegistryName(EntityType.LLAMA).toString(),
				getRegistryName(EntityType.MULE).toString(),
				getRegistryName(EntityType.OCELOT).toString(),
				getRegistryName(EntityType.PANDA).toString(),
				getRegistryName(EntityType.PIG).toString(),
				getRegistryName(EntityType.PIGLIN).toString(),
				getRegistryName(EntityType.PIGLIN_BRUTE).toString(),
				getRegistryName(EntityType.PILLAGER).toString(),
				getRegistryName(EntityType.POLAR_BEAR).toString(),
				getRegistryName(EntityType.RABBIT).toString(),
				getRegistryName(EntityType.RAVAGER).toString(),
				getRegistryName(EntityType.SHEEP).toString(),
				getRegistryName(EntityType.SKELETON).toString(),
				getRegistryName(EntityType.SKELETON_HORSE).toString(),
				getRegistryName(EntityType.SNOW_GOLEM).toString(),
				getRegistryName(EntityType.SPIDER).toString(),
				getRegistryName(EntityType.STRAY).toString(),
				getRegistryName(EntityType.TURTLE).toString(),
				getRegistryName(EntityType.VEX).toString(),
				getRegistryName(EntityType.VILLAGER).toString(),
				getRegistryName(EntityType.VINDICATOR).toString(),
				getRegistryName(EntityType.WANDERING_TRADER).toString(),
				getRegistryName(EntityType.WARDEN).toString(),
				getRegistryName(EntityType.WITCH).toString(),
				getRegistryName(EntityType.WITHER_SKELETON).toString(),
				getRegistryName(EntityType.WOLF).toString(),
				getRegistryName(EntityType.ZOMBIE).toString(),
				getRegistryName(EntityType.ZOMBIE_VILLAGER).toString(),
				getRegistryName(EntityType.ZOMBIE_HORSE).toString(),
				getRegistryName(EntityType.ZOMBIFIED_PIGLIN).toString(),
				getRegistryName(EntityType.ZOGLIN).toString()
		), o -> o instanceof String str && ResourceLocation.isValidResourceLocation(str));
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
