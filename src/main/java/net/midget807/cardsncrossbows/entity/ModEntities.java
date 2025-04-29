package net.midget807.cardsncrossbows.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.midget807.cardsncrossbows.entity.custom.CardOfMadnessEntity;
import net.midget807.cardsncrossbows.entity.custom.NonVecScalingArrowEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {

    public static final EntityType<CardOfMadnessEntity> CARD_OF_MADNESS = Registry.register(
            Registries.ENTITY_TYPE, CardsNCrossbowsMain.id("card_of_madness"),
            FabricEntityTypeBuilder.<CardOfMadnessEntity>create(SpawnGroup.MISC, CardOfMadnessEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackedUpdateRate(10)
                    .trackRangeBlocks(4)
                    .build()
    );

    public static final EntityType<NonVecScalingArrowEntity> NON_VEC_SCALING_ARROW = Registry.register(
            Registries.ENTITY_TYPE, CardsNCrossbowsMain.id("non_vec_scaling_arrow"),
            FabricEntityTypeBuilder.<NonVecScalingArrowEntity>create(SpawnGroup.MISC, NonVecScalingArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackedUpdateRate(10)
                    .trackRangeBlocks(4)
                    .build()
    );

    public static void registerModEntities() {
        CardsNCrossbowsMain.LOGGER.info("Register Mod Entities");
    }
}
