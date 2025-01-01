package com.sindercube.iconic.customModel.client.registry;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.client.content.VilgerEntityRenderer;
import com.sindercube.iconic.customModel.loader.CustomModelLoader;
import com.sindercube.iconic.customModel.registry.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityRenderers {

	public static final Identifier VILGER_ID = Iconic.of("vilger");

	public static void init() {
		EntityModelLayer layer = new EntityModelLayer(VILGER_ID, "main");
		EntityModelLayerRegistry.registerModelLayer(
			layer,
			() -> CustomModelLoader.getEntity(VILGER_ID)
		);
		EntityRendererRegistry.register(
			ModEntityTypes.VILGER,
			context -> new VilgerEntityRenderer(context, layer, VILGER_ID)
		);
	}

}
