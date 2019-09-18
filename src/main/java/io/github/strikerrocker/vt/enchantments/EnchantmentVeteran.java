package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EnchantmentVeteran extends Enchantment {
    EnchantmentVeteran(String name) {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD, new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote)
            event.world.getEntities(EntityXPOrb.class, EntitySelectors.IS_ALIVE).forEach(this::attemptToMove);
    }

    private void attemptToMove(Entity entity) {
        double range = 32;
        EntityPlayer closestPlayer = entity.world.getClosestPlayerToEntity(entity, range);
        if (closestPlayer != null && EnchantmentHelper.getEnchantmentLevel(this, closestPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD)) > 0) {
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

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 40;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType.equals(EntityEquipmentSlot.HEAD);
    }
}
