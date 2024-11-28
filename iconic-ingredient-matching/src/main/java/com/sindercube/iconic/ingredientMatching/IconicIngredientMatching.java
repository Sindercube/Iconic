package com.sindercube.iconic.ingredientMatching;

import com.sindercube.obscure.ingredientMatching.core.ItemConditionRegistry;
import net.fabricmc.api.ModInitializer;

public class IconicIngredientMatching implements ModInitializer {

    @Override
    public void onInitialize() {
		ItemConditionRegistry.init();
    }

}
