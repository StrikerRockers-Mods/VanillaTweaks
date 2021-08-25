package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class HomingEnchantment extends Enchantment {

    public HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /**
     * Handles the logic of Homing enchantment
     *
     * @param event EntityJoinWorldEvent
     */
    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentInit.enableHoming.get() &&
                event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            ServerLevel level = (ServerLevel) event.getWorld();
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.HOMING.get(), shooter.getUseItem());
            if (lvl > 0) {
                LivingEntity target = null;
                AABB coneBound = Utils.getConeBounds(shooter, lvl);
                List<Entity> potentialTarget = level.getEntities(shooter, coneBound);
                for (Entity entity : potentialTarget) {
                    if (entity instanceof LivingEntity livingEntity && shooter.hasLineOfSight(entity)) {
                        target = livingEntity;
                    }
                }
                if (target != null) {
                    double x = target.getX() - arrow.getX();
                    double y = target.getEyeY() - arrow.getY();
                    double z = target.getZ() - arrow.getZ();
                    arrow.shoot(x, y, z, (float) arrow.getDeltaMovement().length(), 0);
                }
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
}
