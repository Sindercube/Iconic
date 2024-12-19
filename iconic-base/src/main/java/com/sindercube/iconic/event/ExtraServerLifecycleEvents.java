package com.sindercube.iconic.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

import java.util.Arrays;

public class ExtraServerLifecycleEvents {

	public static final Event<AfterResourceReload> AFTER_RESOURCE_RELOAD = EventFactory.createArrayBacked(AfterResourceReload.class,
		callbacks -> server -> Arrays.stream(callbacks).forEach(callback -> callback.afterResourcesReloaded(server))
	);

	public static final Event<BeforeStart> BEFORE_START = EventFactory.createArrayBacked(BeforeStart.class,
		callbacks -> server -> Arrays.stream(callbacks).forEach(callback -> callback.beforeStart(server))
	);

	@FunctionalInterface
	public interface AfterResourceReload {
		void afterResourcesReloaded(MinecraftServer server);
	}

	@FunctionalInterface
	public interface BeforeStart {
		void beforeStart(MinecraftServer server);
	}

}
