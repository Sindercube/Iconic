package com.sindercube.iconic.format;

import com.sindercube.iconic.textContent.TextContentRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class IconicFormats implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(FormatLoader.INSTANCE);
		TextContentRegistry.register(FormatTextContent.TYPE);
	}

}
