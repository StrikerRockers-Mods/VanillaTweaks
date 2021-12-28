package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;

public class Sickle extends Feature {

    @Override
    public void initialize() {
        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            TweaksImpl.triggerSickle(player, player.getItemInHand(hand), world, blockPos, world.getBlockState(blockPos), VanillaTweaksFabric.config.tweaks.hoeActsAsSickle);
            return InteractionResult.PASS;
        });
    }
}