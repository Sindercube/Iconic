package com.sindercube.iconic.eml.util;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Transformation;

public class InterpolationUtil {

	public static final Codec<Transformation.Interpolation> CODEC = Codec.STRING.flatComapMap(
		InterpolationUtil::interpolationFromString,
		null
	);

	public static Transformation.Interpolation interpolationFromString(String value) {
		return switch (value) {
			case "catmullrom" -> Transformation.Interpolations.CUBIC;
			default -> Transformation.Interpolations.LINEAR;
		};
	}

}
