package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.LivingEntityTickCallback;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MobsBurnInDaylight extends Feature {

    /**
     * Handles burning of baby zombie and creepers in daylight
     */
    @Override
    public void initialize() {
        LivingEntityTickCallback.EVENT.register(livingEntity -> {
            if (((livingEntity instanceof Creeper && VanillaTweaksFabric.config.tweaks.creeperBurnInDaylight) ||
                    (livingEntity instanceof Zombie && livingEntity.isBaby() && VanillaTweaksFabric.config.tweaks.babyZombieBurnInDaylight))) {
                boolean flag = canBurn(livingEntity);
                if (flag) {
                    ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
                    // Damages the helmet if its present
                    if (!itemStack.isEmpty()) {
                        if (itemStack.isDamageableItem()) {
                            itemStack.setDamageValue(itemStack.getDamageValue() + livingEntity.getRandom().nextInt(2));
                            if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
                                livingEntity.broadcastBreakEvent(EquipmentSlot.HEAD);
                                livingEntity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                            }
                        }
                        flag = false;
                    }
                    if (flag) {
                        livingEntity.setSecondsOnFire(8);
                    }
                }
            }
        });
    }

    /**
     * Returns if entity can burn
     */
    boolean canBurn(LivingEntity entity) {
        Level world = entity.level;
        if (world.isDay() && !world.isClientSide) {
            float f = entity.getBrightness();
            boolean bl = entity.isInWaterRainOrBubble() || entity.isInPowderSnow || entity.wasInPowderSnow;
            return f > 0.5F && entity.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !bl && world.canSeeSky(entity.blockPosition());
        }
        return false;
    }
}