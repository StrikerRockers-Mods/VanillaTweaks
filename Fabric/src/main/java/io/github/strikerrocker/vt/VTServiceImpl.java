package io.github.strikerrocker.vt;

import java.util.List;

public class VTServiceImpl implements IVTService {
    @Override
    public boolean isSelfPlantingEnabled() {
        return VanillaTweaksFabric.config.world.selfPlanting;
    }

    @Override
    public List<? extends String> selfPlantingBlackList() {
        return VanillaTweaksFabric.config.world.selfPlantingBlackList;
    }
}
