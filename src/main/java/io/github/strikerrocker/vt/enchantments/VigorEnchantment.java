package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
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

import java.util.UUID;

public class VigorEnchantment extends Enchantment {
    private static final UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");

    VigorEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
        this.setRegistryName("vigor");
    }

    /**
     * Handles the logic of Vigor enchantment
     *
     * @param event LivingEquipmentChangeEvent
     */
    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentFeature.enableVigor.get()) {
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(this, event.getEntityLiving().getItemBySlot(EquipmentSlot.CHEST));
            AttributeInstance vigorAttribute = event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "vigor", (float) lvl / 10, AttributeModifier.Operation.MULTIPLY_BASE);
            if (vigorAttribute != null) {
                if (lvl > 0) {
                    if (vigorAttribute.getModifier(vigorUUID) == null) {
                        vigorAttribute.addPermanentModifier(vigorModifier);
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
        return EnchantmentFeature.enableVigor.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.CHEST) && EnchantmentFeature.enableVigor.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableVigor.get();
    }
}
