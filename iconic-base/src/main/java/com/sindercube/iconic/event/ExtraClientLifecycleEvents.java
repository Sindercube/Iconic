package com.sindercube.iconic.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

public class ExtraClientLifecycleEvents {

	public static final Event<AfterResourceReload> AFTER_RESOURCE_RELOAD = EventFactory.createArrayBacked(AfterResourceReload.class,
		callbacks -> client -> Arrays.stream(callbacks).forEach(callback -> callback.afterResourcesReloaded(client))
	);

	@FunctionalInterface
	public interface AfterResourceReload {
		void afterResourcesReloaded(MinecraftClient client);
	}

}
