package com.sindercube.iconic.brewingRecipe.type;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.stream.Stream;

public record BrewingRecipeInput (
	DefaultedList<ItemStack> inventory
) implements RecipeInput {

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.get(slot);
	}

	public ItemStack getIngredient() {
		return inventory.get(3);
	}

	public List<ItemStack> getBases() {
		return inventory.subList(0, 2);
	}

	@Override
	public int getSize() {
		return inventory.size();
	}

}
