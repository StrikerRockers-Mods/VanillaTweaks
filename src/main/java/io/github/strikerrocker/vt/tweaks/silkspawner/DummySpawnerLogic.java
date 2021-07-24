package io.github.strikerrocker.vt.tweaks.silkspawner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;

public class DummySpawnerLogic extends BaseSpawner {
    static final DummySpawnerLogic DUMMY_SPAWNER_LOGIC = new DummySpawnerLogic();

    @Override
    public void broadcastEvent(Level p_151322_, BlockPos p_151323_, int p_151324_) {

    }
}
