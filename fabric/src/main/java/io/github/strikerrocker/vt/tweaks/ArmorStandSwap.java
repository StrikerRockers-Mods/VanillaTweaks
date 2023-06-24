package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public class ArmorStandSwap extends Feature {

    @Override
    public void initialize() {
        UseEntityCallback.EVENT.register(((player, level, hand, target, entityHitResult) -> {
            if (TweaksImpl.triggerArmorStandSwap(player, target, VanillaTweaksFabric.config.tweaks.enableArmorStandSwapping))
                return InteractionResult.SUCCESS;
            return InteractionResult.PASS;
        }));
    }
}