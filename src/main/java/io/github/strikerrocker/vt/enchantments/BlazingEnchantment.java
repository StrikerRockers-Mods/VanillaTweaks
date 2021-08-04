package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class BlazingEnchantment extends Enchantment {
    BlazingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName("blazing");
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
    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.SILK_TOUCH && enchantment != Enchantments.BLOCK_FORTUNE;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableBlazing.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableBlazing.get();
    }

}