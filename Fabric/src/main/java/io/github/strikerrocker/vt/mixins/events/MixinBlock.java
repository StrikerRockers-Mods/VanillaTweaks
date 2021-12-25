package io.github.strikerrocker.vt.mixins.events;

import io.github.strikerrocker.vt.events.BlockBreakCallback;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {
    /**
     * Fires block place callback
     */
    @Inject(method = "setPlacedBy", at = @At("RETURN"))
    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo callbackInfo) {
        BlockPlaceCallback.EVENT.invoker().onPlaced(world, pos, state, placer, itemStack);
    }

    /**
     * Fires block break callback
     */
    @Inject(method = "playerWillDestroy", at = @At("RETURN"))
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player, CallbackInfo callbackInfo) {
        BlockBreakCallback.EVENT.invoker().onBreak(world, pos, state, player);
    }
}
