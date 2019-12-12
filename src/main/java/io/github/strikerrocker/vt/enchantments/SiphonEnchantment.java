package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import java.util.List;

public class SiphonEnchantment extends Enchantment {
    SiphonEnchantment(String name) {
        super(Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    public static void harvestDropEvent(List<ItemStack> drops, PlayerEntity playerEntity, ItemStack tool) {
        if (EnchantmentFeature.enableSiphon.get()) {
            drops.removeIf(playerEntity::addItemStackToInventory);
        }
        //TODO remove asm when https://github.com/MinecraftForge/MinecraftForge/pull/5871 is merged
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 61;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableSiphon.get() ? 1 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableSiphon.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableSiphon.get();
    }
}
