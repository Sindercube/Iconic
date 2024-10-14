package com.sindercube.iconic.textContent;

import com.sindercube.iconic.Iconic;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.TextContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextContentRegistry {

	public static void init() {}

	public static final RegistryKey<Registry<TextContent.Type<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(Iconic.of("text_content"));
	public static final Registry<TextContent.Type<?>> REGISTRY = FabricRegistryBuilder.createSimple(REGISTRY_KEY).buildAndRegister();

}
