package io.github.strikerrocker.vt;

import java.util.List;

public interface IVTService {
    boolean isSelfPlantingEnabled();

    List<? extends String> selfPlantingBlackList();
}
