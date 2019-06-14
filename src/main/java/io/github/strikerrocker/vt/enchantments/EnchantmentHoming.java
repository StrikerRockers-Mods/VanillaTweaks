package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class EnchantmentHoming extends Enchantment {
    EnchantmentHoming(String name) {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world != null) {
            event.world.getEntities(EntityArrow.class, EntitySelectors.IS_ALIVE).forEach(this::attemptToMove);
        }
    }

    private void attemptToMove(EntityArrow arrow) {
        EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
        if (shooter != null && EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand()) > 0) {
            int homingLevel = EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand());
            double distance = Math.pow(2, (double) homingLevel - 1) * 32;
            World world = arrow.world;
            List<EntityLivingBase> livingEntities = world.getEntities(EntityLivingBase.class, EntitySelectors.NOT_SPECTATING);
            EntityLivingBase target = null;
            for (EntityLivingBase livingEntity : livingEntities) {
                double distanceToArrow = livingEntity.getDistance(arrow);
                if (distanceToArrow < distance && shooter.canEntityBeSeen(livingEntity) && !livingEntity.getPersistentID().equals(shooter.getPersistentID())) {
                    distance = distanceToArrow;
                    target = livingEntity;
                }
            }
            if (target != null) {
                double x = target.posX - arrow.posX;
                double y = target.getEntityBoundingBox().minY + target.height / 2 - (arrow.posY + arrow.height / 2);
                double z = target.posZ - arrow.posZ;
                arrow.shoot(x, y, z, 1.25f, 0);
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
        return 3;
    }
}
