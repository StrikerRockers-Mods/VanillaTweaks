package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class HomingEnchantment extends ConfigEnchantment {
    public HomingEnchantment(Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, enableEnchantment, treasureOnly);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return (enchantmentLevel - 1) * 10 + 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return enableEnchantment.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof BowItem && enableEnchantment.get();
    }

    @Override
    public boolean isDiscoverable() {
        return enableEnchantment.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return treasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return enableEnchantment.get();
    }
}