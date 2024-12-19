package com.sindercube.iconic.util.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public record IdentifierRecipeType<T extends Recipe<?>> (
	Identifier id
) implements RecipeType<T> {

	public String toString() {
		return id.toString();
	}

}
