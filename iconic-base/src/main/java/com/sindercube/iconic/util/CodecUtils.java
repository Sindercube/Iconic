package com.sindercube.iconic.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class CodecUtils {

	public static <Type> Codec<Type> merge(
			Codec<? extends Type> codec1,
			Codec<? extends Type> codec2
	) {
		return Codec.either(codec1, codec2).flatComapMap(
				either -> {
					if (either.left().isPresent()) return either.left().get();
					if (either.right().isPresent()) return either.right().get();
					return null;
				}, null
		);
	}

	public static <Type> MapCodec<Type> merge(
		MapCodec<? extends Type> codec1,
		MapCodec<? extends Type> codec2
	) {
		return Codec.mapEither(codec1, codec2).xmap(
			either -> {
				if (either.left().isPresent()) return either.left().get();
				if (either.right().isPresent()) return either.right().get();
				return null;
			}, null
		);
	}

}
