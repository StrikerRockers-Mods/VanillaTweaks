package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class SiphonEnchantment extends ConfigEnchantment {
    public SiphonEnchantment(Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, enableEnchantment, treasureOnly);
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
    public int getMaxLevel() {
        return enableEnchantment.get() ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof DiggerItem && enableEnchantment.get();
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