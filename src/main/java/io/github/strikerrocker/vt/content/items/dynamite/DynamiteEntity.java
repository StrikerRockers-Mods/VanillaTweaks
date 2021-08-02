package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.content.items.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class DynamiteEntity extends ThrowableItemProjectile {
    private static final int WET_TICKS = 20;
    private static final EntityDataAccessor<Integer> TICKS_WET = SynchedEntityData.defineId(DynamiteEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKS_SINCE_WET = SynchedEntityData.defineId(DynamiteEntity.class, EntityDataSerializers.INT);

    public DynamiteEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public DynamiteEntity(Level world, double x, double y, double z) {
        super(Items.DYNAMITE_TYPE, x, y, z, world);
    }

    DynamiteEntity(Level world, LivingEntity shooter) {
        super(Items.DYNAMITE_TYPE, shooter, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.DYNAMITE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.DYNAMITE);
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
        if (!this.level.isClientSide()) {
            if (this.isUnderWater()) {
                this.entityData.set(TICKS_WET, this.entityData.get(TICKS_WET) + 1);
            } else
                this.entityData.set(TICKS_WET, 0);
            if (this.entityData.get(TICKS_WET) == 0)
                this.entityData.set(TICKS_SINCE_WET, this.entityData.get(TICKS_SINCE_WET) + 1);
            else
                this.entityData.set(TICKS_SINCE_WET, 0);
        }
        if (this.entityData.get(TICKS_SINCE_WET) < WET_TICKS && !this.isInWater())
            for (int i = 0; i < 3; ++i) {
                float xOffset = (random.nextFloat() * 2 - 1) * getBbWidth() * 0.5F;
                float zOffset = (random.nextFloat() * 2 - 1) * getBbWidth() * 0.5F;
                BlockPos pos = blockPosition();
                level.addParticle(ParticleTypes.DRIPPING_WATER, pos.getX() + xOffset, pos.getY(), pos.getZ() + zOffset, getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z);
            }
    }

    @Override
    protected void onHit(HitResult result) {
        if (!level.isClientSide()) {
            if (this.entityData.get(TICKS_SINCE_WET) < WET_TICKS) {
                this.spawnAtLocation(Items.DYNAMITE);
                this.remove(RemovalReason.KILLED);
            } else {
                if (result instanceof EntityHitResult && ((EntityHitResult) result).getEntity() instanceof DynamiteEntity) {
                    return;
                } else {
                    BlockPos pos = blockPosition();
                    level.explode(this, pos.getX(), pos.getY(), pos.getZ(), Items.dynamiteExplosionPower.get(), Explosion.BlockInteraction.BREAK);
                }
            }
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}