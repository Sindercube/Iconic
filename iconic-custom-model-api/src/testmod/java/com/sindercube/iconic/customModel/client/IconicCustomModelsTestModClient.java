package com.sindercube.iconic.customModel.client;

import com.sindercube.iconic.customModel.api.event.CustomModelLoadingEvents;
import com.sindercube.iconic.customModel.client.registry.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class IconicCustomModelsTestModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
		CustomModelLoadingEvents.AFTER_MODELS_LOADED.register(ModEntityRenderers::init);
    }

}
