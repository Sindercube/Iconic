package com.sindercube.iconic.icon;

import com.sindercube.iconic.textContent.TextContentRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManagerImpl;

public class IconicIcons implements ClientModInitializer {

	public static IconAtlasManager ICON_MANAGER;

	@Override
	public void onInitializeClient() {
		Registry.register(TextContentRegistry.REGISTRY, Iconic.of("icon"), IconTextContent.TYPE);
		ClientLifecycleEvents.CLIENT_STARTED.register(IconicIcons::clientStarted);
//		Sprite star = ICON_MANAGER.getSprite(Iconic.of("star"));
//		System.out.println(star);
	}

	public static void clientStarted(MinecraftClient client) {
		ICON_MANAGER = new IconAtlasManager(client.getTextureManager());
		((ReloadableResourceManagerImpl)client.getResourceManager()).registerReloader(ICON_MANAGER);
	}

}
