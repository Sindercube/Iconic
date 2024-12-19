package com.sindercube.iconic.brewingRecipe.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.brewingRecipe.IconicBrewingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface BrewingRecipe extends Recipe<BrewingRecipeInput> {

	List<Ingredient> POSSIBLE_CATALYSTS = new ArrayList<>();

	static boolean isValidCatalyst(ItemStack stack) {
		for (Ingredient catalyst : POSSIBLE_CATALYSTS) {
			if (catalyst.test(stack)) return true;
		}
		return false;
	}

	@Override
	default RecipeType<BrewingRecipe> getType() {
		return IconicBrewingRecipes.BREWING_RECIPE_TYPE;
	}

	@Override
	default boolean matches(BrewingRecipeInput input, World world) {
		boolean ingredientMatches = this.getBrewingIngredients().catalyst.test(input.getIngredient());
		boolean basesMatch = true;
		for (ItemStack baseStack : input.getBases()) {
			if (!this.getBrewingIngredients().base.test(baseStack)) basesMatch = false;
		}
		return ingredientMatches && basesMatch;
	}

	ItemStack transform(ItemStack baseStack);

	BrewingIngredients getBrewingIngredients();

	@Override
	default ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return null;
	}

	@Override
	default ItemStack craft(BrewingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		return null;
	}

	@Override
	default boolean fits(int width, int height) {
		return true;
	}

	record BrewingIngredients (
		Ingredient catalyst,
		Ingredient base
	) {

		public static final Ingredient BASE_POTION = Ingredient.fromTag(IconicBrewingRecipes.POTION_BASES_TAG);

		public static final MapCodec<BrewingIngredients> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("catalyst").forGetter(BrewingIngredients::catalyst),
			Ingredient.DISALLOW_EMPTY_CODEC.optionalFieldOf("base", BASE_POTION).forGetter(BrewingIngredients::catalyst)
		).apply(instance, BrewingIngredients::new));

	}

}
