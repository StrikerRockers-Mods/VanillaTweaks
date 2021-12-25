package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VigorEnchantment extends Enchantment {

    VigorEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
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
        return VanillaTweaksFabric.config.enchanting.enableVigor ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.CHEST) &&
                VanillaTweaksFabric.config.enchanting.enableVigor;
    }

    @Override
    public boolean isDiscoverable() {
        return VanillaTweaksFabric.config.enchanting.enableVigor;
    }

    @Override
    public boolean isTreasureOnly() {
        return VanillaTweaksFabric.config.enchanting.vigorTreasureOnly;
    }

    @Override
    public boolean isTradeable() {
        return VanillaTweaksFabric.config.enchanting.enableVigor;
    }
}
