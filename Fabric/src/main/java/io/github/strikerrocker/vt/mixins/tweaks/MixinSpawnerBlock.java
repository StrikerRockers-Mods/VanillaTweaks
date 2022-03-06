package io.github.strikerrocker.vt.mixins.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerBlock.class)
public class MixinSpawnerBlock {

    /*
     * Cancels experience drop when spawner is silk touched
     */
    @Inject(method = "spawnAfterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BaseEntityBlock;spawnAfterBreak(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER), cancellable = true)
    public void cancelXP(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, CallbackInfo ci) {
        if (EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.SILK_TOUCH)
                && VanillaTweaksFabric.config.tweaks.enableSilkSpawner) {
            ci.cancel();
        }
    }
}