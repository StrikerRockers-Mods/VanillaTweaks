package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, new EquipmentSlotType[]{EquipmentSlotType.HEAD});
        this.setRegistryName("veteran");
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentFeature.enableVeteran.get() && event.world != null && !event.world.isClientSide()) {
            ServerWorld world = (ServerWorld) event.world;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntityPredicates.ENTITY_STILL_ALIVE).forEach(this::attemptToMove);
        }
    }

    private void attemptToMove(Entity xpEntity) {
        double range = 32;
        PlayerEntity closestPlayer = xpEntity.level.getNearestPlayer(xpEntity, range);
        if (closestPlayer != null && EnchantmentHelper.getItemEnchantmentLevel(this, closestPlayer.getItemBySlot(EquipmentSlotType.HEAD)) > 0) {
            double xDiff = (closestPlayer.getX() - xpEntity.getX()) / range;
            double yDiff = (closestPlayer.getY() + closestPlayer.getEyeHeight() - xpEntity.getY()) / range;
            double zDiff = (closestPlayer.getZ() - xpEntity.getZ()) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                invertedMovementFactor *= invertedMovementFactor;
                Vector3d motion = xpEntity.getDeltaMovement();
                xpEntity.setDeltaMovement(motion.x + xDiff / movementFactor * invertedMovementFactor * 0.1, motion.y + yDiff / movementFactor * invertedMovementFactor * 0.1, motion.z + zDiff / movementFactor * invertedMovementFactor * 0.1);
            }
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return 40;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableVeteran.get() ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlot().equals(EquipmentSlotType.HEAD) && EnchantmentFeature.enableVeteran.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableVeteran.get();
    }
}
