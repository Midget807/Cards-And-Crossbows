package net.midget807.cardsncrossbows.entity.custom;

import net.minecraft.util.math.Vec3d;

public interface ShotByCardsOfMadness {
    boolean isShotByCardOfMadness();
    void setShotByCardOfMadness(boolean bl);

    Vec3d getNonVecScalingVelocity();
    void setNonVecScalingVelocity(Vec3d velocity);
}
