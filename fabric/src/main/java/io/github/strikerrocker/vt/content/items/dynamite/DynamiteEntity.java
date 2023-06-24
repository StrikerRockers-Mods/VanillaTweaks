package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.content.items.FabricItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DynamiteEntity extends ThrowableItemProjectile {
    private static final int WET_TICKS = 20;
    private static final EntityDataAccessor<Integer> TICKS_WET;
    private static final EntityDataAccessor<Integer> TICKS_SINCE_WET;

    static {
        TICKS_WET = SynchedEntityData.defineId(DynamiteEntity.class, EntityDataSerializers.INT);
        TICKS_SINCE_WET = SynchedEntityData.defineId(DynamiteEntity.class, EntityDataSerializers.INT);
    }

    public DynamiteEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    DynamiteEntity(Level level, double x, double y, double z) {
        super(FabricItems.DYNAMITE_TYPE, x, y, z, level);
    }

    DynamiteEntity(Level world, LivingEntity entity) {
        super(FabricItems.DYNAMITE_TYPE, entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return FabricItems.DYNAMITE;
    }

    @Override
    public ItemStack getItemRaw() {
        return new ItemStack(FabricItems.DYNAMITE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(TICKS_WET, 0);
        entityData.define(TICKS_SINCE_WET, WET_TICKS);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.isInWaterRainOrBubble()) getEntityData().set(TICKS_WET, getEntityData().get(TICKS_WET) + 1);
            else getEntityData().set(TICKS_WET, 0);
            if (getEntityData().get(TICKS_WET) == 0)
                getEntityData().set(TICKS_SINCE_WET, getEntityData().get(TICKS_SINCE_WET) + 1);
            else getEntityData().set(TICKS_SINCE_WET, 0);
        }
        if (getEntityData().get(TICKS_SINCE_WET) < WET_TICKS && !this.isUnderWater()) for (int i = 0; i < 3; ++i) {
            float xOffset = (random.nextFloat() * 2 - 1) * getBbWidth() * 0.5F;
            float zOffset = (random.nextFloat() * 2 - 1) * getBbWidth() * 0.5F;
            BlockPos pos = blockPosition();
            level().addParticle(ParticleTypes.DRIPPING_WATER, pos.getX() + xOffset, pos.getY(), pos.getZ() + zOffset, getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (!level().isClientSide) {
            if (getEntityData().get(TICKS_SINCE_WET) < WET_TICKS) {
                this.spawnAtLocation(FabricItems.DYNAMITE);
                this.remove(RemovalReason.KILLED);
            } else {
                if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof DynamiteEntity) {
                    return;
                } else {
                    BlockPos pos = blockPosition();
                    level().explode(this, pos.getX(), pos.getY(), pos.getZ(), VanillaTweaksFabric.config.content.dynamiteExplosionPower, Level.ExplosionInteraction.TNT);
                }
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}