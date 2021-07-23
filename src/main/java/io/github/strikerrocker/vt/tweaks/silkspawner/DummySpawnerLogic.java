package io.github.strikerrocker.vt.tweaks.silkspawner;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

public class DummySpawnerLogic extends AbstractSpawner {
    static final DummySpawnerLogic DUMMY_SPAWNER_LOGIC = new DummySpawnerLogic();

    @Override
    public void broadcastEvent(int id) {

    }

    @Override
    public World getLevel() {
        return Minecraft.getInstance().level;
    }

    @Override
    public BlockPos getPos() {
        return new BlockPos(0, 0, 0);
    }
}
