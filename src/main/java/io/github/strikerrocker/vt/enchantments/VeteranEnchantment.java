package io.github.strikerrocker.vt.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
        this.setRegistryName("veteran");
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentFeature.enableVeteran.get() && event.world != null && !event.world.isClientSide()) {
            ServerLevel world = (ServerLevel) event.world;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntitySelector.ENTITY_STILL_ALIVE).forEach(this::attemptToMove);
        }
    }

    private void attemptToMove(Entity entity) {
        double range = 32;
        Player closestPlayer = entity.level.getNearestPlayer(entity, range);
        if (closestPlayer != null && EnchantmentHelper.getItemEnchantmentLevel(this, closestPlayer.getItemBySlot(EquipmentSlot.HEAD)) > 0) {
            double xDiff = (closestPlayer.getX() - entity.getX()) / range;
            double yDiff = (closestPlayer.getY() + closestPlayer.getEyeHeight() - entity.getY()) / range;
            double zDiff = (closestPlayer.getZ() - entity.getZ()) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                invertedMovementFactor *= invertedMovementFactor;
                Vec3 motion = entity.getDeltaMovement();
                entity.setDeltaMovement(motion.x + xDiff / movementFactor * invertedMovementFactor * 0.1, motion.y + yDiff / movementFactor * invertedMovementFactor * 0.1, motion.z + zDiff / movementFactor * invertedMovementFactor * 0.1);
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
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlot().equals(EquipmentSlot.HEAD) && EnchantmentFeature.enableVeteran.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableVeteran.get();
    }
}
