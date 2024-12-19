package com.sindercube.iconic.customModel.type.animation.geo.data;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.Map;

public record GeoAnimationData (
	float length,
	LoopMode loopMode,
	Map<String, GeoAnimationHandler> animations
) {

	public static final Codec<GeoAnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.FLOAT.fieldOf("animation_length").forGetter(null),
		LoopMode.CODEC.optionalFieldOf("loop", LoopMode.PLAY_ONCE).forGetter(null),
		Codec.unboundedMap(Codec.STRING, GeoAnimationHandler.CODEC).optionalFieldOf("bones", Map.of()).forGetter(null)
	).apply(instance, GeoAnimationData::new));

	public Animation toAnimation() {
		Animation.Builder builder = Animation.Builder.create(this.length).looping();
		this.animations.forEach((name, animation) -> {
			Keyframe[] keyframes = animation.getKeyframes().toArray(Keyframe[]::new);
			builder.addBoneAnimation(name, new Transformation(Transformation.Targets.ROTATE, keyframes));
		});
		return builder.build();
	}

	public enum LoopMode {

		PLAY_ONCE(Either.right(false)),
		HOLD_ON_LAST_FRAME(Either.left("hold_on_last_frame")),
		LOOP(Either.right(true));

		private final Either<String, Boolean> value;

		LoopMode(Either<String, Boolean> value) {
			this.value = value;
		}

		public static final Codec<LoopMode> CODEC = Codec.either(Codec.STRING, Codec.BOOL)
			.xmap(LoopMode::fromValue, LoopMode::getValue);

		public static LoopMode fromValue(Either<String, Boolean> value) {
			if (value.left().isPresent()) {
				if (value.left().get().equals("hold_on_last_frame")) return HOLD_ON_LAST_FRAME;
			}
			if (value.right().isPresent()) {
				return value.right().get() ? LOOP : PLAY_ONCE;
			}
			return PLAY_ONCE;
		}

		public Either<String, Boolean> getValue() {
			return value;
		}

	}

}
