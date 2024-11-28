package com.sindercube.iconic.constructs.testmod.content;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VilgerEntity extends VillagerEntity {

    public final AnimationState spinningHeadAnimationState = new AnimationState();

    public VilgerEntity(EntityType<? extends VilgerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public VillagerEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public void onDamaged(DamageSource source) {
        super.onDamaged(source);
        spinningHeadAnimationState.startIfNotRunning(this.age);
    }

}
