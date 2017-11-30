package com.strikerrocker.vt.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentHoming extends Enchantment {


    public EnchantmentHoming() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("homing");
        this.setName("homing");
    }


    public int getMinimumEnchantability(int enchantmentLevel) {
        return (enchantmentLevel - 1) * 10 + 10;
    }


    public int getMaximumEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }

}
