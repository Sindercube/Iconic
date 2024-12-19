package com.sindercube.iconic.util.file;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sindercube.iconic.Iconic;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class CodecResourceLoader<T> extends SinglePreparationResourceReloader<Void> implements IdentifiableResourceReloadListener {

	public abstract Codec<T> getCodec();

	public abstract String getStartingPath();

	public abstract  String getFileExtension();

	public boolean matchesExtension(Identifier id) {
		return id.getPath().endsWith(this.getFileExtension());
	};

	protected final Map<Identifier, T> resourceMap = new HashMap<>();

	@Nullable
	public T get(Identifier id) {
		return this.resourceMap.getOrDefault(id, null);
	}

	@Override
	protected Void prepare(ResourceManager manager, Profiler profiler) {
		Map<Identifier, Resource> resources = this.getResources(manager);
		this.loadResources(resources);
		return null;
	}

	public void loadResources(Map<Identifier, Resource> resources) {
		resources.forEach((fullPath, resource) -> {
			Optional<T> data = processResource(fullPath, resource);
			Identifier path = fullPath.withPath(p -> p.split(this.getStartingPath())[1]);
			data.ifPresent(animationData -> resourceMap.put(path, animationData));
		});
	}

	public Map<Identifier, Resource> getResources(ResourceManager manager) {
		return manager.findResources(this.getStartingPath(), this::matchesExtension);
	}

	public Optional<T> processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader);
			element = this.preProcessResource(element);
			return Optional.of(this.getCodec().parse(JsonOps.INSTANCE, element).getOrThrow());
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while processing file '{}': {}", path, exception);
			return Optional.empty();
		}
	}

	public JsonElement preProcessResource(JsonElement element) {
		return element;
	}

	@Override protected void apply(Void prepared, ResourceManager manager, Profiler profiler) {}

}
