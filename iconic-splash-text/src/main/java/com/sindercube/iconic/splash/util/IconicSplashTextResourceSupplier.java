package com.sindercube.iconic.splashText.util;

import com.google.common.collect.Iterables;
import com.sindercube.iconic.splashText.SplashTextLoader;
import com.sindercube.iconic.splashText.types.SimpleSplashText;
import com.sindercube.iconic.splashText.types.SplashText;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.session.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IconicSplashTextResourceSupplier extends SplashTextResourceSupplier {

    public IconicSplashTextResourceSupplier(Session session) {
        super(session);
    }

    public SplashText getRandomSplash() {

        List<SplashText> splashes = SplashTextLoader.INSTANCE.getSplashTexts();
        if (splashes.isEmpty()) return new SimpleSplashText("");

        Integer poolWeight = SplashTextLoader.INSTANCE.getMaxWeight();
        int weightMul = (splashes.size() / 100) + 1;
        Collections.shuffle(splashes);
        Iterator<SplashText> iterator = Iterables.cycle(splashes).iterator();

        while (true) {
            SplashText splash = iterator.next();
            poolWeight -= splash.getWeight() * weightMul;
            if (poolWeight > 0) continue;
            return splash;
        }

    }

    @Override
    public SplashTextRenderer get() {
        return getRandomSplash().renderer();
    }

}
