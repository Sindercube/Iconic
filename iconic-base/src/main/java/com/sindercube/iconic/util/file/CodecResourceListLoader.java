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
import java.util.*;
import java.util.stream.Stream;

public abstract class CodecResourceListLoader<T> implements SimpleSynchronousResourceReloadListener {

	public abstract Codec<T> getCodec();

	public abstract String getStartingPath();

	public abstract  String getFileExtension();

	public boolean matchesExtension(Identifier id) {
		return id.getPath().endsWith(this.getFileExtension());
	};

	protected final List<T> resourceList = new ArrayList<>();

	public T get(int index) {
		return this.resourceList.get(index);
	}

	public Stream<T> getEntries() {
		return this.resourceList.stream();
	}

	@Override
	public void reload(ResourceManager manager) {
		Map<Identifier, Resource> resources = manager.findResources(this.getStartingPath(), this::matchesExtension);
		resources.forEach((fullPath, resource) -> {
			T data = processResource(fullPath, resource);
			resourceList.add(data);
		});
	}

	public T processResource(Identifier path, Resource resource) {
		try {
			BufferedReader reader = resource.getReader();
			JsonElement element = JsonParser.parseReader(reader);
			element = this.preProcessResource(element);
			return this.getCodec().parse(JsonOps.INSTANCE, element).getOrThrow();
		} catch (Exception exception) {
			Iconic.LOGGER.error("Error while processing file '{}'", path);
			throw new RuntimeException(exception);
		}
	}

	public JsonElement preProcessResource(JsonElement element) {
		return element;
	}

}
