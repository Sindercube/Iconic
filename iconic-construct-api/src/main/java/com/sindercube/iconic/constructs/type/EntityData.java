package com.sindercube.iconic.constructs.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public record EntityData(EntityType<?> type, NbtCompound data) {

	public static final Codec<EntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Registries.ENTITY_TYPE.getCodec().fieldOf("id").forGetter(EntityData::type),
		NbtCompound.CODEC.optionalFieldOf("data", new NbtCompound()).forGetter(EntityData::data)
	).apply(instance, EntityData::new));

	public @Nullable Entity create(ServerWorld world) {
		Entity entity = type.create(world, SpawnReason.TRIGGERED);
		if (entity != null) entity.readNbt(data);
		return entity;
	}

}
