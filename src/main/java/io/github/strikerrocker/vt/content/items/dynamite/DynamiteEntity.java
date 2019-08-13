package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.content.items.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DynamiteEntity extends ProjectileItemEntity {
    private static final int WET_TICKS = 20;
    private static final DataParameter<Integer> TICKSWET = EntityDataManager.createKey(DynamiteEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKSSINCEWET = EntityDataManager.createKey(DynamiteEntity.class, DataSerializers.VARINT);

    public DynamiteEntity(EntityType type, World world) {
        super(type, world);
    }

    public DynamiteEntity(World world, double x, double y, double z) {
        super(Items.DYNAMITE_TYPE, x, y, z, world);
    }

    DynamiteEntity(World world, LivingEntity entityLivingBase) {
        super(Items.DYNAMITE_TYPE, entityLivingBase, world);
    }

    @Override
    protected Item func_213885_i() {
        return Items.dynamite;
    }

    @Override
    protected void registerData() {
        dataManager.register(TICKSWET, 0);
        dataManager.register(TICKSSINCEWET, WET_TICKS);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.isWet()) {
                this.dataManager.set(TICKSWET, this.dataManager.get(TICKSWET) + 1);
            } else
                this.dataManager.set(TICKSWET, 0);
            if (this.dataManager.get(TICKSWET) == 0)
                this.dataManager.set(TICKSSINCEWET, this.dataManager.get(TICKSSINCEWET) + 1);
            else
                this.dataManager.set(TICKSSINCEWET, 0);
        } else {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
        if (this.dataManager.get(TICKSSINCEWET) < WET_TICKS && !this.isInWater())
            for (int i = 0; i < 3; ++i) {
                float xOffset = (rand.nextFloat() * 2 - 1) * getWidth() * 0.5F;
                float zOffset = (rand.nextFloat() * 2 - 1) * getWidth() * 0.5F;
                world.addParticle(ParticleTypes.DRIPPING_WATER, posX + xOffset, posY, posZ + zOffset, getMotion().x, getMotion().y, getMotion().z);
            }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote)
            if (this.dataManager.get(TICKSSINCEWET) < WET_TICKS) {
                if (!(getThrower() instanceof PlayerEntity) || ((PlayerEntity) getThrower()).abilities.isCreativeMode)
                    this.entityDropItem(Items.dynamite);
            } else {
                if (((EntityRayTraceResult) result).getEntity() instanceof DynamiteEntity)
                    return;
                else
                    world.createExplosion(this, posX, posY, posZ, 3F, Explosion.Mode.BREAK);
            }
        this.remove();
    }
}
