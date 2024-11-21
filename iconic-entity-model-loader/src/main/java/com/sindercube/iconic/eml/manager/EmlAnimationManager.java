package com.sindercube.iconic.eml.manager;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.IconicEntityModelLoader;
import com.sindercube.iconic.eml.utils.EarlyResourceReloadListener;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class EmlAnimationManager extends EarlyResourceReloadListener {

	@Override
	public Identifier getFabricId() {
		return Iconic.of("animations");
	}

	public static EmlAnimationManager INSTANCE;

	public static Map<String, Animation> getAnimation(Identifier path) {
		return INSTANCE.get(path);
	}

	public static Map<String, Animation> getEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("entity/"));
	}

	public static Map<String, Animation> getBlockEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("block_entity/"));
	}


	public static final Map<String, Animation> MISSING_ANIMATION_MAP = null;
	private final Map<Identifier, Map<String, Animation>> animationMaps;

	public EmlAnimationManager() {
		this.animationMaps = new HashMap<>();
		INSTANCE = this;
	}


	public Map<String, Animation> get(Identifier path) {
		return this.animationMaps.getOrDefault(path, MISSING_ANIMATION_MAP);
	}

	@Override
	public void reload(ResourceManager manager) {
		IconicEntityModelLoader.ANIMATION_LOADER_REGISTRY.stream()
			.map(loader -> loader.processResource(manager))
			.forEach(animationMaps::putAll);
	}

}
