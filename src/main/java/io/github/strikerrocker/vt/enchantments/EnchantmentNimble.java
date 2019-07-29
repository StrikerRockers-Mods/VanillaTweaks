package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentNimble extends Enchantment {

    private static final UUID nimbleUUID = UUID.fromString("05b61a62-ae84-492e-8536-f365b7143296");

    EnchantmentNimble(String name) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(this, entity.getItemStackFromSlot(EntityEquipmentSlot.FEET));
        IAttributeInstance speedAttribute = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", (float) enchantmentLevel * 0.20000000298023224, 2);
        if (enchantmentLevel > 0) {
            if (speedAttribute.getModifier(nimbleUUID) == null) {
                speedAttribute.applyModifier(speedModifier);
            }
        } else if (speedAttribute.getModifier(nimbleUUID) != null) {
            speedAttribute.removeModifier(speedModifier);
        }
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType.equals(EntityEquipmentSlot.FEET);
    }
}