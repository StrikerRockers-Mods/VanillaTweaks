package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class HomingEnchantment extends Enchantment {
    private AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    HomingEnchantment(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world != null && !event.world.isRemote() && EnchantmentFeature.enableHoming.get()) {
            ServerWorld world = (ServerWorld) event.world;
            world.getEntities(EntityType.ARROW, EntityPredicates.IS_ALIVE).forEach(entity -> attemptToMove(entity, world));
        }
        //TODO doesnt work
    }

    private void attemptToMove(Entity arrowEntity, ServerWorld world) {
        ArrowEntity arrow = (ArrowEntity) arrowEntity;
        LivingEntity shooter = (LivingEntity) arrow.getShooter();
        if (shooter != null && EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand()) > 0) {
            int homingLevel = EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand());
            double distance = Math.pow(2, (double) homingLevel - 1) * 32;
            List<Entity> livingEntities = world.getEntities().collect(Collectors.toList());
            LivingEntity target = null;
            for (Entity entity : livingEntities) {
                double distanceToArrow = entity.getDistance(arrow);
                if (entity instanceof LivingEntity && distanceToArrow < distance && shooter.canEntityBeSeen(entity) && !entity.getUniqueID().equals(shooter.getUniqueID())) {
                    distance = distanceToArrow;
                    target = (LivingEntity) entity;
                }
            }
            if (target != null) {
                double x = target.posX - arrow.posX;
                double y = target.getBoundingBox().minY + target.getHeight() / 2 - (arrow.posY + arrow.getHeight() / 2);
                double z = target.posZ - arrow.posZ;
                arrow.shoot(x, y, z, (float) Math.sqrt(Math.pow(2, arrow.getMotion().x) + Math.pow(2, arrow.getMotion().y) + Math.pow(2, arrow.getMotion().z)), 0);
            }
        }
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return (enchantmentLevel - 1) * 10 + 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableHoming.get() ? 3 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof BowItem && EnchantmentFeature.enableHoming.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableHoming.get();
    }
}
