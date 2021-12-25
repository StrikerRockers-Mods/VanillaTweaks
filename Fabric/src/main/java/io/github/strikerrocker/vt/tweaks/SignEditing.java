package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class SignEditing extends Feature {

    /**
     * Open sign editor even when its already placed
     */
    @Override
    public void initialize() {
        UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            BlockEntity te = world.getBlockEntity(blockHitResult.getBlockPos());
            if (te instanceof SignBlockEntity sign && VanillaTweaksFabric.config.tweaks.enableSignEditing && !world.isClientSide && player.isShiftKeyDown() && player.getItemInHand(hand).isEmpty()) {
                sign.setEditable(true);
                player.openTextEdit(sign);
                player.swing(hand);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}
