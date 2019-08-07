package io.github.strikerrocker.vt.misc;

import com.electronwill.nightconfig.core.Config;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.function.Supplier;

public class Utils {
    private Utils() {
    }

    public static FakePlayer getFakePlayer(World world) {
        if (world instanceof ServerWorld)
            return FakePlayerFactory.getMinecraft((ServerWorld) world);
        return null;
    }
}
