package com.sindercube.iconic.customModel.util;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface EarlyResourceReloadListener extends SimpleResourceReloadListener<Void> {

    void reload(ResourceManager manager);

    @Override
    default CompletableFuture<Void> load(ResourceManager manager, Executor executor) {
        reload(manager);
        return CompletableFuture.supplyAsync(() -> null, executor);
    }

    @Override
    default CompletableFuture<Void> apply(Void data, ResourceManager manager, Executor executor) {
        return CompletableFuture.runAsync(() -> {}, executor);
    }

}
