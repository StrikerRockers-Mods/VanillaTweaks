package io.github.strikerrocker.vt.world.selfplanting;

import net.minecraft.entity.item.ItemEntity;

public interface ISelfPlanting {
    int getMinSteadyTicks();

    void setMinSteadyTicks(int minSteadyTicks);

    int getSteadyTicks();

    void setSteadyTicks(int steadyTicks);

    void handlePlantingLogic(ItemEntity stack);
}
