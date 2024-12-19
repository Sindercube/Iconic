package com.sindercube.iconic.brewingRecipe.mixin;

import com.sindercube.iconic.brewingRecipe.IconicBrewingRecipes;
import com.sindercube.iconic.brewingRecipe.type.BrewingRecipe;
import com.sindercube.iconic.brewingRecipe.type.BrewingRecipeInput;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin extends LockableContainerBlockEntity {

	@Shadow private DefaultedList<ItemStack> inventory;
	@Shadow int brewTime;
	@Shadow private boolean[] slotsEmptyLastTick;
	@Shadow private Item itemBrewing;
	@Shadow int fuel;

	@Shadow protected abstract boolean[] getSlotsEmpty();

	@Unique private final RecipeManager.MatchGetter<BrewingRecipeInput, BrewingRecipe> matchGetter = RecipeManager
		.createCachedMatchGetter(IconicBrewingRecipes.BREWING_RECIPE_TYPE);

	protected BrewingStandBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	/**
	 * @author Sindercube
	 * @reason Overhaul brewing recipe handling
	 */
	@Overwrite
	public static void tick(World world, BlockPos pos, BlockState state, BrewingStandBlockEntity entity) {
		((BrewingStandBlockEntityMixin)(Object)entity).tick(world, pos, state);
	}

	@Unique
	public void tick(World world, BlockPos pos, BlockState state) {
		this.refuel(world, pos, state);

		ItemStack catalystStack = this.inventory.get(3);
		if (catalystStack.isEmpty()) return;

		BrewingRecipeInput input = new BrewingRecipeInput(this.inventory);
		Optional<RecipeEntry<BrewingRecipe>> maybeRecipe = matchGetter.getFirstMatch(input, this.world);
		if (maybeRecipe.isEmpty()) return;
		BrewingRecipe recipe = maybeRecipe.get().value();

		if (this.brewTime > 0) {
			--this.brewTime;
			if (this.brewTime == 0) this.craft(world, pos, recipe, catalystStack);
			markDirty(world, pos, state);
		} else {
			--this.fuel;
			this.brewTime = 400;
			this.itemBrewing = catalystStack.getItem();
			markDirty(world, pos, state);
		}

		boolean[] bls = this.getSlotsEmpty();
		if (!Arrays.equals(bls, this.slotsEmptyLastTick)) {
			this.slotsEmptyLastTick = bls;
			BlockState newState = state;
			if (!(newState.getBlock() instanceof BrewingStandBlock)) {
				return;
			}

			for(int i = 0; i < BrewingStandBlock.BOTTLE_PROPERTIES.length; ++i) {
				newState = newState.with(BrewingStandBlock.BOTTLE_PROPERTIES[i], bls[i]);
			}

			world.setBlockState(pos, newState, 2);
		}

	}

	@Unique
	private void refuel(World world, BlockPos pos, BlockState state) {
		ItemStack fuelStack = this.inventory.get(4);
		if (this.fuel <= 0 && fuelStack.contains(IconicBrewingRecipes.BREWING_FUEL_COMPONENT)) {
			this.fuel = fuelStack.getOrDefault(IconicBrewingRecipes.BREWING_FUEL_COMPONENT, 0);
			fuelStack.decrement(1);
			markDirty(world, pos, state);
		}
	}

	@Unique
	private void craft(World world, BlockPos pos, BrewingRecipe recipe, ItemStack catalystStack) {
		for(int i = 0; i < 3; ++i) {
			ItemStack baseStack = this.inventory.get(i);
			baseStack = recipe.transform(baseStack);
			this.inventory.set(i, baseStack);
		}
		catalystStack.decrement(1);
		Item remainder = catalystStack.getItem().getRecipeRemainder();
		if (remainder != null) {
			ItemStack remainderStack = new ItemStack(remainder);
			if (catalystStack.isEmpty()) {
				catalystStack = remainderStack;
			} else {
				ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), remainderStack);
			}
		}
		this.inventory.set(3, catalystStack);
		world.syncWorldEvent(1035, pos, 0);
	}

	/**
	 * @author Sindercube
	 * @reason Overhaul brewing recipe handling
	 */
	@Overwrite
	public boolean isValid(int slot, ItemStack stack) {
		return switch (slot) {
			case 4 -> stack.contains(IconicBrewingRecipes.BREWING_FUEL_COMPONENT);
			case 3 -> BrewingRecipe.isValidCatalyst(stack);
			default -> this.getStack(slot).isEmpty() && stack.isIn(IconicBrewingRecipes.ALLOWED_BASES_TAG);
		};
	}

}
