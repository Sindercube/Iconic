package com.sindercube.iconic.customModel.type.animation.geo.data;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.function.Function;

public enum GeoTransformationType implements StringIdentifiable {

	POSITION("position", GeoTransformationType::createTranslationalVector),
	ROTATION("rotation", GeoTransformationType::createRotationalVector),
	SCALE("scale", GeoTransformationType::createScalingVector);

	public static final Codec<GeoTransformationType> CODEC = StringIdentifiable.createCodec(GeoTransformationType::values);

	public static Vector3f createTranslationalVector(Vec3d vec) {
		return AnimationHelper.createTranslationalVector((float)vec.x, (float)vec.y, (float)vec.z);
	}

	public static Vector3f createRotationalVector(Vec3d vec) {
		return AnimationHelper.createRotationalVector((float)vec.x, (float)vec.y, (float)vec.z);
	}

	public static Vector3f createScalingVector(Vec3d vec) {
		return AnimationHelper.createScalingVector((float)vec.x, (float)vec.y, (float)vec.z);
	}

	private final String id;
	private final Function<Vec3d, Vector3f> updateVectorFunction;

	GeoTransformationType(String id, Function<Vec3d, Vector3f> function) {
		this.id = id;
		this.updateVectorFunction = function;
	}

	public Vector3f updateVector(Vec3d vec) {
		return updateVectorFunction.apply(vec);
	}

	@Override
	public String asString() {
		return this.id;
	}

}
