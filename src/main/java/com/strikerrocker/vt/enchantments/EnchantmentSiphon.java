package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Gives you the drops directly when harvesting blocks
 */
@SuppressWarnings("unused")
public class EnchantmentSiphon extends Enchantment {
    public EnchantmentSiphon() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("siphon");
        this.setName("siphon");
    }

    public int getMinimumEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    public int getMaximumEnchantability(int enchantmentLevel) {
        return 61;
    }
}
