package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HopsEnchantment extends Enchantment {

    HopsEnchantment(String name) {
        super(Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        Vec3d vec3d = event.getEntityLiving().getMotion();
        event.getEntityLiving().setMotion(vec3d.x, vec3d.y + EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET)) / 10, vec3d.z);
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        event.setDistance(event.getDistance() - EnchantmentHelper.getEnchantmentLevel(this, event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET)));
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
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.FEET);
    }
}
