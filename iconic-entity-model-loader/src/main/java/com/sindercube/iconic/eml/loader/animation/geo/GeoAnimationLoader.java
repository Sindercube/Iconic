package com.sindercube.iconic.eml.loader.animation.geo;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.loader.AnimationLoader;
import com.sindercube.iconic.eml.loader.animation.geo.data.GeoAnimationData;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;

public class GeoAnimationLoader implements AnimationLoader {

	public static final GeoAnimationLoader INSTANCE = new GeoAnimationLoader();


	@Override
	public Map<Identifier, Resource> getResources(ResourceManager manager) {
		return manager.findResources("models", path -> path.getPath().endsWith(".geo.json"));
	}

	@Override
	public Optional<Map<String, Animation>> processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader);
			return Optional.of(GeoAnimationData.CODEC.parse(JsonOps.INSTANCE, element).getOrThrow().getAnimationMap());
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while loading model file '{}': {}", path, exception);
			return Optional.empty();
		}
	}

}
