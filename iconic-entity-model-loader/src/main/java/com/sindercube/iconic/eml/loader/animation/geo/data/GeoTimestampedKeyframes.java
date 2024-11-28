package com.sindercube.iconic.eml.loader.animation.geo.data;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GeoTimestampedKeyframes(
	Map<Float, GeoTransformation> timestamps
) {

	public static final Codec<GeoTimestampedKeyframes> CODEC = Codec.unboundedMap(Codec.STRING, GeoTransformation.CODEC)
		.flatComapMap(GeoTimestampedKeyframes::remapTimestamps, null)
		.xmap(GeoTimestampedKeyframes::new, GeoTimestampedKeyframes::timestamps);

	public static <T> Map<Float, T> remapTimestamps(Map<String, T> map) {
		Map<Float, T> result = new HashMap<>();
		map.forEach((timestampString, value) -> {
			float timestamp = Float.parseFloat(timestampString);
			result.put(timestamp, value);
		});
		return result;
	}

	public List<Keyframe> getKeyframes(GeoTransformationType type) {
		List<Keyframe> keyframes = new ArrayList<>();
		timestamps.forEach((timestamp, transform) -> {
			Keyframe keyframe = transform.toKeyframe(type, timestamp);
			keyframes.add(keyframe);
		});
		return keyframes;
	}

}
