package com.sindercube.iconic.eml.manager;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.eml.IconicEntityModelLoader;
import com.sindercube.iconic.eml.util.EarlyResourceReloadListener;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CustomModelManager extends EarlyResourceReloadListener {

	@Override
	public Identifier getFabricId() {
		return Iconic.of("models");
	}

	public static CustomModelManager INSTANCE;

	public static TexturedModelData getModel(Identifier path) {
		return INSTANCE.get(path);
	}

	public static TexturedModelData getEntity(Identifier path) {
		return getModel(path.withPrefixedPath("entity/"));
	}

	public static TexturedModelData getBlockEntity(Identifier path) {
		return getModel(path.withPrefixedPath("block_entity/"));
	}


	public static final TexturedModelData MISSING_MODEL = null;
	private final Map<Identifier, TexturedModelData> models;

	public CustomModelManager() {
		this.models = new HashMap<>();
		INSTANCE = this;
	}


	public TexturedModelData get(Identifier path) {
		return this.models.getOrDefault(path, MISSING_MODEL);
	}

	@Override
	public void reload(ResourceManager manager) {
		IconicEntityModelLoader.MODEL_LOADER_REGISTRY.stream()
			.map(loader -> loader.processResource(manager))
			.forEach(models::putAll);
	}

}
