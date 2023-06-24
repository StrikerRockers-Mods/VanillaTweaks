package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class HopsEnchantment extends ConfigEnchantment {
    public HopsEnchantment(Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET}, enableEnchantment, treasureOnly);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
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
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.FEET) && enableEnchantment.get();
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