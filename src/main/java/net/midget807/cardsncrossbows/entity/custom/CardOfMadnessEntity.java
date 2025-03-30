package net.midget807.cardsncrossbows.entity.custom;

import net.midget807.cardsncrossbows.entity.ModEntities;
import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class CardOfMadnessEntity extends ThrownItemEntity {

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    public CardOfMadnessEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.CARD_OF_MADNESS, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CARD_OF_MADNESS;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof ArrowEntity arrowEntity) {
            if (!arrowEntity.isOnGround()) {
                int i = MathHelper.ceil(MathHelper.clamp(arrowEntity.getDamage() * arrowEntity.getVelocity().length(), 0.0, 2.147483647E9));
                Predicate<LivingEntity> predicate = livingEntity -> livingEntity != this.getOwner() && livingEntity instanceof PlayerEntity && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity);
                TargetPredicate targetPredicate = TargetPredicate.createAttackable().setPredicate(predicate);
                PlayerEntity playerEntity = this.getWorld().getClosestPlayer(targetPredicate, null);
                if (playerEntity != null) {
                    NonVecScalingArrow nonVecScalingArrow = new NonVecScalingArrow(this.getWorld(), (LivingEntity) this.getOwner());
                    nonVecScalingArrow.setDamage(i);
                    nonVecScalingArrow.setVelocity(playerEntity.getPos().subtract(this.getPos()));
                    nonVecScalingArrow.setVelocity(nonVecScalingArrow.getVelocity().multiply(100));
                    entity.discard();
                    this.getWorld().spawnEntity(nonVecScalingArrow);
                }
            }
        } else {
            entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1.0f);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}
