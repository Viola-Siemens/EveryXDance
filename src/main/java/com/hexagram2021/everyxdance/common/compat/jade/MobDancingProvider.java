package com.hexagram2021.everyxdance.common.compat.jade;

import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;

public enum MobDancingProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
	INSTANCE;

	private static final String TAG_DANCING_REMAINING = "DancingRemaining";

	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		if(entityAccessor.getServerData().contains(TAG_DANCING_REMAINING)) {
			int time = entityAccessor.getServerData().getInt(TAG_DANCING_REMAINING);
			if(time > 0) {
				iTooltip.add(Component.translatable("jade.everyxdance.remaining.time", IThemeHelper.get().seconds(time)));
			}
		}
	}

	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(MODID, "mob_dancing");
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
		int time = -1;
		Entity entity = entityAccessor.getEntity();
		if (entity instanceof IDanceableEntity danceableEntity) {
			time = danceableEntity.everyxdance$getRemainingDanceTick();
		}

		if (time > 0) {
			compoundTag.putInt(TAG_DANCING_REMAINING, time);
		}
	}
}
