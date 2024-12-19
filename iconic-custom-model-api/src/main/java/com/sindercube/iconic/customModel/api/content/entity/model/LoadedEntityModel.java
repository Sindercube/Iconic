package com.sindercube.iconic.customModel.api.content.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public abstract class LoadedEntityModel<T extends Entity> extends SinglePartEntityModel<T> {

	protected ModelPart root;
	protected Identifier id;

	public LoadedEntityModel(ModelPart root, Identifier id) {
		this.root = root;
		this.id = id;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	public Identifier getId() {
		return id;
	}

}
