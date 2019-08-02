package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SelfPlanting implements ISelfPlanting {
    static final String MIN_STEADY_TICKS_KEY = "MinSteadyTicks";
    static final String STEADY_TICKS_KEY = "SteadyTicks";
    private static Random random = new Random();
    private int minSteadyTicks = 0;
    private int steadyTicks = 0;

    @Override
    public int getMinSteadyTicks() {
        return minSteadyTicks;
    }

    @Override
    public void setMinSteadyTicks(int minSteadyTicks) {
        this.minSteadyTicks = minSteadyTicks;
    }

    @Override
    public int getSteadyTicks() {
        return steadyTicks;
    }

    @Override
    public void setSteadyTicks(int steadyTicks) {
        this.steadyTicks = steadyTicks;
    }

    @Override
    public void handlePlantingLogic(EntityItem entity) {
        Item item = entity.getItem().getItem();
        if (item instanceof ItemSeeds || item instanceof ItemSeedFood) {
            if (this.minSteadyTicks == 0)
                this.minSteadyTicks = random.nextInt(75) + 75;
            ++this.steadyTicks;
            BlockPos entityPos = new BlockPos(entity);
            BlockPos lastTickEntityPos = new BlockPos(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
            if (entityPos.compareTo(lastTickEntityPos) != 0)
                this.steadyTicks = 0;
            if (this.steadyTicks >= this.minSteadyTicks && entity.getItem().getItem().onItemUse(Utils.getFakePlayer(entity.world), entity.world, entityPos, EnumHand.MAIN_HAND, EnumFacing.UP, 0, 0, 0) == EnumActionResult.SUCCESS) {
                entity.getItem().shrink(1);
            }
        }
    }
}
