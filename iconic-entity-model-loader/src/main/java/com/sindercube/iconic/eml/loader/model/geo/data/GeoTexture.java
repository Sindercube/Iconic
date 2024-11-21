package com.sindercube.iconic.eml.loader.model.geo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GeoTexture (
        int width,
        int height
) {

    public static final Codec<GeoTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("texture_width").forGetter(GeoTexture::width),
            Codec.INT.fieldOf("texture_height").forGetter(GeoTexture::height)
    ).apply(instance, GeoTexture::new));

}
