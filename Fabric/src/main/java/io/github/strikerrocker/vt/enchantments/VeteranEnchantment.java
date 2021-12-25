package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinCost(int level) {
        return 10;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
    }

    @Override
    public int getMaxLevel() {
        return VanillaTweaksFabric.config.enchanting.enableVeteran ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.HEAD) &&
                VanillaTweaksFabric.config.enchanting.enableVeteran;
    }

    @Override
    public boolean isDiscoverable() {
        return VanillaTweaksFabric.config.enchanting.enableVeteran;
    }

    @Override
    public boolean isTreasureOnly() {
        return VanillaTweaksFabric.config.enchanting.veteranTreasureOnly;
    }

    @Override
    public boolean isTradeable() {
        return VanillaTweaksFabric.config.enchanting.enableVeteran;
    }
}
