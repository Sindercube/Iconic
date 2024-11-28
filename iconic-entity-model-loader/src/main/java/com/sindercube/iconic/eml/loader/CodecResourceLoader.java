package com.sindercube.iconic.eml.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;

public interface CodecResourceLoader<T> extends ResourceLoader<T> {

	Codec<T> getCodec();

	Map<Identifier, Resource> getResources(ResourceManager manager);

	@Override
	default Optional<T> processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader);
			return Optional.of(getCodec().parse(JsonOps.INSTANCE, element).getOrThrow());
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while processing file '{}': {}", path, exception);
			return Optional.empty();
		}
	}

}
