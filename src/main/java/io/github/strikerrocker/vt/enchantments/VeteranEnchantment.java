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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment(String name) {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, new EquipmentSlotType[]{EquipmentSlotType.HEAD});
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentFeature.enableVeteran.get() && event.world != null && !event.world.isRemote()) {
            ServerWorld world = (ServerWorld) event.world;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntityPredicates.IS_ALIVE).forEach(this::attemptToMove);
        }
    }

    private void attemptToMove(Entity entity) {
        double range = 32;
        PlayerEntity closestPlayer = entity.world.getClosestPlayer(entity, range);
        if (closestPlayer != null && EnchantmentHelper.getEnchantmentLevel(this, closestPlayer.getItemStackFromSlot(EquipmentSlotType.HEAD)) > 0) {
            double xDiff = (closestPlayer.getPosition().getX() - entity.getPosition().getX()) / range;
            double yDiff = (closestPlayer.getPosition().getY() + closestPlayer.getEyeHeight() - entity.getPosition().getY()) / range;
            double zDiff = (closestPlayer.getPosition().getZ() - entity.getPosition().getZ()) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                invertedMovementFactor *= invertedMovementFactor;
                Vec3d motion = entity.getMotion();
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
        return EnchantmentFeature.enableVeteran.get() ? 1 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.HEAD) && EnchantmentFeature.enableVeteran.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableVeteran.get();
    }
}
