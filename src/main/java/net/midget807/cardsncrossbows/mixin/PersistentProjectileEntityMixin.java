package net.midget807.cardsncrossbows.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midget807.cardsncrossbows.entity.custom.ShotByCardsOfMadness;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity implements ShotByCardsOfMadness {
    @Shadow public double damage;
    private boolean shotByCardOfMadness = false;
    private Vec3d nonVecScalingVelocity = new Vec3d(0, 0, 0);

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isShotByCardOfMadness() {
        return this.shotByCardOfMadness;
    }
    @Override
    public void setShotByCardOfMadness(boolean shotByCardOfMadness) {
        this.shotByCardOfMadness = shotByCardOfMadness;
    }

    @Override
    public Vec3d getNonVecScalingVelocity() {
        return this.nonVecScalingVelocity;
    }
    @Override
    public void setNonVecScalingVelocity(Vec3d nonVecScalingVelocity) {
        this.nonVecScalingVelocity = nonVecScalingVelocity;
    }


}
