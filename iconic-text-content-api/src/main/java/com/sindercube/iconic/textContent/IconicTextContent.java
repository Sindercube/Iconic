package com.sindercube.iconic.textContent;

import com.sindercube.iconic.Iconic;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.TextContent;

public class IconicTextContent implements ClientModInitializer {

	public static final RegistryKey<Registry<TextContent.Type<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(Iconic.of("text_content"));
	public static final Registry<TextContent.Type<?>> REGISTRY = FabricRegistryBuilder.createSimple(REGISTRY_KEY).buildAndRegister();

	@Override
	public void onInitializeClient() {}

}
