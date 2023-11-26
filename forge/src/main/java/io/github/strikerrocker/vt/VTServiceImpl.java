package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.world.SelfPlanting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

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

    @Override
    public int getSelfPlantingInterval() {
        return SelfPlanting.selfPlantingInterval.get();
    }

    @Override
    public Player getFakePlayer(ServerLevel serverLevel) {
        // TODO Return Fake Player ( Not available in forge now )
        return null;
    }
}
