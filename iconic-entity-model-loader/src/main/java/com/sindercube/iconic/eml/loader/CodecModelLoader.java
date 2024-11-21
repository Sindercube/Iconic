package com.sindercube.iconic.eml.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.Optional;

public interface CodecModelLoader extends ModelLoader {

	Codec<TexturedModelData> getCodec();

	default JsonElement preProcess(JsonElement json) {
		return json;
	}

	@Override
	default Optional<TexturedModelData> processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement json = preProcess(JsonParser.parseReader(reader));
			return Optional.of(getCodec().parse(JsonOps.INSTANCE, json).getOrThrow());
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while loading model file '{}': {}", path, exception);
			return Optional.empty();
		}
	}

}
