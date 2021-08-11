package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.misc.ConeShape;
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

import java.util.List;

public class HomingEnchantment extends Enchantment {

    public HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.setRegistryName("homing");
    }

    /**
     * Handles the logic of Homing enchantment
     *
     * @param event EntityJoinWorldEvent
     */
    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentFeature.enableHoming.get() &&
                event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            ServerLevel level = (ServerLevel) event.getWorld();
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getUseItem());
            if (lvl > 0) {
                LivingEntity target = null;
                AABB coneBound = ConeShape.getConeBounds(shooter, lvl);
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
        return EnchantmentFeature.enableHoming.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof BowItem && EnchantmentFeature.enableHoming.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentFeature.enableHoming.get();
    }
}
