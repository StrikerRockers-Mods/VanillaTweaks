package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

public class ConfigEnchantment extends Enchantment {
    Supplier<Boolean> enableEnchantment;
    Supplier<Boolean> treasureOnly;

    public ConfigEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots, Supplier<Boolean> enableEnchantment, Supplier<Boolean> treasureOnly) {
        super(rarity, category, slots);
        this.enableEnchantment = enableEnchantment;
        this.treasureOnly = treasureOnly;
    }
}