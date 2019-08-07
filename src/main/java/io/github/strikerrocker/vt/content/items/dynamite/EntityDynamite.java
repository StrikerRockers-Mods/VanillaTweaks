package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.content.items.Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDynamite extends ThrowableEntity {
    /**
     * Number of ticks required for this dynamite to dry off
     */
    private static final int WET_TICKS = 20;

    /**
     * Datawatcher ID for ticks wet
     */
    private static final DataParameter<Integer> TICKSWET = EntityDataManager.createKey(EntityDynamite.class, DataSerializers.VARINT);

    /**
     * Datawatcher ID for ticks since wet
     */
    private static final DataParameter<Integer> TICKSSINCEWET = EntityDataManager.createKey(EntityDynamite.class, DataSerializers.VARINT);

    @SuppressWarnings("unused")
    public EntityDynamite(World world) {
        super(world);
    }

    public EntityDynamite(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    EntityDynamite(World world, LivingEntity entityLivingBase) {
        super(world);
    }

    public EntityDynamite(EntityType<? extends EnderPearlEntity> p_i50153_1_, World p_i50153_2_) {
        super(p_i50153_1_, p_i50153_2_);
    }

    public EntityDynamite(World worldIn, LivingEntity throwerIn) {
        super(EntityType.ENDER_PEARL, throwerIn, worldIn);
        this.perlThrower = throwerIn;
    }

    @OnlyIn(Dist.CLIENT)
    public EntityDynamite(World worldIn, double x, double y, double z) {
        super(EntityType.ENDER_PEARL, x, y, z, worldIn);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(TICKSWET, 0);
        this.dataManager.register(TICKSSINCEWET, WET_TICKS);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
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
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
        if (this.dataManager.get(TICKSSINCEWET) < WET_TICKS && !this.isInWater())
            for (int i = 0; i < 3; ++i) {
                float xOffset = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                float zOffset = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + xOffset, this.posY, this.posZ + zOffset, this.motionX, this.motionY, this.motionZ);
            }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote)
            if (this.dataManager.get(TICKSSINCEWET) < WET_TICKS) {
                if (!(getThrower() instanceof EntityPlayer) || ((EntityPlayer) getThrower()).capabilities.isCreativeMode)
                    this.dropItem(Items.dynamite, 1);
            } else {
                if (result.entityHit instanceof EntityDynamite)
                    return;
                else
                    world.createExplosion(this, this.posX, this.posY, this.posZ, 3F, true);
            }
        this.remove();
    }

    @Override
    protected void registerData() {

    }
}
