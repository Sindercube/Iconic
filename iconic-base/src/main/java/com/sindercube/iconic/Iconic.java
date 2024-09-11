package com.sindercube.iconic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iconic implements ClientModInitializer {

	public static final String MOD_ID = "iconic";
	public static final Logger LOGGER = LoggerFactory.getLogger("Iconic");

	public static Identifier of(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static Identifier defaulted(String path) {
		if (path.contains(":")) return Identifier.of(path);
		return Identifier.of(MOD_ID, path);
	}

	@Override
	public void onInitializeClient() {
		ResourceManagerHelperImpl.registerBuiltinResourcePack(
			of("information_tooltips"),
			"resource_packs/information_tooltips",
			FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
			Text.translatable("resource_pack.iconic.information_tooltips.name"),
			ResourcePackActivationType.NORMAL
		);
		LOGGER.info("Initialized!");
	}


	public static boolean isModLoaded(String mod) {
		return FabricLoader.getInstance().isModLoaded(mod);
	}

}
