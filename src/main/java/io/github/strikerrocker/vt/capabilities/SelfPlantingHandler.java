package io.github.strikerrocker.vt.capabilities;

import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Random;

public class SelfPlantingHandler implements ISelfPlanting, INBTSerializable<NBTTagCompound>
{
    public static final String MINSTEADYTICKS_KEY = "MinSteadyTicks";
    public static final String STEADYTICKS_KEY = "SteadyTicks";
    private static Random random = new Random();
    private int minSteadyTicks, steadyTicks;

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger(MINSTEADYTICKS_KEY, minSteadyTicks);
        compound.setInteger(STEADYTICKS_KEY, steadyTicks);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        minSteadyTicks = nbt.getInteger(MINSTEADYTICKS_KEY);
        steadyTicks = nbt.getInteger(STEADYTICKS_KEY);
    }

    @Override
    public int getMinSteadyTicks()
    {
        return minSteadyTicks;
    }

    @Override
    public void setMinSteadyTicks(int minSteadyTicks)
    {
        this.minSteadyTicks = minSteadyTicks;
    }

    @Override
    public int getSteadyTicks()
    {
        return steadyTicks;
    }

    @Override
    public void setSteadyTicks(int steadyTicks)
    {
        this.steadyTicks = steadyTicks;
    }

    @Override
    public void handlePlantingLogic(EntityItem entity)
    {
        Item item = entity.getItem().getItem();
        if (item instanceof ItemSeeds || item instanceof ItemSeedFood)
        {
            if (this.minSteadyTicks == 0)
                this.minSteadyTicks = random.nextInt(75) + 75;
            ++this.steadyTicks;
            BlockPos entityPos = new BlockPos(entity);
            BlockPos lastTickEntityPos = new BlockPos(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
            if (entityPos.compareTo(lastTickEntityPos) != 0)
                this.steadyTicks = 0;
            if (this.steadyTicks >= this.minSteadyTicks)
            {
                entity.getItem().getItem().onItemUse(VTUtils.getFakePlayer(entity.world), entity.world, entityPos, EnumHand.MAIN_HAND, EnumFacing.UP, 0, 0, 0);
                entity.getItem().shrink(1);
            }
        }
    }
}