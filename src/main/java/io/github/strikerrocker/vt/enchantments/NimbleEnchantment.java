package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class NimbleEnchantment extends Enchantment {

    private static final UUID nimbleUUID = UUID.fromString("05b61a62-ae84-492e-8536-f365b7143296");

    NimbleEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
        this.setRegistryName("nimble");
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentFeature.enableNimble.get()) {
            LivingEntity entity = event.getEntityLiving();
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(this, entity.getItemBySlot(EquipmentSlotType.FEET));
            ModifiableAttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", (float) enchantmentLevel * 0.20000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL);
            if (enchantmentLevel > 0) {
                if (speedAttribute.getModifier(nimbleUUID) == null) {
                    speedAttribute.addPermanentModifier(speedModifier);
                }
            } else if (speedAttribute.getModifier(nimbleUUID) != null) {
                speedAttribute.removeModifier(speedModifier);
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
        return EnchantmentFeature.enableNimble.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlot().equals(EquipmentSlotType.FEET) && EnchantmentFeature.enableNimble.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableNimble.get();
    }
}