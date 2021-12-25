package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public class ShearNameTag extends Feature {

    /**
     * Make the nametag shear-able
     */
    @Override
    public void initialize() {
        UseEntityCallback.EVENT.register(((player, world, hand, target, entityHitResult) -> {
            ItemStack heldItem = player.getItemInHand(hand);
            TweaksImpl.triggerShearNametag(player, heldItem, target, world, VanillaTweaksFabric.config.tweaks.shearOffNameTag);
            return InteractionResult.PASS;
        }));
    }
}