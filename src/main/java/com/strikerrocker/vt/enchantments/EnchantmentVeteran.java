package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Draws experience points and item entities toward the wearer
 */
@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentVeteran extends VTEnchantmentBase {
    public EnchantmentVeteran() {
        super("veteran", Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD, EntityEquipmentSlot.HEAD);
    }

    @Override
    public void performAction(Entity entity, Event baseEvent) {
        if (entity instanceof EntityXPOrb) {
            World world = entity.worldObj;
            double range = 32;
            EntityPlayer closestPlayer = world.getClosestPlayerToEntity(entity, range);
            if (closestPlayer != null && this.getEnchantmentLevel(closestPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD)) > 0) {
                double xDiff = (closestPlayer.posX - entity.posX) / range;
                double yDiff = (closestPlayer.posY + closestPlayer.getEyeHeight() - entity.posY) / range;
                double zDiff = (closestPlayer.posZ - entity.posZ) / range;
                double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
                double invertedMovementFactor = 1 - movementFactor;
                if (invertedMovementFactor > 0) {
                    invertedMovementFactor *= invertedMovementFactor;
                    entity.motionX += xDiff / movementFactor * invertedMovementFactor * 0.1;
                    entity.motionY += yDiff / movementFactor * invertedMovementFactor * 0.1;
                    entity.motionZ += zDiff / movementFactor * invertedMovementFactor * 0.1;
                }
            }
        }
    }

    @Override
    public int getMinimumEnchantability(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaximumEnchantability(int enchantmentLevel) {
        return 40;
    }
}
