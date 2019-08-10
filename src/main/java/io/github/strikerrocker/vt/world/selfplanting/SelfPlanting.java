package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
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
    public void handlePlantingLogic(ItemEntity entity) {
        Item item = entity.getItem().getItem();
        if (item instanceof BlockItem && Block.getBlockFromItem(item) instanceof IGrowable) {
            if (this.minSteadyTicks == 0)
                this.minSteadyTicks = random.nextInt(75) + 75;
            ++this.steadyTicks;
            BlockPos entityPos = new BlockPos(entity);
            BlockPos lastTickEntityPos = new BlockPos(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
            if (entityPos.compareTo(lastTickEntityPos) != 0)
                this.steadyTicks = 0;
            if (this.steadyTicks >= this.minSteadyTicks && entity.getItem().getItem().onItemUse(new ItemUseContext(Utils.getFakePlayer(entity.world), Hand.MAIN_HAND, null)) == ActionResultType.SUCCESS) {
                entity.getItem().shrink(1);
            }
        }
    }
}
