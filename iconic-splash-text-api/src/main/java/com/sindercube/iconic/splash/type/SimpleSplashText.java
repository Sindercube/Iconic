package com.sindercube.iconic.splash.type;

import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class SimpleSplashText implements SplashText {

    private final String text;

    public SimpleSplashText(String text) {
        this.text = text;
    }

    public static final Codec<SimpleSplashText> CODEC = Codec.STRING.xmap(SimpleSplashText::new, null);

    @Override
    public SplashTextRenderer getRenderer() {
        return new SplashTextRenderer(this.text);
    }

    @Override
    public SplashText setStyle(Style style) {
        return new AdvancedSplashText(Text.literal(text)).setStyle(style);
    }

    @Override
    public String toString() {
        return "SimpleSplashText["+this.text+"]";
    }

}
