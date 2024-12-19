package com.sindercube.iconic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iconic implements ModInitializer {

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
	public void onInitialize() {}

	public static boolean isModLoaded(String mod) {
		return FabricLoader.getInstance().isModLoaded(mod);
	}

}
