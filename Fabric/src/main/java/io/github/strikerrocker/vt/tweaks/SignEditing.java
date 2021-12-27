package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SignEditing extends Feature {

    @Override
    public void initialize() {
        UseBlockCallback.EVENT.register((player, level, hand, blockHitResult) -> {
            BlockEntity be = level.getBlockEntity(blockHitResult.getBlockPos());
            if (TweaksImpl.triggerSignEditing(level, player, be, hand, VanillaTweaksFabric.config.tweaks.enableSignEditing))
                return InteractionResult.SUCCESS;
            else
                return InteractionResult.PASS;
        });
    }
}
