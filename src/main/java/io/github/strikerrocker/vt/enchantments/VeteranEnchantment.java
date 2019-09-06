package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment(String name) {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, new EquipmentSlotType[]{EquipmentSlotType.HEAD});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentFeature.veteran)
            for (PlayerEntity player : event.world.getPlayers()) {
                for (ExperienceOrbEntity experienceOrbEntity : player.world.getEntitiesWithinAABB(ExperienceOrbEntity.class, player.getBoundingBox().expand(32, 32, 32), EntityPredicates.IS_ALIVE)) {
                    attemptToMove(experienceOrbEntity, player);
                }
            }
        //TODO doesnt work
    }

    private void attemptToMove(Entity entity, PlayerEntity closestPlayer) {
        double range = 32;
        if (closestPlayer != null && EnchantmentHelper.getEnchantmentLevel(this, closestPlayer.getItemStackFromSlot(EquipmentSlotType.HEAD)) > 0) {
            double xDiff = (closestPlayer.posX - entity.posX) / range;
            double yDiff = (closestPlayer.posY + closestPlayer.getEyeHeight() - entity.posY) / range;
            double zDiff = (closestPlayer.posZ - entity.posZ) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                Vec3d motion = entity.getMotion();
                invertedMovementFactor *= invertedMovementFactor;
                entity.setMotion(motion.x + xDiff / movementFactor * invertedMovementFactor * 0.1, motion.y + yDiff / movementFactor * invertedMovementFactor * 0.1, motion.z + zDiff / movementFactor * invertedMovementFactor * 0.1);
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
        return EnchantmentFeature.veteran ? 1 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.HEAD) && EnchantmentFeature.veteran;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.veteran;
    }
}
