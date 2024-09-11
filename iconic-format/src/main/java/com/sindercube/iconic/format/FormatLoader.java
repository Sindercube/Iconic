package com.sindercube.iconic.format;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sindercube.iconic.Iconic.LOGGER;

public class FormatLoader implements SimpleSynchronousResourceReloadListener {

    public static final FormatLoader INSTANCE = new FormatLoader();
	public static final Map<String, Text> FORMATS = new HashMap<>();

	public static final Codec<Map<String, Text>> CODEC = Codec.unboundedMap(Codec.STRING, TextCodecs.CODEC);

	@Override
    public Identifier getFabricId() {
        return Iconic.of("formats");
    }

	public static final String FORMATS_PATH = "formats.json";

	@Override
    public void reload(ResourceManager manager) {
		Map<Identifier, Resource> reader = manager.findResources("texts", p -> p.getPath().endsWith("/" + FORMATS_PATH));

		for (Identifier identifier : reader.keySet()) {
			BufferedReader fileReader;
			try {
				fileReader = reader.get(identifier).getReader();
			} catch (IOException e) {
				continue;
			}
			try {
				JsonObject map = JsonParser.parseReader(fileReader).getAsJsonObject();
				Map<String, Text> formats = CODEC.parse(JsonOps.INSTANCE, map).getOrThrow();
				FORMATS.putAll(formats);
			} catch (ClassCastException exception) {
				LOGGER.error("Unable to load emote file '{}': {}", identifier, exception);
			}
		}
    }
}
