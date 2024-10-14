package com.sindercube.iconic.format;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.textContent.TextContentRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;

public class IconicFormats implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(FormatLoader.INSTANCE);
		Registry.register(TextContentRegistry.REGISTRY, Iconic.of("format"), FormatTextContent.TYPE);
	}

}
