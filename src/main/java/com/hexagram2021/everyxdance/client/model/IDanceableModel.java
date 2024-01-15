package com.hexagram2021.everyxdance.client.model;

import com.google.common.collect.Lists;
import com.hexagram2021.everyxdance.common.config.EveryXDanceCommonConfig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

@OnlyIn(Dist.CLIENT)
public interface IDanceableModel {
	ModelPart everyxdance$getHead();
	ModelPart everyxdance$getBody();
	@Nullable
	ModelPart everyxdance$getRightArm();
	@Nullable
	ModelPart everyxdance$getLeftArm();
	@Nullable
	ModelPart everyxdance$getRightLeg();
	@Nullable
	ModelPart everyxdance$getLeftLeg();
	@Nullable
	ModelPart everyxdance$getNose();

	Backup everyxdance$getBackup();
	void everyxdance$setBackup(Backup backup);

	List<Preset> PRESETS = Lists.newArrayList();

	static int getDancePresetIndex(RandomSource randomSource) {
		int index = EveryXDanceCommonConfig.MOB_DANCE_PRESET_INDEX.get();
		return index == -1 ? randomSource.nextInt(PRESETS.size()) : index;
	}

	static void performDance(int presetIndex, IDanceableModel model, float ageInTicks) {
		if(presetIndex >= 0 && presetIndex < PRESETS.size()) {
			PRESETS.get(presetIndex).animation().accept(model, ageInTicks);
		}
	}

	static void createBackup(IDanceableModel model) {
		model.everyxdance$setBackup(Backup.from(model));
	}
	static void reset(IDanceableModel model) {
		model.everyxdance$getBackup().reset(model);
	}

	record Preset(String name, BiConsumer<IDanceableModel, Float> animation) implements Comparable<Preset> {
		@Override
		public int compareTo(IDanceableModel.Preset o) {
			return this.name.compareTo(o.name);
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof IDanceableModel.Preset preset) {
				return this.name.equals(preset.name);
			}
			return false;
		}
		@Override
		public int hashCode() {
			return this.name.hashCode();
		}
	}

	record BackupModelState(float x, float y, float z, float xRot, float yRot, float zRot, float xScale, float yScale, float zScale, boolean visible) {
		public static BackupModelState empty() {
			return new BackupModelState(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false);
		}

		public static BackupModelState from(@Nullable ModelPart modelPart) {
			if(modelPart == null) {
				return empty();
			}
			return new BackupModelState(
					modelPart.x, modelPart.y, modelPart.z,
					modelPart.xRot, modelPart.yRot, modelPart.zRot,
					modelPart.xScale, modelPart.yScale, modelPart.zScale,
					modelPart.visible
			);
		}

		public void reset(ModelPart modelPart) {
			modelPart.x = this.x;
			modelPart.y = this.y;
			modelPart.z = this.z;
			modelPart.xRot = this.xRot;
			modelPart.yRot = this.yRot;
			modelPart.zRot = this.zRot;
			modelPart.xScale = this.xScale;
			modelPart.yScale = this.yScale;
			modelPart.zScale = this.zScale;
			modelPart.visible = this.visible;
		}
	}

	record Backup(BackupModelState head, BackupModelState body, BackupModelState rightArm, BackupModelState leftArm,
				  BackupModelState rightLeg, BackupModelState leftLeg, BackupModelState nose) {
		public static Backup empty() {
			return new Backup(
					BackupModelState.empty(), BackupModelState.empty(), BackupModelState.empty(), BackupModelState.empty(),
					BackupModelState.empty(), BackupModelState.empty(), BackupModelState.empty()
			);
		}

		public static Backup from(IDanceableModel model) {
			return new Backup(
					BackupModelState.from(model.everyxdance$getHead()),
					BackupModelState.from(model.everyxdance$getBody()),
					BackupModelState.from(model.everyxdance$getRightArm()),
					BackupModelState.from(model.everyxdance$getLeftArm()),
					BackupModelState.from(model.everyxdance$getRightLeg()),
					BackupModelState.from(model.everyxdance$getLeftLeg()),
					BackupModelState.from(model.everyxdance$getNose())
			);
		}

		public void reset(IDanceableModel model) {
			this.head.reset(model.everyxdance$getHead());
			this.body.reset(model.everyxdance$getBody());
			ModelPart rightArm = model.everyxdance$getRightArm();
			if(rightArm != null) {
				this.rightArm.reset(rightArm);
			}
			ModelPart leftArm = model.everyxdance$getLeftArm();
			if(leftArm != null) {
				this.leftArm.reset(leftArm);
			}
			ModelPart rightLeg = model.everyxdance$getRightLeg();
			if(rightLeg != null) {
				this.rightLeg.reset(rightLeg);
			}
			ModelPart leftLeg = model.everyxdance$getLeftLeg();
			if(leftLeg != null) {
				this.leftLeg.reset(leftLeg);
			}
			ModelPart nose = model.everyxdance$getNose();
			if(nose != null) {
				this.nose.reset(nose);
			}
		}
	}
}
