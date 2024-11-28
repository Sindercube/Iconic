package com.sindercube.iconic.eml.loader.animation.geo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import com.sindercube.iconic.eml.util.InterpolationUtil;
import com.sindercube.iconic.util.CodecUtils;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.Map;

public record GeoTransformation(
	Vec3d pre,
	Vec3d post,
	Transformation.Interpolation interpolation
) {

	public static final Codec<GeoTransformation> SIMPLE_CODEC = Vec3d.CODEC
		.xmap(GeoTransformation::simple, GeoTransformation::getValue);

	public static final Codec<GeoTransformation> ADVANCED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Vec3d.CODEC.fieldOf("pre").forGetter(GeoTransformation::pre),
		Vec3d.CODEC.fieldOf("post").forGetter(GeoTransformation::post),
		InterpolationUtil.CODEC.optionalFieldOf("lerp_mode", Transformation.Interpolations.LINEAR).forGetter(GeoTransformation::interpolation)
	).apply(instance, GeoTransformation::new));

	public static final Codec<GeoTransformation> CODEC = CodecUtils.merge(SIMPLE_CODEC, ADVANCED_CODEC);

	public static final UnboundedMapCodec<Float, GeoTransformation> TIMESTAMPED_CODEC = Codec.unboundedMap(Codec.FLOAT, CODEC);

	public static final UnboundedMapCodec<GeoTransformationType, Map<Float, GeoTransformation>> MAP_CODEC =
		Codec.unboundedMap(GeoTransformationType.CODEC, TIMESTAMPED_CODEC);

	public static GeoTransformation simple(Vec3d vec) {
		return new GeoTransformation(vec, vec, Transformation.Interpolations.LINEAR);
	}

	public Vec3d getValue() {
		return pre;
	}

	public Keyframe toKeyframe(GeoTransformationType type, float timestamp) {
		Vector3f vec3f = type.updateVector(post);
		return new Keyframe(timestamp, vec3f, interpolation);
	}

}
