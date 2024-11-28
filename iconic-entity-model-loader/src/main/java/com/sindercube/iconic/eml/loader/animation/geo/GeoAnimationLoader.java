package com.sindercube.iconic.eml.loader.animation.geo;

import com.mojang.serialization.Codec;
import com.sindercube.iconic.eml.loader.AnimationLoader;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Map;

public class GeoAnimationLoader implements AnimationLoader {

	public static final GeoAnimationLoader INSTANCE = new GeoAnimationLoader();

	@Override
	public Codec<Map<String, Animation>> getCodec() {
		return GeoAnimation.ANIMATION_CODEC;
	}

	@Override
	public Map<Identifier, Resource> getResources(ResourceManager manager) {
		return manager.findResources("animations", path -> path.getPath().endsWith(".animation.json"));
	}

}
