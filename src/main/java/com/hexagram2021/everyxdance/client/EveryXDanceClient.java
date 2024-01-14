package com.hexagram2021.everyxdance.client;

import com.hexagram2021.everyxdance.client.model.IDanceableModel;
import com.hexagram2021.everyxdance.common.event.CollectDancePresetEvent;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.hexagram2021.everyxdance.EveryXDance.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class EveryXDanceClient {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		CollectDancePresetEvent collectDancePresetEvent = new CollectDancePresetEvent();
		ModLoader.get().postEventWrapContainerInModOrder(collectDancePresetEvent);
		IDanceableModel.PRESETS.clear();
		IDanceableModel.PRESETS.addAll(collectDancePresetEvent.getPresets());
	}
	@SubscribeEvent
	public static void onCollectDancePreset(CollectDancePresetEvent event) {
		event.register(new ResourceLocation(MODID, "piglin_dance").toString(), (model, ageInTicks) -> {
			float rate = ageInTicks / 60.0F;
			model.everyxdance$getHead().x = Mth.sin(rate * 10.0F);
			model.everyxdance$getHead().y = Mth.sin(rate * 40.0F) + 0.4F;
			ModelPart rightArm = model.everyxdance$getRightArm();
			if(rightArm != null) {
				rightArm.zRot = Mth.DEG_TO_RAD * (70.0F + Mth.cos(rate * 40.0F) * 10.0F);
				rightArm.y = Mth.sin(rate * 40.0F) * 0.5F + 1.5F;
			}
			ModelPart leftArm = model.everyxdance$getLeftArm();
			if(leftArm != null) {
				leftArm.zRot = -Mth.DEG_TO_RAD * (70.0F + Mth.cos(rate * 40.0F) * 10.0F);
				leftArm.y = Mth.sin(rate * 40.0F) * 0.5F + 1.5F;
			}
			ModelPart rightLeg = model.everyxdance$getRightLeg();
			if(rightLeg != null) {
				rightLeg.zRot = Mth.DEG_TO_RAD * Mth.cos(rate * 40.0F) * 5.0F;
			}
			ModelPart leftLeg = model.everyxdance$getLeftLeg();
			if(leftLeg != null) {
				leftLeg.zRot = Mth.DEG_TO_RAD * Mth.cos(rate * 40.0F) * 5.0F;
			}
			model.everyxdance$getBody().y = Mth.sin(rate * 40.0F) * 0.35F;
			model.everyxdance$getBody().zRot = Mth.DEG_TO_RAD * Mth.cos(rate * 20.0F) * 3.0F;
		});
	}
}
