package io.github.strikerrocker.vt.misc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class Utils {
    /**
     * Returns an FakePlayer for the given Level
     */
    public static FakePlayer getFakePlayer(Level level) {
        if (level instanceof ServerLevel serverLevel)
            return FakePlayerFactory.getMinecraft(serverLevel);
        return null;
    }
}