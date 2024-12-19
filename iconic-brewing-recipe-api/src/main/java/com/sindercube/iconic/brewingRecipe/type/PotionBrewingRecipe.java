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

public record PotionBrewingRecipe(
	BrewingIngredients ingredients,
	RegistryEntry<Potion> result
) implements BrewingRecipe {

	@Override
	public RecipeSerializer<?> getSerializer() {
		return IconicBrewingRecipes.POTION_BREWING_RECIPE_SERIALIZER;
	}

	public static final MapCodec<PotionBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BrewingIngredients.CODEC.forGetter(PotionBrewingRecipe::ingredients),
		Registries.POTION.getEntryCodec().fieldOf("result").forGetter(PotionBrewingRecipe::result)
	).apply(instance, PotionBrewingRecipe::new));

	public static final RecipeSerializer<PotionBrewingRecipe> SERIALIZER = new CodecRecipeSerializer<>(CODEC);

	@Override
	public BrewingIngredients getBrewingIngredients() {
		return this.ingredients;
	}

	@Override
	public ItemStack transform(ItemStack baseStack) {
		PotionContentsComponent component = new PotionContentsComponent(this.result);
		baseStack.set(DataComponentTypes.POTION_CONTENTS, component);
		return baseStack;
	}

}
