package io.github.strikerrocker.vt.tweaks.silkspawner;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DummyMobSpawnerLogic extends MobSpawnerBaseLogic {
    static final DummyMobSpawnerLogic DUMMY_MOB_SPAWNER_LOGIC = new DummyMobSpawnerLogic();

    @Override
    public void broadcastEvent(int id) {

    }

    @Override
    public World getSpawnerWorld() {
        return Minecraft.getMinecraft().world;
    }

    @Override
    public BlockPos getSpawnerPosition() {
        return new BlockPos(0, 0, 0);
    }
}
