package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * d
 * Gives the wearer an extra heart per enchantment level
 */
@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentVigor extends Enchantment {
    private static UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");

    public EnchantmentVigor() {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
        this.setRegistryName("vigor");
        this.setName("vigor");
    }


    public int getMinimumEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }


    public int getMaximumEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }


    public int getMaxLevel() {
        return 3;
    }

    /**
     * Adds the vigor buff to the specified living entity at the specified enchantment level
     *
     * @param livingEntity     The living entity to add the vigor buff to
     * @param enchantmentLevel The enchantment level of the vigor buff
     */
    private void addVigorBuff(EntityLivingBase livingEntity, int enchantmentLevel) {
        IAttributeInstance vigorAttribute = livingEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        if (vigorAttribute.getModifier(vigorUUID) == null) {
            AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "Vigor", (float) enchantmentLevel / 10, 1);
            vigorAttribute.applyModifier(vigorModifier);
        }
    }

    /**
     * Removes the vigor buff from the specified living entity at the specified enchantment level
     *
     * @param livingEntity     The living entity to remove the vigor buff from
     * @param enchantmentLevel The enchantment level of the vigor buff
     */
    private void removeVigorBuff(EntityLivingBase livingEntity, int enchantmentLevel) {
        IAttributeInstance vigorAttribute = livingEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        if (vigorAttribute.getModifier(vigorUUID) != null) {
            AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "Vigor", (float) enchantmentLevel / 10, 1);
            vigorAttribute.removeModifier(vigorModifier);
            if (livingEntity.getHealth() > livingEntity.getMaxHealth())
                livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }

}
