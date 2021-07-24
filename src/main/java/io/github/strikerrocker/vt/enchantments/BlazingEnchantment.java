package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class BlazingEnchantment extends Enchantment {
    BlazingEnchantment(String name) {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.setRegistryName(name);
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableBlazing.get() ? 1 : 0;
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
    protected boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.SILK_TOUCH && ench != Enchantments.BLOCK_FORTUNE;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof DiggerItem && EnchantmentFeature.enableBlazing.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableBlazing.get();
    }

}