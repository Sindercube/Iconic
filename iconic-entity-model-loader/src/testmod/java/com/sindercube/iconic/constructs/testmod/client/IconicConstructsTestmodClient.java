package com.sindercube.iconic.constructs.testmod.client;

import com.sindercube.iconic.constructs.testmod.client.registry.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class IconicConstructsTestmodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
		ModEntityRenderers.init();
    }

}
