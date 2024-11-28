package com.sindercube.iconic.constructs.testmod.client.registry;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.constructs.testmod.client.content.VilgerEntityModel;
import com.sindercube.iconic.constructs.testmod.client.content.VilgerEntityRenderer;
import com.sindercube.iconic.constructs.testmod.registry.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityRenderers {

	public static final Identifier VILGER_ID = Iconic.of("vilger");
	public static final EntityModelLayer VILGER_LAYER = new EntityModelLayer(VILGER_ID, "main");

	public static void init() {
		EntityModelLayerRegistry.registerModelLayer(
			VILGER_LAYER,
			() -> VilgerEntityModel.getModelData(VILGER_ID)
		);
		EntityRendererRegistry.register(
			ModEntityTypes.VILGER,
			context -> new VilgerEntityRenderer(context, VILGER_LAYER, VILGER_ID)
		);
	}

}
