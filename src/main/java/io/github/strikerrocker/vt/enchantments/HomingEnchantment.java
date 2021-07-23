package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class HomingEnchantment extends Enchantment {
    private final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    HomingEnchantment(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world != null && !event.world.isClientSide() && EnchantmentFeature.enableHoming.get()) {
            ServerWorld world = (ServerWorld) event.world;
            world.getEntities(EntityType.ARROW, EntityPredicates.ENTITY_STILL_ALIVE).forEach(entity -> attemptToMove(entity, world));
        }
    }

    private void attemptToMove(Entity arrowEntity, ServerWorld world) {
        AbstractArrowEntity arrow = (AbstractArrowEntity) arrowEntity;
        LivingEntity shooter = (LivingEntity) arrow.getOwner();
        if (shooter != null && EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem()) > 0) {
            int homingLevel = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem());
            double distance = Math.pow(2, (double) homingLevel - 1) * 32;
            List<Entity> livingEntities = world.getEntities().collect(Collectors.toList());
            LivingEntity target = null;
            for (Entity entity : livingEntities) {
                double distanceToArrow = entity.distanceTo(arrow);
                if (entity instanceof LivingEntity && distanceToArrow < distance && shooter.canSee(entity) && !entity.getUUID().equals(shooter.getUUID())) {
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
