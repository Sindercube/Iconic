package com.sindercube.iconic.customModel.loader;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.IconicCustomModels;
import com.sindercube.iconic.customModel.api.event.CustomModelLoadingEvents;
import com.sindercube.iconic.customModel.type.CustomModel;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class CustomModelLoader extends CustomGenericLoader<CustomModel.Type, TexturedModelData> {

	@Override
	public Registry<CustomModel.Type> getRegistry() {
		return IconicCustomModels.MODEL_TYPE_REGISTRY;
	}

	@Override
	public Identifier getFabricId() {
		return Iconic.of("custom_models");
	}

	@Override
	public String getStartingPath() {
		return "models";
	}


	public static CustomModelLoader INSTANCE;

	public static TexturedModelData getAnimation(Identifier path) {
		return INSTANCE.get(path);
	}

	public static TexturedModelData getEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("entity/"));
	}

	public static TexturedModelData getBlockEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("block_entity/"));
	}

	public CustomModelLoader() {
		this.resourceMap.clear();
		INSTANCE = this;
	}

	@Override
	public Void prepare(ResourceManager manager, Profiler profiler) {
		CustomModelLoadingEvents.AFTER_MODELS_LOADED.invoker().afterModelsLoaded();
		return super.prepare(manager, profiler);
	}

}
