package io.github.strikerrocker.vt.misc;

import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class Utils {
    private Utils() {
    }

    public static FakePlayer getFakePlayer(World world) {
        if (world instanceof ServerWorld)
            return FakePlayerFactory.getMinecraft((ServerWorld) world);
        return null;
    }
}
