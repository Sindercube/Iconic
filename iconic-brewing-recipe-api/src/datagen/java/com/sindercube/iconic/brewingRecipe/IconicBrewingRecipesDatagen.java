package com.sindercube.iconic.brewingRecipe;

import com.sindercube.iconic.event.ExtraServerLifecycleEvents;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.CompletableFuture;

public class IconicBrewingRecipesDatagen implements DataGeneratorEntrypoint {

	public static MinecraftServer SERVER;

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		ServerWorldEvents.LOAD.register((server, world) -> SERVER = server);
		ExtraServerLifecycleEvents.AFTER_RESOURCE_RELOAD.register(server -> SERVER = server);
		ServerLifecycleEvents.SERVER_STARTING.register(server -> SERVER = server);

		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, manager) -> SERVER = server);

		pack.addProvider(RecipeGenerator::new);
	}

	public static class RecipeGenerator extends FabricRecipeProvider {

		public RecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
			super(output, lookup);
		}

		@Override
		public void generate(RecipeExporter exporter) {
//			System.out.println(BrewingRecipeRegistry.EMPTY);

			ExtraServerLifecycleEvents.AFTER_RESOURCE_RELOAD.register(server -> {
				System.out.println("test");
			});
			ServerWorldEvents.LOAD.register((server, world) -> {
				System.out.println("test");
			});
			ExtraServerLifecycleEvents.AFTER_RESOURCE_RELOAD.register(server -> {
				System.out.println("test");
			});
			ServerLifecycleEvents.SERVER_STARTING.register(server -> {
				System.out.println("test");

			});
			ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, manager) -> {
				System.out.println("test");
			});

//			SERVER.getBrewingRecipeRegistry().itemRecipes.forEach(recipe -> {
//				System.out.println("test");
//			});
		}

	}

}
