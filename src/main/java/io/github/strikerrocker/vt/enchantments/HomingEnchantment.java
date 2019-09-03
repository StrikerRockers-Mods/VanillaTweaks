package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class HomingEnchantment extends Enchantment {
    private AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    HomingEnchantment(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world != null && EnchantmentFeature.enableHoming.get()) {
            for (PlayerEntity player : event.world.getPlayers()) {
                for (ArrowEntity arrowEntity : event.world.getEntitiesWithinAABB(ArrowEntity.class, player.getBoundingBox().grow(128), EntityPredicates.IS_ALIVE)) {
                    attemptToMove(arrowEntity);
                }
            }
        }
    }

    private void attemptToMove(ArrowEntity arrow) {
        LivingEntity shooter = (LivingEntity) arrow.getShooter();
        if (shooter != null && EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand()) > 0) {
            int homingLevel = EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand());
            double distance = Math.pow(2, (double) homingLevel - 1) * 32;
            World world = arrow.world;
            List<LivingEntity> livingEntities = world.getEntitiesWithinAABB(LivingEntity.class, ZERO_AABB.grow(distance, distance, distance), EntityPredicates.NOT_SPECTATING);
            LivingEntity target = null;
            for (LivingEntity livingEntity : livingEntities) {
                double distanceToArrow = livingEntity.getDistance(arrow);
                if (distanceToArrow < distance && shooter.canEntityBeSeen(livingEntity) && !livingEntity.getUniqueID().equals(shooter.getUniqueID())) {
                    distance = distanceToArrow;
                    target = livingEntity;
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
