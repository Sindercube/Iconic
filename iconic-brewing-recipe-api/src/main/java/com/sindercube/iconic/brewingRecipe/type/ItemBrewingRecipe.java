package com.sindercube.iconic.brewingRecipe.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.brewingRecipe.IconicBrewingRecipes;
import com.sindercube.iconic.util.recipe.CodecRecipeSerializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

public record ItemBrewingRecipe(
	BrewingIngredients ingredients,
	ItemStack result
) implements BrewingRecipe {

	@Override
	public RecipeSerializer<?> getSerializer() {
		return IconicBrewingRecipes.ITEM_BREWING_RECIPE_SERIALIZER;
	}

	public static final MapCodec<ItemBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BrewingIngredients.CODEC.forGetter(ItemBrewingRecipe::ingredients),
		ItemStack.CODEC.fieldOf("result").forGetter(ItemBrewingRecipe::result)
	).apply(instance, ItemBrewingRecipe::new));

	public static final RecipeSerializer<ItemBrewingRecipe> SERIALIZER = new CodecRecipeSerializer<>(CODEC);

	@Override
	public BrewingIngredients getBrewingIngredients() {
		return this.ingredients;
	}

	@Override
	public ItemStack transform(ItemStack baseStack) {
		return result;
	}

}
