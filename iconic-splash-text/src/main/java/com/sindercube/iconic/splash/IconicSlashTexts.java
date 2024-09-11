package com.sindercube.iconic.splash;

import com.sindercube.iconic.splashText.SplashTextLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class IconicSlashTexts implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(SplashTextLoader.INSTANCE);
	}

}
