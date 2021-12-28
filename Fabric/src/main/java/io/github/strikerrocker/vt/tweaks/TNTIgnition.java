package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;

public class TNTIgnition extends Feature {

    @Override
    public void initialize() {
        BlockPlaceCallback.EVENT.register((level, pos, blockState, entity, stack) -> {
            TweaksImpl.triggerTNTIgnition(level, pos, blockState, VanillaTweaksFabric.config.tweaks.tntIgnition);
        });
    }
}