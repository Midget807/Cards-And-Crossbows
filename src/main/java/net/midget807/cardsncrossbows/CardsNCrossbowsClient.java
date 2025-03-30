package net.midget807.cardsncrossbows;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.cardsncrossbows.entity.ModEntities;
import net.midget807.cardsncrossbows.entity.custom.client.NonVecScalingArrowRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class CardsNCrossbowsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CARD_OF_MADNESS, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.NON_VEC_SCALING_ARROW, NonVecScalingArrowRenderer::new);
    }
}
