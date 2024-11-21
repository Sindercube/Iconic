package com.sindercube.iconic.eml;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.loader.AnimationLoader;
import com.sindercube.iconic.eml.loader.ModelLoader;
import com.sindercube.iconic.eml.loader.animation.geo.GeoAnimationLoader;
import com.sindercube.iconic.eml.loader.model.geo.GeoModelLoader;
import com.sindercube.iconic.eml.manager.EmlAnimationManager;
import com.sindercube.iconic.eml.manager.EmlModelManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.ResourceType;

public class IconicEntityModelLoader implements ClientModInitializer {

	public static RegistryKey<Registry<ModelLoader>> MODEL_LOADER_KEY = RegistryKey.ofRegistry(Iconic.of("model_loader"));
	public static Registry<ModelLoader> MODEL_LOADER_REGISTRY = FabricRegistryBuilder.createSimple(MODEL_LOADER_KEY).buildAndRegister();

	public static RegistryKey<Registry<AnimationLoader>> ANIMATION_LOADER_KEY = RegistryKey.ofRegistry(Iconic.of("animation_loader"));
	public static Registry<AnimationLoader> ANIMATION_LOADER_REGISTRY = FabricRegistryBuilder.createSimple(ANIMATION_LOADER_KEY).buildAndRegister();

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new EmlModelManager());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new EmlAnimationManager());
		Registry.register(MODEL_LOADER_REGISTRY, Iconic.of("geo_model"), GeoModelLoader.INSTANCE);
		Registry.register(ANIMATION_LOADER_REGISTRY, Iconic.of("geo_animation"), GeoAnimationLoader.INSTANCE);
	}

}
