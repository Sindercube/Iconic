package com.sindercube.iconic.customModel.type.animation.geo;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.customModel.type.animation.geo.data.GeoAnimationData;
import com.sindercube.iconic.customModel.type.CustomAnimation;
import net.minecraft.client.render.entity.animation.Animation;

import java.util.HashMap;
import java.util.Map;

public record GeoAnimation (
        Map<String, GeoAnimationData> animations
) implements CustomAnimation {

    public static final Codec<GeoAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeoAnimationData.CODEC).fieldOf("animations").forGetter(null)
    ).apply(instance, GeoAnimation::new));

	public static final Type TYPE = new Type() {

		@Override
		public Codec<? extends CustomAnimation> getCodec() {
			return CODEC;
		}

		@Override
		public String getFileExtension() {
			return ".animation.json";
		}

	};

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public Map<String, Animation> getRaw() {
		Map<String, Animation> animationMap = new HashMap<>();
		this.animations.forEach((name, animationData) -> animationMap.put(name, animationData.toAnimation()));
		return animationMap;
	}

}
