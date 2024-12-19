package com.sindercube.iconic.constructs;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.constructs.api.content.block.ConstructBlockPlacementDispenserBehavior;
import com.sindercube.iconic.constructs.api.content.entity.ConstructableEntity;
import com.sindercube.iconic.constructs.mixin.CarvedPumpkinBlockInvoker;
import com.sindercube.iconic.constructs.type.Construct;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class IconicConstructs implements ModInitializer {

	public static final RegistryKey<Registry<Construct>> REGISTRY_KEY = RegistryKey.ofRegistry(Iconic.of("constructs"));

	public static final TagKey<Block> CONSTRUCT_BLOCK = TagKey.of(
		RegistryKeys.BLOCK,
		Iconic.of("construct_blocks")
	);


	@Override
	public void onInitialize() {
		DynamicRegistries.registerSynced(REGISTRY_KEY, Construct.CODEC);
		DispenserBlock.registerBehavior(Blocks.CARVED_PUMPKIN, new ConstructBlockPlacementDispenserBehavior());
		DispenserBlock.registerBehavior(Blocks.WITHER_SKELETON_SKULL, new ConstructBlockPlacementDispenserBehavior());
	}


	public static boolean isConstructBlock(BlockState state) {
		return state.isIn(CONSTRUCT_BLOCK);
	}

	public static boolean isConstructBlock(Block block) {
		return isConstructBlock(block.getDefaultState());
	}

	public static void trySpawnConstructs(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (isConstructBlock(block)) world.getRegistryManager().get(IconicConstructs.REGISTRY_KEY)
			.forEach(construct -> trySpawnConstruct(world, pos, construct));
	}

	public static void trySpawnConstruct(World world, BlockPos pos, Construct construct) {
		BlockPattern.Result result = construct.matchPattern(world, pos);
		if (result == null) return;

		Entity entity = construct.createEntity(world);
		if (entity == null) return;

		CarvedPumpkinBlockInvoker.invokeSpawnEntity(world, result, entity, construct.offsetPosition(result));
		if (entity instanceof ConstructableEntity constructable) constructable.onConstructed(result, world, pos);
	}

	public static boolean canDispense(WorldView world, BlockPos pos) {
		return world.getRegistryManager().get(IconicConstructs.REGISTRY_KEY).stream()
			.map(construct -> construct.canConstruct(world, pos))
			.toList().contains(true);
	}

}
