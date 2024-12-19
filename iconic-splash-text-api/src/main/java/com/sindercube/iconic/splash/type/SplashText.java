package com.sindercube.iconic.splash.type;

import com.mojang.serialization.Codec;
import com.sindercube.iconic.util.CodecUtils;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;

public interface SplashText {

    Codec<SplashText> CODEC = CodecUtils.merge(AdvancedSplashText.CODEC, SimpleSplashText.CODEC);

    SplashTextRenderer getRenderer();

    SplashText setStyle(Style style);

	default int getWeight() {
		return 1;
	}

	default boolean validate() {
		return true;
	}

}
