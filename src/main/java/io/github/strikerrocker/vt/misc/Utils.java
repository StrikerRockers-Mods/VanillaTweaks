package io.github.strikerrocker.vt.misc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class Utils {
    private Utils() {
    }

    public static FakePlayer getFakePlayer(Level world) {
        if (world instanceof ServerLevel)
            return FakePlayerFactory.getMinecraft((ServerLevel) world);
        return null;
    }
}
