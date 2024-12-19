package com.sindercube.iconic.customModel.loader;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.IconicCustomModels;
import com.sindercube.iconic.customModel.type.CustomAnimation;
import com.sindercube.iconic.customModel.type.CustomModel;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CustomAnimationLoader extends CustomGenericLoader<CustomAnimation.Type, Map<String, Animation>> {

	@Override
	public Registry<CustomModel.Type> getRegistry() {
		return IconicCustomModels.MODEL_TYPE_REGISTRY;
	}

	@Override
	public Identifier getFabricId() {
		return Iconic.of("custom_animations");
	}

	@Override
	public String getStartingPath() {
		return "animations";
	}


	public static CustomAnimationLoader INSTANCE;

	public static Map<String, Animation> getAnimation(Identifier path) {
		return INSTANCE.get(path);
	}

	public static Map<String, Animation> getEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("entity/"));
	}

	public static Map<String, Animation> getBlockEntity(Identifier path) {
		return getAnimation(path.withPrefixedPath("block_entity/"));
	}

	public CustomAnimationLoader() {
		this.resourceMap.clear();
		INSTANCE = this;
	}

}
