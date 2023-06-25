package io.github.strikerrocker.vt;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IVTService {
    boolean isSelfPlantingEnabled();

    List<? extends String> selfPlantingBlackList();

    int getSelfPlantingInterval();

    boolean place(ItemEntity itemEntity, Level level);
}
