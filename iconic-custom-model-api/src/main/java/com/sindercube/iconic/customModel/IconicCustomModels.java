package com.sindercube.iconic.customModel;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.loader.CustomAnimationLoader;
import com.sindercube.iconic.customModel.loader.CustomModelLoader;
import com.sindercube.iconic.customModel.type.animation.geo.GeoAnimation;
import com.sindercube.iconic.customModel.type.model.geo.GeoModel;
import com.sindercube.iconic.customModel.type.CustomAnimation;
import com.sindercube.iconic.customModel.type.CustomModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.*;

public class IconicCustomModels implements ClientModInitializer {

	public static RegistryKey<Registry<CustomModel.Type>> MODEL_TYPE_KEY = RegistryKey.ofRegistry(Iconic.of("model_loader"));
	public static Registry<CustomModel.Type> MODEL_TYPE_REGISTRY = FabricRegistryBuilder.createSimple(MODEL_TYPE_KEY).buildAndRegister();

	public static RegistryKey<Registry<CustomAnimation.Type>> ANIMATION_TYPE_KEY = RegistryKey.ofRegistry(Iconic.of("animation_loader"));
	public static Registry<CustomAnimation.Type> ANIMATION_TYPE_REGISTRY = FabricRegistryBuilder.createSimple(ANIMATION_TYPE_KEY).buildAndRegister();

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new CustomModelLoader());
//		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new CustomAnimationLoader());

		Registry.register(MODEL_TYPE_REGISTRY, Iconic.of("geo_model"), GeoModel.TYPE);
		Registry.register(ANIMATION_TYPE_REGISTRY, Iconic.of("geo_animation"), GeoAnimation.TYPE);
	}

}
