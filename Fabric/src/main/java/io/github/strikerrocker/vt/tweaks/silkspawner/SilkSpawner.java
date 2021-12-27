package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.BlockBreakCallback;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;
import io.github.strikerrocker.vt.tweaks.TweaksImpl;
import net.minecraft.world.entity.player.Player;

public class SilkSpawner extends Feature {

    @Override
    public void initialize() {
        BlockPlaceCallback.EVENT.register((level, pos, blockState, entity, stack) -> {
            if (entity instanceof Player)
                TweaksImpl.triggerSpawnerPlacement(level, pos, stack, VanillaTweaksFabric.config.tweaks.enableSilkSpawner);
        });
        BlockBreakCallback.EVENT.register((level, pos, state, player) -> TweaksImpl.triggerSpawnerBreak(level, pos, state, player, VanillaTweaksFabric.config.tweaks.enableSilkSpawner));
    }
}