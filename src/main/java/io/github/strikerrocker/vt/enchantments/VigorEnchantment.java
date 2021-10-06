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
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.VIGOR.get(), event.getEntityLiving().getItemBySlot(EquipmentSlot.CHEST));
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
