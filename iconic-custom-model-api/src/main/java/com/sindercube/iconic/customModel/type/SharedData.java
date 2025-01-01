package com.sindercube.iconic.customModel.type;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;

public interface SharedData<T> {

	Type getType();
	T getRaw();

	interface Type {

		Codec<? extends SharedData<?>> getCodec();
		String getFileExtension();

		default JsonElement preProcessResource(JsonElement element) {
			return element;
		}

		default boolean matchesExtension(Identifier id) {
			return id.getPath().endsWith(this.getFileExtension());
		}

	}
}
