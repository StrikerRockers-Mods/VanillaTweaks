package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class VigorEnchantment extends ConfigEnchantment {
    public VigorEnchantment(Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST}, enableEnchantment, treasureOnly);
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 10;
    }

    @Override
    public int getMaxCost(int level) {
        return level * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return enableEnchantment.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.CHEST) &&
                enableEnchantment.get();
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