package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.world.SelfPlanting;

import java.util.List;

public class VTServiceImpl implements IVTService {
    @Override
    public boolean isSelfPlantingEnabled() {
        return SelfPlanting.enableSelfPlanting.get();
    }

    @Override
    public List<? extends String> selfPlantingBlackList() {
        return SelfPlanting.selfPlantingBlacklist.get();
    }
}
