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
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentInit.enableVeteran.get() && event.world != null && !event.world.isClientSide()) {
            ServerLevel world = (ServerLevel) event.world;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntitySelector.ENTITY_STILL_ALIVE).forEach(VeteranEnchantment::attemptToMove);
        }
    }

    /**
     * Handles the logic of Veteran enchantment
     *
     * @param xpEntity The Experience Orb Entity
     */
    private static void attemptToMove(Entity xpEntity) {
        double range = 32;
        Player closestPlayer = xpEntity.level.getNearestPlayer(xpEntity, range);
        if (closestPlayer != null && EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.VETERAN.get(), closestPlayer.getItemBySlot(EquipmentSlot.HEAD)) > 0) {
            double xDiff = (closestPlayer.getX() - xpEntity.getX()) / range;
            double yDiff = (closestPlayer.getY() + closestPlayer.getEyeHeight() - xpEntity.getY()) / range;
            double zDiff = (closestPlayer.getZ() - xpEntity.getZ()) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                invertedMovementFactor *= invertedMovementFactor;
                Vec3 motion = xpEntity.getDeltaMovement();
                xpEntity.setDeltaMovement(motion.x + xDiff / movementFactor * invertedMovementFactor * 0.3,
                        motion.y + yDiff / movementFactor * invertedMovementFactor * 0.3,
                        motion.z + zDiff / movementFactor * invertedMovementFactor * 0.3);
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
        return EnchantmentInit.enableVeteran.get() ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.HEAD) &&
                EnchantmentInit.enableVeteran.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableVeteran.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.veteranTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableVeteran.get();
    }
}
