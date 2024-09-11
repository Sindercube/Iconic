package com.sindercube.iconic.splashText.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.splashText.util.IconicSplashTextRenderer;
import com.sindercube.iconic.splashText.util.TextProcessor;
import com.sindercube.iconic.util.DateUtils;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.dynamic.Codecs;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class AdvancedSplashText implements SplashText {

    private MutableText rawText;
    private final int weight;
    private final String mod;
    private final LocalDate date;

    public AdvancedSplashText(
            Text text,
            Integer weight,
            String mod,
            LocalDate date
    ) {
        this.rawText = TextProcessor.process(text);
        this.weight = weight;
        this.mod = mod;
        this.date = date;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean validate() {
        if (!DateUtils.isToday(this.date)) return false;
        if (!Iconic.isModLoaded(this.mod)) return false;
        return true;
    }

    public AdvancedSplashText(MutableText text) {
        this(text, 1, "minecraft", DateUtils.today());
    }

    public void modifyText(UnaryOperator<MutableText> operator) {
        this.rawText = operator.apply(this.rawText);
    }

    public static final Codec<AdvancedSplashText> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextCodecs.CODEC.fieldOf("raw").forGetter(null),
            Codecs.rangedInt(1, 128).optionalFieldOf("weight", 1).forGetter(null),
            Codec.STRING.optionalFieldOf("mod", "minecraft").forGetter(null),
            DateUtils.CODEC.optionalFieldOf("date", DateUtils.today()).forGetter(null)
    ).apply(instance, AdvancedSplashText::new));

    @Override
    public SplashTextRenderer renderer() {
        return new IconicSplashTextRenderer(this.rawText);
    }

    @Override
    public SplashText setStyle(Style style) {
        modifyText(text -> text.fillStyle(style));
        return this;
    }

    @Override
    public String toString() {
        return "AdvancedSplashText["+this.rawText.getString()+"]";
    }

}
