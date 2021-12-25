package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HomingEnchantment extends Enchantment {

    HomingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return (level - 1) * 10 + 10;
    }

    @Override
    public int getMaxCost(int level) {
        return level * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return VanillaTweaksFabric.config.enchanting.enableHoming ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof BowItem && VanillaTweaksFabric.config.enchanting.enableHoming;
    }

    @Override
    public boolean isDiscoverable() {
        return VanillaTweaksFabric.config.enchanting.enableHoming;
    }

    @Override
    public boolean isTreasureOnly() {
        return VanillaTweaksFabric.config.enchanting.homingTreasureOnly;
    }

    @Override
    public boolean isTradeable() {
        return VanillaTweaksFabric.config.enchanting.enableHoming;
    }
}
