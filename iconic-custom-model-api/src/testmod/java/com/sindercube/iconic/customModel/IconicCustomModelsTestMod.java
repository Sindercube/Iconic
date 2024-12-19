package com.sindercube.iconic.customModel;

import com.sindercube.iconic.customModel.registry.ModEntityTypes;
import net.fabricmc.api.ModInitializer;

public class IconicCustomModelsTestMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ModEntityTypes.init();
    }

}
