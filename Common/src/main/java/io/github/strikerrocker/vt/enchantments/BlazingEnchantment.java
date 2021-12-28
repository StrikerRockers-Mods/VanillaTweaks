package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.Supplier;

public class BlazingEnchantment extends ConfigEnchantment {
    public BlazingEnchantment(Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, enableEnchantment, treasureOnly);
    }

    @Override
    public int getMaxLevel() {
        return enableEnchantment.get() ? 1 : 0;
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return 61;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.SILK_TOUCH;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof DiggerItem && enableEnchantment.get();
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