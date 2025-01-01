package com.sindercube.iconic.customModel.api.content.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public abstract class LoadedEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<S>> extends LivingEntityRenderer<T, S, M> {

	private final Identifier id;

	public LoadedEntityRenderer(EntityRendererFactory.Context context, M entityModel, Identifier id, float shadowRadius) {
		super(context, entityModel, shadowRadius);
		this.id = id;
	}

	@Override
	public Identifier getTexture(S state) {
		return this.id.withPrefixedPath("textures/entity/").withSuffixedPath(".png");
	}

}
