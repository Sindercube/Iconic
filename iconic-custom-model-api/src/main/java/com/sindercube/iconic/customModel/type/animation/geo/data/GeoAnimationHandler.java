package com.sindercube.iconic.customModel.type.animation.geo.data;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.*;

public record GeoAnimationHandler (
	Map<GeoTransformationType, GeoTimestampedKeyframes> timestampedTransformations
) {

	public static final Codec<GeoAnimationHandler> CODEC =
		Codec.unboundedMap(GeoTransformationType.CODEC, GeoTimestampedKeyframes.CODEC)
			.xmap(GeoAnimationHandler::new, GeoAnimationHandler::timestampedTransformations);

    public List<Keyframe> getKeyframes() {
        List<Keyframe> result = new ArrayList<>();
		for (Map.Entry<GeoTransformationType, GeoTimestampedKeyframes> ttEntry : timestampedTransformations.entrySet()) {
			GeoTransformationType type = ttEntry.getKey();
			GeoTimestampedKeyframes keyframes = ttEntry.getValue();
			result.addAll(keyframes.getKeyframes(type));
		}
        return result;
    }

}
