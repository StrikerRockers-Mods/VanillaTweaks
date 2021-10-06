package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class SiphonEnchantment extends Enchantment {
    SiphonEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName("siphon");
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
        return EnchantmentFeature.enableSiphon.get() ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableSiphon.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableSiphon.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentFeature.enableSiphon.get();
    }
}
