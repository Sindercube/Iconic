package com.sindercube.iconic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IconicClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
//		ResourceManagerHelperImpl.registerBuiltinResourcePack(
//			Iconic.of("information_tooltips"),
//			"resource_packs/information_tooltips",
//			FabricLoader.getInstance().getModContainer(Iconic.MOD_ID).orElseThrow(),
//			Text.translatable("resource_pack.iconic.information_tooltips.name"),
//			ResourcePackActivationType.NORMAL
//		);
	}

}
