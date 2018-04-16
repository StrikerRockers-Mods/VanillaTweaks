package io.github.strikerrocker.vt.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class EntitySitting extends Entity
{
    public static final HashMap<BlockPos, EntitySitting> OCCUPIED = new HashMap<>();

    @SuppressWarnings("unused")
    public EntitySitting(World world) {
        super(world);
        noClip = true;
        height = 0.0001F;
        width = 0.0001F;
    }

    public EntitySitting(World world, BlockPos pos) {
        super(world);
        setPosition(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noClip = true;
        height = 0.0001F;
        width = 0.0001F;
        OCCUPIED.put(pos, this);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }
}