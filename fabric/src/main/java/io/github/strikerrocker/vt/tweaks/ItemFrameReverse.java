package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public class ItemFrameReverse extends Feature {

    @Override
    public void initialize() {
        UseEntityCallback.EVENT.register(((player, world, hand, target, entityHitResult) -> {
            if (TweaksImpl.triggerItemFrameReverse(target, player, VanillaTweaksFabric.config.tweaks.itemFrameRotateBackwards)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }));
    }
}