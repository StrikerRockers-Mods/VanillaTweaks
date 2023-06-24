package io.github.strikerrocker.vt;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IVTService {
    boolean isSelfPlantingEnabled();

    List<? extends String> selfPlantingBlackList();

    int getSelfPlantingInterval();

    Player getFakePlayer(ServerLevel serverLevel);
}
