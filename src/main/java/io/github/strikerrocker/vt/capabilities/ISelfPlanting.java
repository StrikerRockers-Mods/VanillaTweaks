package io.github.strikerrocker.vt.capabilities;

import net.minecraft.entity.item.EntityItem;

public interface ISelfPlanting
{
    int getMinSteadyTicks();

    void setMinSteadyTicks(int minSteadyTicks);

    int getSteadyTicks();

    void setSteadyTicks(int steadyTicks);

    void handlePlantingLogic(EntityItem stack);
}
