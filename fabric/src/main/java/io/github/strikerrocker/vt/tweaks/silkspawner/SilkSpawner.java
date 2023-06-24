package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;
import io.github.strikerrocker.vt.tweaks.TweaksImpl;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.world.entity.player.Player;

public class SilkSpawner extends Feature {

    @Override
    public void initialize() {
        BlockPlaceCallback.EVENT.register((level, pos, blockState, entity, stack) -> {
            if (entity instanceof Player)
                TweaksImpl.triggerSpawnerPlacement(level, pos, stack, VanillaTweaksFabric.config.tweaks.enableSilkSpawner);
        });
        PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> TweaksImpl.triggerSpawnerBreak(level, pos, state, player, VanillaTweaksFabric.config.tweaks.enableSilkSpawner, false));
    }
}