package com.sindercube.iconic.customModel.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.type.CustomModel;
import com.sindercube.iconic.customModel.type.SharedData;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.registry.Registry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericLoaderOld<B extends SharedData.Type, T> extends SinglePreparationResourceReloader<Void> implements IdentifiableResourceReloadListener {

	public abstract Registry<B> getRegistry();

	public abstract String getStartingPath();

	protected final Map<Identifier, T> resourceMap = new HashMap<>();

	@Nullable
	public T get(Identifier id) {
		return this.resourceMap.getOrDefault(id, null);
	}

	@Override
	public Void prepare(ResourceManager manager, Profiler profiler) {
		this.getRegistry().forEach(type -> {
			Map<Identifier, Resource> resources = manager.findResources(this.getStartingPath(), type::matchesExtension);
			this.loadResources(resources, type);
		});
		return null;
	}

	public void loadResources(Map<Identifier, Resource> resources, CustomModel.Type type) {
		resources.forEach((fullPath, resource) -> {
			T data = this.processResource(fullPath, resource, type);
			Identifier path = fullPath.withPath(p ->
				p.split(this.getStartingPath() + "/")[1].split(type.getFileExtension())[0]
			);
			this.resourceMap.put(path, data);
		});
	}

	@SuppressWarnings("unchecked")
	public T processResource(Identifier path, Resource resource, CustomModel.Type type) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader);
			element = type.preProcessResource(element);
			return (T) type.getCodec().parse(JsonOps.INSTANCE, element).getOrThrow().getRaw();
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while processing file '{}': {}", path, exception);
			throw new RuntimeException(exception);
		}
	}

	@Override protected void apply(Void prepared, ResourceManager manager, Profiler profiler) {}

}
