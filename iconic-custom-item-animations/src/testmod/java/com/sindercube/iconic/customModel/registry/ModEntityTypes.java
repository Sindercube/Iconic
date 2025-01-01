package com.sindercube.iconic.customModel.registry;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.content.VilgerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

	public static void init() {
		FabricDefaultAttributeRegistry.register(VILGER, VillagerEntity.createVillagerAttributes());
	}

    public static final EntityType<VilgerEntity> VILGER = register("vilger",
            EntityType.Builder.create(VilgerEntity::new, SpawnGroup.MISC)
                    .dimensions(0.6f, 1.35f)
    );

	public static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		Identifier id = Iconic.of(name);
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
		EntityType<T> type = builder.build(key);
		return Registry.register(Registries.ENTITY_TYPE, id, type);
	}

}
