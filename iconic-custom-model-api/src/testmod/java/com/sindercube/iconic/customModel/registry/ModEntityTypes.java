package com.sindercube.iconic.customModel.registry;

import com.sindercube.iconic.Iconic;
import com.sindercube.iconic.customModel.content.VilgerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityTypes {

	public static void init() {
		FabricDefaultAttributeRegistry.register(VILGER, VilgerEntity.createLivingAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1)
			.add(EntityAttributes.GENERIC_ARMOR, 1)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1)
			.build());
	}

    public static final EntityType<VilgerEntity> VILGER = Registry.register(
            Registries.ENTITY_TYPE,
            Iconic.of("vilger"),
            EntityType.Builder.create(VilgerEntity::new, SpawnGroup.MISC)
                    .dimensions(0.6f, 1.35f)
                    .build()
    );

}
