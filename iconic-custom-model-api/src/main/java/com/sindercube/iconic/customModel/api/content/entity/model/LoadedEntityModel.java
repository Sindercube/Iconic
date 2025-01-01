package com.sindercube.iconic.customModel.api.content.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;

public abstract class LoadedEntityModel<T extends EntityRenderState> extends EntityModel<T> {

	public LoadedEntityModel(ModelPart root) {
		super(root);
	}

}
