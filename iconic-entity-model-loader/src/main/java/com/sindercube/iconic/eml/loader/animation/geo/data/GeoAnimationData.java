package com.sindercube.iconic.eml.loader.animation.geo.data;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.loader.animation.geo.GeoAnimation;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record GeoAnimationData(
        Map<String, GeoAnimation> animations
) {

    @Nullable
    public static GeoAnimationData fromJson(Identifier path, JsonObject object) {
        try {
            return CODEC.parse(JsonOps.INSTANCE, object).getOrThrow();
        } catch (Exception exception) {
            Iconic.LOGGER.error("Failed to load Geo Animation '{}': {}", path, exception);
            return null;
        }
    }

    public static final Codec<GeoAnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeoAnimation.CODEC).fieldOf("animations").forGetter(null)
    ).apply(instance, GeoAnimationData::new));

    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        this.animations.forEach((name, animationData) -> animationMap.put(name, animationData.toAnimation()));
        return animationMap;
    }

}
