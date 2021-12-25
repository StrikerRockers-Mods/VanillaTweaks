package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class NimbleEnchantment extends Enchantment {

    private static final UUID nimbleUUID = UUID.fromString("05b61a62-ae84-492e-8536-f365b7143296");

    NimbleEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    /**
     * Handles the logic of Nimble enchantment
     *
     * @param event LivingEquipmentChangeEvent
     */
    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentInit.enableNimble.get()) {
            EnchantmentImpl.triggerNimble(event.getEntityLiving(), EnchantmentInit.NIMBLE.get());
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentInit.enableNimble.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.FEET) &&
                EnchantmentInit.enableNimble.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableNimble.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.nimbleTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableNimble.get();
    }
}