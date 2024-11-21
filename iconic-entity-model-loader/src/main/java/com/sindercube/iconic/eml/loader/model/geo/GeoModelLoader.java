package com.sindercube.iconic.eml.loader.model.geo;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.loader.ModelLoader;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;

public class GeoModelLoader implements ModelLoader {

	public static final GeoModelLoader INSTANCE = new GeoModelLoader();


	@Override
	public Map<Identifier, Resource> getResources(ResourceManager manager) {
		return manager.findResources("models", path -> path.getPath().endsWith(".geo.json"));
	}

	@Override
	public Optional<TexturedModelData> processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("minecraft:geometry").get(0);
			return Optional.of(GeoModelData.CODEC.parse(JsonOps.INSTANCE, element).getOrThrow().toModelData());
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while loading model file '{}': {}", path, exception);
			return Optional.empty();
		}
	}

}
