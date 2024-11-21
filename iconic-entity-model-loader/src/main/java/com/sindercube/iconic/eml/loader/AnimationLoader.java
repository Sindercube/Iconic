package com.sindercube.iconic.eml.loader;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface AnimationLoader {

	default Map<Identifier, Map<String, Animation>> processResource(ResourceManager manager) {
		Map<Identifier, Map<String, Animation>> result = new HashMap<>();

		Map<Identifier, Resource> resources = getResources(manager);
		resources.forEach((path, resource) -> {
			Optional<Map<String, Animation>> data = processResource(path, resource);
			data.ifPresent(animationData -> result.put(path, animationData));
		});

		return result;
	}

	Map<Identifier, Resource> getResources(ResourceManager manager);

	Optional<Map<String, Animation>> processResource(Identifier path, Resource resource);

}
