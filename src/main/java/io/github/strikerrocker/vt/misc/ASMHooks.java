package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.enchantments.BlazingEnchantment;
import io.github.strikerrocker.vt.enchantments.EnchantmentFeature;
import io.github.strikerrocker.vt.enchantments.SiphonEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;

public class ASMHooks {
    public static List<ItemStack> getdrops(LootTable lootTable, LootContext lootContext) {
        List<ItemStack> drops = lootTable.generate(lootContext);
        return modifyDrops(drops, lootContext);
    }

    public static List<ItemStack> modifyDrops(List<ItemStack> original, LootContext context) {

        Entity entity = context.get(LootParameters.THIS_ENTITY);
        ItemStack tool = context.get(LootParameters.TOOL);

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentFeature.enchantments.get("blazing").getA(), tool) > 0)
                BlazingEnchantment.harvestDropEvent(original, player, tool);
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentFeature.enchantments.get("siphon").getA(), tool) > 0)
                SiphonEnchantment.harvestDropEvent(original, player, tool);
        }
        return original;
    }
}
