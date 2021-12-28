package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.misc.ConeShape;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class EnchantmentImpl {
    private static final UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");
    private static final UUID nimbleUUID = UUID.fromString("05b61a62-ae84-492e-8536-f365b7143296");

    public static void triggerVigor(LivingEntity livingEntity, Enchantment vigor) {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(vigor, livingEntity.getItemBySlot(EquipmentSlot.CHEST));
        AttributeInstance vigorAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "vigor", (float) lvl / 10, AttributeModifier.Operation.MULTIPLY_BASE);
        if (vigorAttribute != null) {
            if (lvl > 0) {
                if (vigorAttribute.getModifier(vigorUUID) == null) {
                    vigorAttribute.addPermanentModifier(vigorModifier);
                }
            } else {
                if (vigorAttribute.getModifier(vigorUUID) != null) {
                    vigorAttribute.removeModifier(vigorModifier);
                    if (livingEntity.getHealth() > livingEntity.getMaxHealth())
                        livingEntity.setHealth(livingEntity.getMaxHealth());
                }
            }
        }
    }

    public static void moveXP(Entity entity, Enchantment enchantment) {
        double range = 32;
        Player closestPlayer = entity.level.getNearestPlayer(entity, range);
        if (closestPlayer != null && EnchantmentHelper.getItemEnchantmentLevel(enchantment, closestPlayer.getItemBySlot(EquipmentSlot.HEAD)) > 0) {
            double xDiff = (closestPlayer.position().x() - entity.position().x()) / range;
            double yDiff = (closestPlayer.position().y() + closestPlayer.getEyeHeight() - entity.position().y()) / range;
            double zDiff = (closestPlayer.position().z() - entity.position().z()) / range;
            double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
            double invertedMovementFactor = 1 - movementFactor;
            if (invertedMovementFactor > 0) {
                invertedMovementFactor *= invertedMovementFactor;
                Vec3 motion = entity.getDeltaMovement();
                entity.setDeltaMovement(motion.x + xDiff / movementFactor * invertedMovementFactor * 0.3,
                        motion.y + yDiff / movementFactor * invertedMovementFactor * 0.3,
                        motion.z + zDiff / movementFactor * invertedMovementFactor * 0.3);
            }
        }
    }

    public static void triggerNimble(LivingEntity livingEntity, Enchantment enchantment) {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, livingEntity.getItemBySlot(EquipmentSlot.FEET));
        AttributeInstance speedAttribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(nimbleUUID, "Nimble", lvl * 0.20000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL);
        if (speedAttribute != null) {
            if (lvl > 0) {
                if (speedAttribute.getModifier(nimbleUUID) == null) {
                    speedAttribute.addPermanentModifier(speedModifier);
                }
            } else if (speedAttribute.getModifier(nimbleUUID) != null) {
                speedAttribute.removeModifier(speedModifier);
            }
        }
    }

    public static void triggerHops(LivingEntity livingEntity, Enchantment enchantment) {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, livingEntity.getItemBySlot(EquipmentSlot.FEET));
        if (lvl > 0) {
            if (!livingEntity.hasEffect(MobEffects.JUMP)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, lvl, true, false, false));
            }
        } else {
            if (livingEntity.hasEffect(MobEffects.JUMP)) {
                livingEntity.removeEffect(MobEffects.JUMP);
            }
        }
    }

    public static LivingEntity getHomingTarget(Level world, LivingEntity shooter, int homingLevel) {
        AABB coneBound = ConeShape.getConeBoundApprox(shooter, homingLevel);
        List<Entity> potentialTargets = world.getEntities(shooter, coneBound);
        LivingEntity target = null;
        for (Entity potentialTarget : potentialTargets) {
            if (potentialTarget instanceof LivingEntity livingEntity && shooter.hasLineOfSight(potentialTarget)) {
                if (livingEntity instanceof OwnableEntity tamed && tamed.getOwnerUUID() == shooter.getUUID()) continue;
                target = livingEntity;
            }
        }
        VanillaTweaks.LOGGER.debug(coneBound);
        VanillaTweaks.LOGGER.debug(target);
        return target;
    }

    public static void triggerHoming(AbstractArrow arrow, LivingEntity shooter, Enchantment enchantment) {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, shooter.getUseItem());
        if (lvl > 0) {
            LivingEntity target = EnchantmentImpl.getHomingTarget(arrow.getCommandSenderWorld(), shooter, lvl);
            if (target != null) {
                double x = target.getX() - arrow.getX();
                double y = target.getEyeY() - arrow.getY();
                double z = target.getZ() - arrow.getZ();
                arrow.setNoGravity(true);
                arrow.shoot(x, y, z, (float) arrow.getDeltaMovement().length(), 0);
            }
        }
    }
}