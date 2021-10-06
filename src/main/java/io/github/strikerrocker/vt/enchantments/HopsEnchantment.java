package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HopsEnchantment extends Enchantment {

    HopsEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    /**
     * Handles the logic of Hops enchantment
     *
     * @param event LivingEquipmentChangeEvent
     */
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EnchantmentInit.enableHops.get() && !event.getEntity().level.isClientSide()) {
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.HOPS.get(), event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET));
            if (lvl > 0) {
                if (!entity.hasEffect(MobEffects.JUMP)) {
                    entity.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, lvl, true, false, false));
                }
            } else {
                if (entity.hasEffect(MobEffects.JUMP)) {
                    entity.removeEffect(MobEffects.JUMP);
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
        return EnchantmentInit.enableHops.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.FEET) && EnchantmentInit.enableHops.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableHops.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.hopsTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableHops.get();
    }
}
