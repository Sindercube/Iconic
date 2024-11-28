package com.sindercube.iconic.eml.loader.animation.geo;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.eml.loader.animation.geo.data.GeoAnimationData;
import net.minecraft.client.render.entity.animation.Animation;

import java.util.HashMap;
import java.util.Map;

public record GeoAnimation (
        Map<String, GeoAnimationData> animations
) {

    public static final Codec<GeoAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeoAnimationData.CODEC).fieldOf("animations").forGetter(null)
    ).apply(instance, GeoAnimation::new));

	public static final Codec<Map<String, Animation>> ANIMATION_CODEC = CODEC.flatComapMap(
		GeoAnimation::getAnimationMap,
		null
	);

    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        this.animations.forEach((name, animationData) -> animationMap.put(name, animationData.toAnimation()));
        return animationMap;
    }

}
