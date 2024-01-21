package com.hexagram2021.everyxdance.common.compat.jade;

import com.hexagram2021.everyxdance.common.entity.IDanceableEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;

public enum MobDancingProvider implements IEntityComponentProvider {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		if(entityAccessor.getEntity() instanceof IDanceableEntity danceableEntity) {
			int time = danceableEntity.everyxdance$getRemainingDanceTick();
			if(time > 0) {
				iTooltip.add(Component.translatable("jade.everyxdance.remaining.time", IThemeHelper.get().seconds(time)));
			}
		}
	}

	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(MODID, "mob_dancing");
	}
}
