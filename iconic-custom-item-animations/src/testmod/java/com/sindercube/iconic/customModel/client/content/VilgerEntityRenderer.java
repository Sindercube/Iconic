package com.sindercube.iconic.customModel.client.content;

import com.sindercube.iconic.customModel.content.VilgerEntity;
import com.sindercube.iconic.customModel.api.content.entity.renderer.LoadedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.util.Identifier;

public class VilgerEntityRenderer extends LoadedEntityRenderer<VilgerEntity, VillagerEntityRenderState, VilgerEntityModel> {

    public VilgerEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer, Identifier id) {
        super(context, new VilgerEntityModel(context.getPart(layer)), id, 0.5f);
    }

	@Override
	public VillagerEntityRenderState createRenderState() {
		return new VillagerEntityRenderState();
	}

}
