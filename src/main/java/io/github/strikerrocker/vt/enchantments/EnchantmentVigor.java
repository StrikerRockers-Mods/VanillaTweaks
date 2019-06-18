package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentVigor extends Enchantment {
    private static final UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");

    EnchantmentVigor(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST));
        IAttributeInstance vigorAttribute = event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "vigor", (float) lvl / 10, 1);
        if (lvl > 0) {
            if (vigorAttribute.getModifier(vigorUUID) == null) {
                vigorAttribute.applyModifier(vigorModifier);
            }
        } else {
            if (vigorAttribute.getModifier(vigorUUID) != null) {
                vigorAttribute.removeModifier(vigorModifier);
                if (event.getEntityLiving().getHealth() > event.getEntityLiving().getMaxHealth())
                    event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
            }
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
        return stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot().equals(EntityEquipmentSlot.CHEST);
    }
}
