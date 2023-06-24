package io.github.strikerrocker.vt;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

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

    @Override
    public int getSelfPlantingInterval() {
        return VanillaTweaksFabric.config.world.selfPlantingInterval;
    }

    @Override
    public Player getFakePlayer(ServerLevel serverLevel) {
        return FakePlayer.get(serverLevel);
    }

}