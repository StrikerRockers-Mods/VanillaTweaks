package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class VigorEnchantment extends Enchantment {
    private static final UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");

    VigorEnchantment(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{EquipmentSlotType.CHEST});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentFeature.enableVigor.get()) {
            int lvl = EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.CHEST));
            IAttributeInstance vigorAttribute = event.getEntityLiving().getAttributes().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
            AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "vigor", (float) lvl / 10, AttributeModifier.Operation.MULTIPLY_BASE);
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
        return EnchantmentFeature.enableVigor.get() ? 3 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.CHEST) && EnchantmentFeature.enableVigor.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableVigor.get();
    }
}
