package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class HomingEnchantment extends Enchantment {

    public HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /**
     * Returns the target entity for homing enchantment
     */
    @Nullable
    private static LivingEntity getTarget(Level level, LivingEntity shooter, int homingLevel) {
        LivingEntity target = null;
        AABB coneBound = Utils.getConeBoundApprox(shooter, homingLevel);
        List<Entity> potentialTargets = level.getEntities(shooter, coneBound);
        for (Entity entity : potentialTargets) {
            if (entity instanceof LivingEntity livingEntity && shooter.hasLineOfSight(entity)) {
                if (livingEntity instanceof OwnableEntity tamed && tamed.getOwnerUUID() == shooter.getUUID()) continue;
                target = livingEntity;
            }
        }
        VanillaTweaks.LOGGER.debug(coneBound);
        VanillaTweaks.LOGGER.debug(target);
        return target;
    }

    /**
     * Retargets the arrow towards the targeted entity
     */
    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentInit.enableHoming.get() &&
                event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.HOMING.get(), shooter.getUseItem());
            if (lvl > 0) {
                LivingEntity target = getTarget(event.getWorld(), shooter, lvl);
                if (target != null) {
                    double x = target.getX() - arrow.getX();
                    double y = target.getEyeY() - arrow.getY();
                    double z = target.getZ() - arrow.getZ();
                    arrow.setNoGravity(true);
                    arrow.shoot(x, y, z, (float) arrow.getDeltaMovement().length(), 0);
                }
            }
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
                LivingEntity target = getTarget(event.getEntityLiving().getCommandSenderWorld(), player, homingLvl);
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
