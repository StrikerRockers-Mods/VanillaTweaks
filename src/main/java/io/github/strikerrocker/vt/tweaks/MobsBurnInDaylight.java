package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MobsBurnInDaylight extends Feature {
    private ForgeConfigSpec.BooleanValue babyZombieBurnInDaylight;
    private ForgeConfigSpec.BooleanValue creeperBurnInDaylight;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        babyZombieBurnInDaylight = builder
                .translation("config.vanillatweaks:babyZombieBurnInDaylight")
                .comment("Want baby zombies to burn by the light of the day?")
                .define("babyZombieBurnInDaylight", true);
        creeperBurnInDaylight = builder
                .translation("config.vanillatweaks:creeperBurnInDaylight")
                .comment("Want creeper's to burn in daylight?")
                .define("creeperBurnInDaylight", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    /**
     * Handles burning of baby zombie and creepers in daylight
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof Creeper && creeperBurnInDaylight.get() ||
                entity instanceof Zombie && entity.isBaby() && babyZombieBurnInDaylight.get()) {
            boolean flag = canBurn(entity);
            if (flag) {
                ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.HEAD);
                // Damages the helmet if its present
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + entity.getRandom().nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            entity.broadcastBreakEvent(EquipmentSlot.HEAD);
                            entity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    entity.setSecondsOnFire(8);
                }
            }
        }
    }

    /**
     * Returns if entity can burn
     */
    boolean canBurn(LivingEntity entity) {
        Level level = entity.level;
        if (level.isDay() && !level.isClientSide) {
            float f = entity.getBrightness();
            boolean flag = entity.isInWaterRainOrBubble() || entity.isInPowderSnow || entity.wasInPowderSnow;
            return f > 0.5F && entity.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && level.canSeeSky(entity.blockPosition());
        }
        return false;
    }
}