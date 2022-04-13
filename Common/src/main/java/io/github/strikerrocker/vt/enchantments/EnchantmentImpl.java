package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.misc.ConeShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

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

    @Nullable
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
        if (target != null) {
            VanillaTweaks.LOGGER.debug(coneBound.toString());
            VanillaTweaks.LOGGER.debug(target.toString());
        }
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

    public static List<ItemStack> blazingLogic(ServerLevel world, Entity entity, ItemStack tool, List<ItemStack> dropList) {
        RecipeManager recipeManager = world.getRecipeManager();
        Container simpleInv = new SimpleContainer(1);
        ItemStack itemToBeChecked;
        Optional<SmeltingRecipe> smeltingResult;
        ArrayList<ItemStack> newDropList = new ArrayList<>(dropList);
        if (!newDropList.isEmpty())
            for (int i = 0; i < newDropList.size(); i++) {
                itemToBeChecked = newDropList.get(i);
                simpleInv.setItem(0, itemToBeChecked);
                smeltingResult = recipeManager.getRecipeFor(RecipeType.SMELTING, simpleInv, entity.level);
                if (smeltingResult.isPresent() && entity instanceof Player playerEntity) {
                    int count = itemToBeChecked.getCount();
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool) > 0) {
                        count = getFortuneCount(world.getRandom(), count, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool));
                        tool.hurtAndBreak(Math.max(count - 1, 3), playerEntity, player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                    }
                    newDropList.set(i, new ItemStack(smeltingResult.get().getResultItem().getItem(), count));
                    playerEntity.giveExperiencePoints((int) (smeltingResult.get().getExperience() * itemToBeChecked.getCount()));
                }
            }
        return newDropList;
    }

    public static int getFortuneCount(Random random, int initialCount, int lvl) {
        if (lvl > 0) {
            int i = random.nextInt(lvl + 2) - 1;
            if (i < 0) {
                i = 0;
            }
            return initialCount * (i + 1);
        } else {
            return initialCount;
        }
    }

    public static List<ItemStack> siphonLogic(Entity entity, List<ItemStack> dropList) {
        if (entity instanceof Player playerEntity) {
            ArrayList<ItemStack> newDropList = new ArrayList<>(dropList);
            newDropList.removeIf(playerEntity::addItem);
            return newDropList;
        }
        return dropList;
    }
}