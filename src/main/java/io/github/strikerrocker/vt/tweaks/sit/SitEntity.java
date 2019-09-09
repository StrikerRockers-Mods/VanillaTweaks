package io.github.strikerrocker.vt.tweaks.sit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.HashMap;
import java.util.Map;

public class SitEntity extends Entity {
    static final Map<BlockPos, SitEntity> OCCUPIED = new HashMap<>();

    SitEntity(EntityType<SitEntity> type, World world) {
        super(type, world);
    }

    SitEntity(World world, BlockPos pos) {
        super(Sit.SIT_ENTITY_TYPE, world);
        setPosition(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noClip = true;
        OCCUPIED.put(pos, this);
    }

    @Override
    protected void registerData() {
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
