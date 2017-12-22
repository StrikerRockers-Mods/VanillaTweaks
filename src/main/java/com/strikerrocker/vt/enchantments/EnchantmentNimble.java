package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.UUID;

/**
 * Gives the wearer speed
 */
@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentNimble extends VTEnchantmentBase {
    private static UUID nimbleUUID = UUID.fromString("05b61a62-ae84-492e-8536-f365b7143296");

    public EnchantmentNimble() {
        super("nimble", Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_FEET, EntityEquipmentSlot.FEET);
    }

    @Override
    public void performAction(Entity entity, Event baseEvent) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase livingEntity = (EntityLivingBase) entity;
            int enchantmentLevel = this.getEnchantmentLevel(livingEntity.getItemStackFromSlot(EntityEquipmentSlot.FEET));
            if (enchantmentLevel > 0)
                addSpeedBuff(livingEntity, enchantmentLevel);
            else
                removeSpeedBuff(livingEntity, enchantmentLevel);
        }
    }

    @Override
    public int getMinimumEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaximumEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    /**
     * Adds the speed buff to the specified living entity at the specified enchantment level
     *
     * @param livingEntity     The living entity to add the speed buff to
     * @param enchantmentLevel The enchantment level of the speed buff
     */
    private void addSpeedBuff(EntityLivingBase livingEntity, int enchantmentLevel) {
        IAttributeInstance speedAttribute = livingEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (speedAttribute.getModifier(nimbleUUID) == null) {
            AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", (float) enchantmentLevel * 0.20000000298023224, 2);
            speedAttribute.applyModifier(speedModifier);
        }
    }

    /**
     * Removes the speed buff from the specified living entity at the specified enchantment level
     *
     * @param livingEntity     The living entity to remove the speed buff from
     * @param enchantmentLevel The enchantment level of the speed buff
     */
    private void removeSpeedBuff(EntityLivingBase livingEntity, int enchantmentLevel) {
        IAttributeInstance speedAttribute = livingEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (speedAttribute.getModifier(nimbleUUID) != null) {
            AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", (float) enchantmentLevel * 0.20000000298023224, 2);
            speedAttribute.removeModifier(speedModifier);
        }
    }
}
