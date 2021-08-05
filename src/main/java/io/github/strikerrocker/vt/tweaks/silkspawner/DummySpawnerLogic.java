package io.github.strikerrocker.vt.tweaks.silkspawner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;

public class DummySpawnerLogic extends BaseSpawner {
    static final DummySpawnerLogic DUMMY_SPAWNER_LOGIC = new DummySpawnerLogic();

    @Override
    public void broadcastEvent(Level level, BlockPos blockPos, int id) {

    }
}
