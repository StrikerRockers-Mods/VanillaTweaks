package io.github.strikerrocker.vt.enchantments.homing;

import com.google.common.collect.Lists;
import io.github.strikerrocker.vt.enchantments.EnchantmentFeature;
import net.minecraft.core.BlockPos;
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

    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentFeature.enableHoming.get() && event.getEntity() instanceof AbstractArrow arrow) {
            onArrowJoinWorld(arrow, (ServerLevel) event.getWorld());
        }
    }

    /**
     * Handles the logic of Homing enchantment
     *
     * @param arrow The arrow entity
     * @param level The server level
     */
    private void onArrowJoinWorld(AbstractArrow arrow, ServerLevel level) {
        LivingEntity shooter = (LivingEntity) arrow.getOwner();
        if (shooter != null && EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem()) > 0) {
            int homingLevel = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem());
            LivingEntity target = null;
            AABB coneBound = ConeShape.getConeBounds(shooter, homingLevel);
            List<Entity> livingEntities = Lists.newArrayList();
            level.getEntities().get(coneBound, (entity -> {
                if (!entity.getUUID().equals(shooter.getUUID())) {
                    livingEntities.add(entity);
                }
            }));
            for (Entity entity : livingEntities) {
                if (entity instanceof LivingEntity && shooter.hasLineOfSight(entity)) {
                    target = (LivingEntity) entity;
                }
            }
            System.out.println(target);
            if (target != null) {
                BlockPos arrowPos = arrow.blockPosition();
                BlockPos targetPos = target.blockPosition();
                double x = targetPos.getX() - arrowPos.getX();
                double y = target.getEyeY() - arrowPos.getY();
                double z = targetPos.getZ() - arrowPos.getZ();
                arrow.shoot(x, y, z, (float) Math.sqrt(Math.pow(arrow.getDeltaMovement().x, 2) + Math.pow(arrow.getDeltaMovement().y, 2) + Math.pow(arrow.getDeltaMovement().z, 2)), 0);
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
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableHoming.get();
    }
}
