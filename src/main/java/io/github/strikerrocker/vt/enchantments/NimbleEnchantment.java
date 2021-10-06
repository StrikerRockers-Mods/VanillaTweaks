package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
            LivingEntity entity = event.getEntityLiving();
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.NIMBLE.get(), entity.getItemBySlot(EquipmentSlot.FEET));
            AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", lvl * 0.20000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL);
            if (speedAttribute != null) {
                if (lvl > 0) {
                    if (speedAttribute.getModifier(nimbleUUID) == null) {
                        speedAttribute.addPermanentModifier(speedModifier);
                    }
                } else if (speedAttribute.getModifier(nimbleUUID) != null) {
                    speedAttribute.removeModifier(speedModifier);
                }
            }
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