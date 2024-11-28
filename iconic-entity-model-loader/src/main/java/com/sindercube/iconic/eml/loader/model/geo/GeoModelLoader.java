package com.sindercube.iconic.eml.loader.model.geo;

import com.mojang.serialization.Codec;
import com.sindercube.iconic.eml.loader.ModelLoader;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Map;

public class GeoModelLoader implements ModelLoader {

	public static final GeoModelLoader INSTANCE = new GeoModelLoader();

	@Override
	public Codec<TexturedModelData> getCodec() {
		return GeoModel.MODEL_CODEC;
	}

	@Override
	public Map<Identifier, Resource> getResources(ResourceManager manager) {
		return manager.findResources("models", path -> path.getPath().endsWith(".geo.json"));
	}

}
