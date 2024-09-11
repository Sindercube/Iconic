package com.sindercube.iconic.util.file;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sindercube.iconic.Iconic.LOGGER;

public interface CodecObjectLoader<T> extends SimpleSynchronousResourceReloadListener {

	Codec<T> getCodec();

	String getStartingPath();
	Boolean matchPath(Identifier path);

	@Override
	default void reload(ResourceManager manager) {
		Codec<T> codec = getCodec();
		Map<Identifier, Resource> resources = manager.findResources(getStartingPath(), this::matchPath);
		Map<Identifier, T> data = new HashMap<>();
		for (Identifier identifier : resources.keySet()) {
			BufferedReader reader;
			try {
				reader = resources.get(identifier).getReader();
			} catch (IOException e) {
				continue;
			}
			try {
				JsonElement element = JsonParser.parseReader(reader);
				T object = codec.parse(JsonOps.INSTANCE, element).getOrThrow();
				data.put(identifier, object);
			} catch (ClassCastException exception) {
				LOGGER.error("Unable to load emote file '{}': {}", identifier, exception);
			}
		}
		process(data);
	}

	void process(Map<Identifier, T> data);

}
