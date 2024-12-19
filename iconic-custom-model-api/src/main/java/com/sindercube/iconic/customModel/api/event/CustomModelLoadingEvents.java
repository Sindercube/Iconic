package com.sindercube.iconic.customModel.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Arrays;

public class CustomModelLoadingEvents {

	public static final Event<AfterModelsLoaded> AFTER_MODELS_LOADED = EventFactory.createArrayBacked(AfterModelsLoaded.class,
		callbacks -> () -> Arrays.stream(callbacks).forEach(AfterModelsLoaded::afterModelsLoaded)
	);

	@FunctionalInterface
	public interface AfterModelsLoaded {
		void afterModelsLoaded();
	}

}
