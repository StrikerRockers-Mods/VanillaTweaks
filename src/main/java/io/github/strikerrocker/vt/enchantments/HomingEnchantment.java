package io.github.strikerrocker.vt.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class HomingEnchantment extends Enchantment {
    private final AABB ZERO_AABB = new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.setRegistryName("homing");
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world != null && !event.world.isClientSide() && EnchantmentFeature.enableHoming.get()) {
            ServerLevel world = (ServerLevel) event.world;
            world.getEntities(EntityType.ARROW, EntitySelector.ENTITY_STILL_ALIVE).forEach(entity -> attemptToMove(entity, world));
        }
    }

    private void attemptToMove(Entity arrowEntity, ServerLevel world) {
        AbstractArrow arrow = (AbstractArrow) arrowEntity;
        LivingEntity shooter = (LivingEntity) arrow.getOwner();
        if (shooter != null && EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem()) > 0) {
            int homingLevel = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem());
            double distance = Math.pow(2, (double) homingLevel - 1) * 32;
            LivingEntity target = null;
            List<Entity> livingEntities = new ArrayList<>();
            world.getEntities().getAll().forEach(livingEntities::add);
            for (Entity entity : livingEntities) {
                double distanceToArrow = entity.distanceTo(arrow);
                if (entity instanceof LivingEntity && distanceToArrow < distance && shooter.hasLineOfSight(entity) && !entity.getUUID().equals(shooter.getUUID())) {
                    distance = distanceToArrow;
                    target = (LivingEntity) entity;
                }
            }
            if (target != null) {
                BlockPos arrowPos = arrow.blockPosition();
                BlockPos targetPos = target.blockPosition();
                double x = targetPos.getX() - arrowPos.getX();
                double y = target.getBoundingBox().minY + target.getBbHeight() / 2 - (arrowPos.getY() + arrow.getBbHeight() / 2);
                double z = targetPos.getZ() - arrowPos.getZ();
                arrow.shoot(x, y, z, (float) Math.sqrt(Math.pow(2, arrow.getDeltaMovement().x) + Math.pow(2, arrow.getDeltaMovement().y) + Math.pow(2, arrow.getDeltaMovement().z)), 0);
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
