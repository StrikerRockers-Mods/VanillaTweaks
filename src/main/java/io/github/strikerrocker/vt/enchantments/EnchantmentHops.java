package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentHops extends Enchantment {

    EnchantmentHops(String name) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        event.getEntityLiving().motionY += ((double) EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)) / 10);
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        event.setDistance(event.getDistance() - EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)));
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
}
