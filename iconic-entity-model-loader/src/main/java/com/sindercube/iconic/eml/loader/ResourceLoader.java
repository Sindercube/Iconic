package com.sindercube.iconic.eml.loader;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface ResourceLoader<T> {

	default Map<Identifier, T> processResource(ResourceManager manager) {
		Map<Identifier, T> result = new HashMap<>();

		Map<Identifier, Resource> resources = getResources(manager);
		resources.forEach((path, resource) -> {
			Optional<T> data = processResource(path, resource);
			data.ifPresent(animationData -> result.put(path, animationData));
		});

		return result;
	}

	Map<Identifier, Resource> getResources(ResourceManager manager);

	Optional<T> processResource(Identifier path, Resource resource);

}
