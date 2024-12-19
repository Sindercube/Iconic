package com.sindercube.iconic.customModel.api.content.entity.renderer;

import com.sindercube.iconic.customModel.api.content.entity.model.LoadedEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public abstract class LoadedEntityRenderer<T extends LivingEntity, L extends LoadedEntityModel<T>> extends LivingEntityRenderer<T, L> {

	public LoadedEntityRenderer(EntityRendererFactory.Context context, L entityModel, float shadowRadius) {
		super(context, entityModel, shadowRadius);
	}

	@Override
	public Identifier getTexture(T entity) {
		return this.model.getId().withPrefixedPath("textures/entity/").withSuffixedPath(".png");
	}

}
