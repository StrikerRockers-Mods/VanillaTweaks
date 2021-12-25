package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.ArrayList;
import java.util.List;

public class SiphonEnchantment extends Enchantment {
    SiphonEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static List<ItemStack> siphonLogic(Entity entity, List<ItemStack> dropList) {
        if (entity instanceof Player playerEntity) {
            ArrayList<ItemStack> newDropList = new ArrayList<>(dropList);
            newDropList.removeIf(playerEntity::addItem);
            return newDropList;
        }
        return dropList;
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxCost(int level) {
        return 61;
    }

    @Override
    public int getMaxLevel() {
        return VanillaTweaksFabric.config.enchanting.enableSiphon ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof TieredItem && VanillaTweaksFabric.config.enchanting.enableSiphon;
    }

    @Override
    public boolean isDiscoverable() {
        return VanillaTweaksFabric.config.enchanting.enableSiphon;
    }

    @Override
    public boolean isTreasureOnly() {
        return VanillaTweaksFabric.config.enchanting.siphonTreasureOnly;
    }

    @Override
    public boolean isTradeable() {
        return VanillaTweaksFabric.config.enchanting.enableSiphon;
    }
}
