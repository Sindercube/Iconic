package com.sindercube.iconic.splashText.types;

import com.mojang.serialization.Codec;
import com.sindercube.iconic.util.CodecUtils;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;

public interface SplashText {

    Codec<SplashText> CODEC = CodecUtils.merge(AdvancedSplashText.CODEC, SimpleSplashText.CODEC);

    int getWeight();
    boolean validate();
    SplashTextRenderer renderer();
    SplashText setStyle(Style style);

}
