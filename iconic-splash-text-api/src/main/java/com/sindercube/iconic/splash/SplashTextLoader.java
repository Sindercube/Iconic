package com.sindercube.iconic.splash;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.splash.type.SimpleSplashText;
import com.sindercube.iconic.splash.type.SplashText;
import com.sindercube.iconic.splash.type.SplashTextData;
import com.sindercube.iconic.util.file.SimpleFileLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SplashTextLoader implements SimpleFileLoader {

    @Override
    public Identifier getFabricId() {
        return Iconic.of("splashes");
    }

    public static final SplashTextLoader INSTANCE = new SplashTextLoader();

    private SplashTextLoader() {}

    private final List<SplashText> splashTexts = new ArrayList<>();
    private Integer maxWeight = 0;

    public List<SplashText> getSplashTexts() {
        return splashTexts;
    }
    public Integer getMaxWeight() {
        return maxWeight;
    }



    private static final String NEW_SPLASHES_FILE = "values/splashes.json";
    private static final Identifier OLD_SPLASHES_FILE = Identifier.of("minecraft", "values/splashes.txt");

    @Override
    public void init(SimpleFileLoader.DataFileLoader loader, ResourceManager manager) {
        loader.json().find(NEW_SPLASHES_FILE, this::addSplashes);
        loader.raw().get(OLD_SPLASHES_FILE, this::addVanillaSplashes);
    }

    public void addSplashes(Identifier identifier, JsonElement element) {
        if (!element.isJsonObject()) {
            Iconic.LOGGER.error("Unable to load splash file: '{}', file must be an object containing splash text data", identifier);
            return;
        }
        JsonObject object = element.getAsJsonObject();
        SplashTextData.fromJson(object).getTexts()
            .filter(SplashText::validate)
            .forEach(this::addSplash);
    }

    public void addSplash(SplashText splash) {
        this.maxWeight += splash.getWeight();
        this.splashTexts.add(splash);
		System.out.println(this.splashTexts);
    }

    public void addVanillaSplashes(Stream<String> stream) {
        stream.map(SimpleSplashText::new).forEach(this::addSplash);
    }

}
