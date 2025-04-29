package net.midget807.cardsncrossbows.entity.custom;

import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CardOfMadnessEntity extends ThrownItemEntity {
    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    public CardOfMadnessEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
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
        if (entity instanceof ArrowEntity) {
            entity.discard();
            this.getWorld().spawnEntity(new CowEntity(EntityType.COW, this.getWorld()));
            this.discard();
        }
        return false;
    }
}
