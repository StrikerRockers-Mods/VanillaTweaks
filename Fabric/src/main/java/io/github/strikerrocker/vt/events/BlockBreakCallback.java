package io.github.strikerrocker.vt.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockBreakCallback {
    Event<BlockBreakCallback> EVENT = EventFactory.createArrayBacked(BlockBreakCallback.class,
            listeners -> ((world, pos, blockState, player) -> {
                for (BlockBreakCallback blockBreakCallback : listeners) {
                    blockBreakCallback.onBreak(world, pos, blockState, player);
                }
            })
    );

    void onBreak(Level world, BlockPos pos, BlockState blockState, Player playerEntity);
}