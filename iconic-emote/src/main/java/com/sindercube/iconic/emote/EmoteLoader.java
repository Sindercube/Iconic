package com.sindercube.iconic.emote;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.sindercube.iconic.Iconic.LOGGER;

public class EmoteLoader implements SimpleSynchronousResourceReloadListener {

	public static final Pattern EMOTE_PATTERN = Pattern.compile(":(\\w+?):");

	public static final EmoteLoader INSTANCE = new EmoteLoader();
    public static final Map<String, Text> EMOTES = new HashMap<>();

    public static final Codec<Map<String, Text>> CODEC = Codec.unboundedMap(Codec.STRING, TextCodecs.CODEC);

    @Override
    public Identifier getFabricId() {
        return Iconic.of("emotes");
    }

    public static final String EMOTES_PATH = "emotes.json";

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> reader = manager.findResources("texts", p -> p.getPath().endsWith("/" + EMOTES_PATH));

        for (Identifier identifier : reader.keySet()) {
            BufferedReader fileReader;
            try {
                fileReader = reader.get(identifier).getReader();
            } catch (IOException e) {
                continue;
            }
            try {
                JsonObject map = JsonParser.parseReader(fileReader).getAsJsonObject();
                Map<String, Text> emotes = CODEC.parse(JsonOps.INSTANCE, map).getOrThrow();
                EMOTES.putAll(emotes);
            } catch (ClassCastException exception) {
                LOGGER.error("Unable to load emote file '{}': {}", identifier, exception);
            }
        }
    }

	public static Text get(String key) {
		return EMOTES.get(key);
	}

	public static List<String> getSuggestions() {
		List<String> suggestions = new ArrayList<>();
		for (String key : EMOTES.keySet()) {
			Text value = EMOTES.get(key);
			suggestions.add(":" + key + ": " + value.getString());
		}
		return suggestions;
	}

}
