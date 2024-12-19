package com.sindercube.iconic.brewingRecipe;

import com.mojang.serialization.Codec;
import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.brewingRecipe.type.BrewingRecipe;
import com.sindercube.iconic.brewingRecipe.type.ItemBrewingRecipe;
import com.sindercube.iconic.brewingRecipe.type.PotionBrewingRecipe;
import com.sindercube.iconic.event.ExtraServerLifecycleEvents;
import com.sindercube.iconic.util.recipe.IdentifierRecipeType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class IconicBrewingRecipes implements ModInitializer {

	public static final RecipeType<BrewingRecipe> BREWING_RECIPE_TYPE = Registry.register(
		Registries.RECIPE_TYPE,
		Iconic.of("brewing"),
		new IdentifierRecipeType<>(Iconic.of("brewing"))
	);

	public static final ComponentType<Integer> BREWING_FUEL_COMPONENT = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Iconic.of("brewing_fuel"),
		new ComponentType.Builder<Integer>().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build()
	);

	public static final RecipeSerializer<PotionBrewingRecipe> POTION_BREWING_RECIPE_SERIALIZER = Registry.register(
		Registries.RECIPE_SERIALIZER,
		Iconic.of("brewing_potion"),
		PotionBrewingRecipe.SERIALIZER
	);

	public static final RecipeSerializer<ItemBrewingRecipe> ITEM_BREWING_RECIPE_SERIALIZER = Registry.register(
		Registries.RECIPE_SERIALIZER,
		Iconic.of("brewing_item"),
		ItemBrewingRecipe.SERIALIZER
	);

	public static final TagKey<Item> POTION_BASES_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of("potion_bases"));
	public static final TagKey<Item> ALLOWED_BASES_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of("allowed_bases"));

	@Override
	public void onInitialize() {
		ExtraServerLifecycleEvents.AFTER_RESOURCE_RELOAD.register(server -> {
			System.out.println("AAAAAAAAA");
//			server.getRecipeManager().
			DynamicRegistries.getDynamicRegistries().stream()
				.map(RegistryLoader.Entry::key)
				.forEach(System.out::println);
//					.filter(e -> e.key().equals(RegistryKeys.RECIPE))
//					.toList().getFirst();
//			System.out.println(test);
//			server.getBrewingRecipeRegistry().itemRecipes.forEach(e -> {
//				RegistryEntry<BrewingRecipe> test = null;
//				DynamicRegistries.getDynamicRegistries().
//			});
			BrewingRecipe.POSSIBLE_CATALYSTS.clear();
			server.getRecipeManager().values().stream()
				.map(RecipeEntry::value)
				.filter(recipe -> recipe.getType().equals(IconicBrewingRecipes.BREWING_RECIPE_TYPE))
				.map(recipe -> ((BrewingRecipe) recipe).getBrewingIngredients().catalyst())
				.forEach(BrewingRecipe.POSSIBLE_CATALYSTS::add);
		});
		DefaultItemComponentEvents.MODIFY.register(context -> {
			context.modify(Items.BLAZE_POWDER, components -> components.add(BREWING_FUEL_COMPONENT, 20));
		});
	}

}
