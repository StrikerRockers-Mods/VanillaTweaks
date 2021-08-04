package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.content.items.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DynamiteEntity extends ProjectileItemEntity {
    private static final int WET_TICKS = 20;
    private static final DataParameter<Integer> TICKS_WET = EntityDataManager.defineId(DynamiteEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> TICKS_SINCE_WET = EntityDataManager.defineId(DynamiteEntity.class, DataSerializers.INT);

    public DynamiteEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
        super(type, world);
    }

    public DynamiteEntity(World world, double x, double y, double z) {
        super(Items.DYNAMITE_TYPE, x, y, z, world);
    }

    DynamiteEntity(World world, LivingEntity shooter) {
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
    protected void onHit(RayTraceResult result) {
        if (!level.isClientSide()) {
            if (this.entityData.get(TICKS_SINCE_WET) < WET_TICKS) {
                this.spawnAtLocation(Items.DYNAMITE);
                this.remove();
            } else {
                if (result instanceof EntityRayTraceResult && ((EntityRayTraceResult) result).getEntity() instanceof DynamiteEntity) {
                    return;
                } else {
                    BlockPos pos = blockPosition();
                    level.explode(this, pos.getX(), pos.getY(), pos.getZ(), Items.dynamiteExplosionPower.get(), Explosion.Mode.BREAK);
                }
            }
            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}