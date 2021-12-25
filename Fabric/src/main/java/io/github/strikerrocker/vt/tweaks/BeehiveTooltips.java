package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.BeehiveBlock;

public class BeehiveTooltips extends Feature {

    /**
     * Shows the number of bees and honey level of bee hives
     */
    @Override
    public void initialize() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (context.isAdvanced() && stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BeehiveBlock) {
                TweaksImpl.addBeeHiveTooltip(stack, lines);
            }
        });
    }
}