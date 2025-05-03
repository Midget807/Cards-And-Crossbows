package net.midget807.cardsncrossbows.entity.custom;

import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.function.Predicate;

public class CardOfMadnessEntity extends ThrownItemEntity {
    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
        this.setOwner(livingEntity);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CARD_OF_MADNESS;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof ArrowEntity) {
            entity.discard();
        }
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1.0f);
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH_SMALL, SoundCategory.PLAYERS, 0.25f, 0.75f);
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.CARDS_OF_MADNESS), 0.0, 0.1, 0.0));
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH_SMALL, SoundCategory.NEUTRAL, 0.25f, MathHelper.clamp(this.getWorld().getRandom().nextFloat(), 0.7f, 0.85f));
        this.discard();
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getSource();
        LivingEntity owner = (LivingEntity) this.getOwner();
        if (entity instanceof PersistentProjectileEntity) {
            Vec3d arrowVec = entity.getVelocity();
            Vec3d arrowPos = entity.getPos();
            LivingEntity target;
            if (owner != null) {
                Predicate<Entity> predicate = entity2 -> !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative() && entity2 != owner;
                target = owner.getWorld().getClosestPlayer(entity.getX(), entity.getY(), entity.getZ(), 20, predicate);

                if (target != null) {
                    this.discard();
                    NonVecScalingArrowEntity nonVecScalingArrow = new NonVecScalingArrowEntity(this.getWorld(), owner);
                    nonVecScalingArrow.setPosition(arrowPos);
                    nonVecScalingArrow.setVelocity(0, 0, 0);
                    nonVecScalingArrow.setDamage(arrowVec.length() * 2.0f);
                    nonVecScalingArrow.setNoGravity(true);
                    nonVecScalingArrow.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getEyePos());
                    nonVecScalingArrow.setPitch(nonVecScalingArrow.getPitch());
                    nonVecScalingArrow.setYaw(nonVecScalingArrow.getYaw());
                    nonVecScalingArrow.setVelocity(owner, nonVecScalingArrow.getPitch(), nonVecScalingArrow.getYaw(), 0.0f, 1.0f, 0.0f);
                    nonVecScalingArrow.setShouldUpdateRotation(false);
                    owner.getWorld().spawnEntity(nonVecScalingArrow);
                }
            }
        }
        return true;
    }
}
