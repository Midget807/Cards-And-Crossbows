package net.midget807.cardsncrossbows.entity.custom;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Arrays;

public class NonVecScalingArrowEntity extends ArrowEntity {
    public boolean shouldUpdateRotation = true;
    public NonVecScalingArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public NonVecScalingArrowEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public NonVecScalingArrowEntity(World world, LivingEntity owner) {
        super(world, owner);
        this.setOwner(owner);
    }
    
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 1;
        int i = MathHelper.ceil(MathHelper.clamp(f * this.damage + 0.5, 0.0, 2.147483647E9));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.<Entity>newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.discard();
                return;
            }

            this.piercedEntities.add(entity.getId());
        }

        if (this.isCritical()) {
            long l = this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + i, 2147483647L);
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource;
        if (entity2 == null) {
            damageSource = this.getDamageSources().arrow(this, this);
        } else {
            damageSource = this.getDamageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }

        if (entity.damage(damageSource, i)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity) {
                if (!this.getWorld().isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }

                if (this.punch > 0) {
                    double d = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                    Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(this.punch * 0.6 * d);
                    if (vec3d.lengthSquared() > 0.0) {
                        livingEntity.addVelocity(vec3d.x, 0.1, vec3d.z);
                    }
                }

                if (!this.getWorld().isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
                }

                this.onHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2)
                            .networkHandler
                            .sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, GameStateChangeS2CPacket.DEMO_OPEN_SCREEN));
                }

                if (!entity.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingEntity);
                }

                if (!this.getWorld().isClient && entity2 instanceof ServerPlayerEntity serverPlayerEntity) {
                    if (this.piercingKilledEntities != null && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, this.piercingKilledEntities);
                    } else if (!entity.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1));
            this.setYaw(this.getYaw() + 180.0F);
            this.prevYaw += 180.0F;
            if (!this.getWorld().isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient) {
            this.getWorld().addParticle(ParticleTypes.WAX_ON, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
        }
        this.baseTick();
        if (!this.shot) {
            this.emitGameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.shot = true;
        }

        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 180.0F / (float)Math.PI));
            this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 180.0F / (float)Math.PI));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        if (!blockState.isAir() && !bl) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3d vec3d2 = this.getPos();

                for (Box box : voxelShape.getBoundingBoxes()) {
                    if (box.offset(blockPos).contains(vec3d2)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shake > 0) {
            this.shake--;
        }

        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }

        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.shouldFall()) {
                this.fall();
            } else if (!this.getWorld().isClient) {
                this.age();
            }

            this.inGroundTime++;
        } else {
            this.inGroundTime = 0;
            Vec3d vec3d3 = this.getPos();
            Vec3d vec3d2 = vec3d3.add(vec3d);
            HitResult hitResult = this.getWorld()
                    .raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3d2 = hitResult.getPos();
            }

            while (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitResult).getEntity();
                    Entity entity2 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).shouldDamagePlayer((PlayerEntity)entity)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }

                if (hitResult != null && !bl) {
                    this.onCollision(hitResult);
                    this.velocityDirty = true;
                }

                if (entityHitResult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitResult = null;
            }

            vec3d = this.getVelocity();
            double e = vec3d.x;
            double f = vec3d.y;
            double g = vec3d.z;
            if (this.isCritical()) {
                for (int i = 0; i < 4; i++) {
                    this.getWorld().addParticle(ParticleTypes.CRIT, this.getX() + e * i / 4.0, this.getY() + f * i / 4.0, this.getZ() + g * i / 4.0, -e, -f + 0.2, -g);
                }
            }

            double h = this.getX() + e;
            double j = this.getY() + f;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();
            /*if (bl) {
                this.setYaw((float)(MathHelper.atan2(-e, -g) * 180.0F / (float)Math.PI));
            } else {
                this.setYaw((float)(MathHelper.atan2(e, g) * 180.0F / (float)Math.PI));
            }*/
            if (this.shouldUpdateRotation) {
                this.setPitch((float) (MathHelper.atan2(f, l) * 180.0F / (float) Math.PI));
                this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
                this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
            }
            float m = 0.99F;
            float n = 0.05F;
            if (this.isTouchingWater()) {
                for (int o = 0; o < 4; o++) {
                    float p = 0.25F;
                    this.getWorld().addParticle(ParticleTypes.BUBBLE, h - e * 0.25, j - f * 0.25, k - g * 0.25, e, f, g);
                }

                m = this.getDragInWater();
            }

            this.setVelocity(vec3d.multiply(m));
            if (!this.hasNoGravity() && !bl) {
                Vec3d vec3d4 = this.getVelocity();
                this.setVelocity(vec3d4.x, vec3d4.y - 0.05F, vec3d4.z);
            }

            this.setPosition(h, j, k);
            this.checkBlockCollision();
            if (this.age == 400) {
                this.discard();
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public void setShouldUpdateRotation(boolean shouldUpdateRotation) {
        this.shouldUpdateRotation = shouldUpdateRotation;
    }
}
