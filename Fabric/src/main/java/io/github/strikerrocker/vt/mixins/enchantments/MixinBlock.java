package io.github.strikerrocker.vt.mixins.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.enchantments.BlazingEnchantment;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.enchantments.SiphonEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Blazing and Siphon Functionality
 */
@Mixin(Block.class)
public class MixinBlock {
    @Inject(
            at = @At("RETURN"),
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            cancellable = true)
    private static void dropLoot(BlockState state, ServerLevel world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfoReturnable<List<ItemStack>> ci) {
        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentModule.BLAZING, tool) != 0 && VanillaTweaksFabric.config.enchanting.enableBlazing) {
            ci.setReturnValue(BlazingEnchantment.blazingLogic(world, entity, tool, ci.getReturnValue()));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentModule.SIPHON, tool) != 0 && VanillaTweaksFabric.config.enchanting.enableSiphon) {
            ci.setReturnValue(SiphonEnchantment.siphonLogic(entity, ci.getReturnValue()));
        }
    }
}