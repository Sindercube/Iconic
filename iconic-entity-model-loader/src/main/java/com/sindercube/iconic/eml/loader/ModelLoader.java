package com.sindercube.iconic.eml.loader;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface ModelLoader {

	default Map<Identifier, TexturedModelData> processResource(ResourceManager manager) {
		Map<Identifier, TexturedModelData> result = new HashMap<>();

		Map<Identifier, Resource> resources = getResources(manager);
		resources.forEach((path, resource) -> {
			Optional<TexturedModelData> data = processResource(path, resource);
			data.ifPresent(modelData -> result.put(path, modelData));
		});

		return result;
	}

	Map<Identifier, Resource> getResources(ResourceManager manager);

	Optional<TexturedModelData> processResource(Identifier path, Resource resource);

}
