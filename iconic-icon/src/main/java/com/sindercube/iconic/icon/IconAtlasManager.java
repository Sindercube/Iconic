package com.sindercube.iconic.icon;

import com.sindercube.iconic.Iconic;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

public class IconAtlasManager extends SpriteAtlasHolder {

	public IconAtlasManager(TextureManager manager) {
		super(manager, Iconic.of("textures/atlas/icons.png"), Iconic.of("icons"));
	}

	public Sprite getSprite(Identifier path) {
		return super.getSprite(path);
	}

}
