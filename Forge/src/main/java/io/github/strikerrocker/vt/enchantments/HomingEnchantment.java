package io.github.strikerrocker.vt.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HomingEnchantment extends Enchantment {

    public HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /**
     * Retargets the arrow towards the targeted entity
     */
    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentInit.enableHoming.get() &&
                event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            EnchantmentImpl.triggerHoming(arrow, shooter, EnchantmentInit.HOMING.get());
        }
    }

    /**
     * Adds the glowing effect to the targeting entity when using the bow
     */
    @SubscribeEvent
    public static void useItem(LivingEntityUseItemEvent event) {
        if (!event.getEntityLiving().getCommandSenderWorld().isClientSide()) {
            LivingEntity player = event.getEntityLiving();
            int homingLvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.HOMING.get(), event.getItem());
            if (homingLvl > 0) {
                LivingEntity target = EnchantmentImpl.getHomingTarget(event.getEntityLiving().getCommandSenderWorld(), player, homingLvl);
                if (target != null)
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 1, true, false, false));
            }
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return (enchantmentLevel - 1) * 10 + 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentInit.enableHoming.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof BowItem && EnchantmentInit.enableHoming.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableHoming.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.homingTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableHoming.get();
    }
}