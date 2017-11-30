package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Draws experience points and item entities toward the wearer
 */
@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentVeteran extends Enchantment {
    public EnchantmentVeteran() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD, new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
        this.setRegistryName("veteran");
        this.setName("veteran");
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    public int getMinimumEnchantability(int enchantmentLevel) {
        return 10;
    }

    public int getMaximumEnchantability(int enchantmentLevel) {
        return 40;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }
}
