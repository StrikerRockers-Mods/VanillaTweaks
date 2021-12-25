package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;

public class ItemFrameReverse extends Feature {

    /**
     * Rotates the item frame in reverse when shift right clicked
     */
    @Override
    public void initialize() {
        UseEntityCallback.EVENT.register(((player, world, hand, target, entityHitResult) -> {
            if (VanillaTweaksFabric.config.tweaks.itemFrameRotateBackwards && target instanceof ItemFrame frame && player.isShiftKeyDown()) {
                int rotation = frame.getRotation() - 1;
                if (rotation < 0)
                    rotation = 7;
                frame.setRotation(rotation);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }));
    }
}
