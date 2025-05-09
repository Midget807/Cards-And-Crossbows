package net.midget807.cardsncrossbows.entity.custom.client;

import net.midget807.cardsncrossbows.entity.custom.NonVecScalingArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class NonVecScalingArrowRenderer extends ProjectileEntityRenderer<NonVecScalingArrowEntity> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");
    public static final Identifier TIPPED_TEXTURE = new Identifier("textures/entity/projectiles/tipped_arrow.png");
    public NonVecScalingArrowRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(NonVecScalingArrowEntity entity) {
        return entity.getColor() > 0 ? TIPPED_TEXTURE : TEXTURE;
    }
}
