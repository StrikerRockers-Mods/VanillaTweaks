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
public class VigorEnchantment extends Enchantment {
    private static final UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");

    VigorEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    /**
     * Handles the logic of Vigor enchantment
     *
     * @param event LivingEquipmentChangeEvent
     */
    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentInit.enableVigor.get()) {
            EnchantmentImpl.triggerVigor(event.getEntityLiving(), EnchantmentInit.VIGOR.get());
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 10 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentInit.enableVigor.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.CHEST) &&
                EnchantmentInit.enableVigor.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableVigor.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.vigorTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableVigor.get();
    }
}
