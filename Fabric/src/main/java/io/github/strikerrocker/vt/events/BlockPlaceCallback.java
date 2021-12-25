package io.github.strikerrocker.vt.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            listeners -> ((world, pos, blockState, entity, stack) -> {
                for (BlockPlaceCallback blockPlaceCallback : listeners) {
                    blockPlaceCallback.onPlaced(world, pos, blockState, entity, stack);
                }
            })
    );

    void onPlaced(Level world, BlockPos pos, BlockState blockState, LivingEntity entity, ItemStack stack);
}