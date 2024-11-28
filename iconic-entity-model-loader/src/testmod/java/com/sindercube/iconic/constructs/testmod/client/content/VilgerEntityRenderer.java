package com.sindercube.iconic.constructs.testmod.client.content;

import com.sindercube.iconic.constructs.testmod.content.VilgerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VilgerEntityRenderer extends LoadedEntityRenderer<VilgerEntity, VilgerEntityModel> {

    public VilgerEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer modelLayer, Identifier id) {
        super(context, new VilgerEntityModel(context.getPart(modelLayer), id), 0.5f);
    }

}
