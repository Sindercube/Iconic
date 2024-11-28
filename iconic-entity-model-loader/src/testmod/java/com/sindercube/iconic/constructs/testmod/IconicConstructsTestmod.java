package com.sindercube.iconic.constructs.testmod;

import com.sindercube.iconic.constructs.testmod.registry.ModEntityTypes;
import net.fabricmc.api.ModInitializer;

public class IconicConstructsTestmod implements ModInitializer {

    @Override
    public void onInitialize() {
        ModEntityTypes.init();
    }

}
