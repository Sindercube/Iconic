package com.sindercube.iconic.ingredientMatching.access;

import com.sindercube.iconic.ingredientMatching.core.ItemCondition;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface ConditionalEntry {

	default List<ItemCondition> getConditions() {
		return new ArrayList<>();
	}

	default boolean test(ItemStack stack) {
		return getConditions().stream()
			.allMatch(condition -> condition.parse(stack));
	}

}
