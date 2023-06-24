package io.github.strikerrocker.vt;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IVTService {
    boolean isSelfPlantingEnabled();

    List<? extends String> selfPlantingBlackList();

    boolean place(ItemEntity itemEntity, Level level);

    int getSelfPlantingInterval();
}
