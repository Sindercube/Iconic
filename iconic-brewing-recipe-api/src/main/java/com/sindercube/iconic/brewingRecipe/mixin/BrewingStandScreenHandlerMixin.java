package com.sindercube.iconic.brewingRecipe.mixin;

import com.sindercube.iconic.brewingRecipe.type.BrewingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BrewingStandScreenHandler.class)
public class BrewingStandScreenHandlerMixin {

	@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$IngredientSlot")
	public static class IngredientSlotMixin {

		/**
		 * @author Sindercube
		 * @reason Get from custom valid ingredient list
		 */
		@Overwrite
		public boolean canInsert(ItemStack stack) {
			return BrewingRecipe.isValidCatalyst(stack);
		}

	}

}
