package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

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

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!livingEntity.world.isRemote) {
            World world = livingEntity.world;
            if (((livingEntity instanceof CreeperEntity && creeperBurnInDaylight.get()) || (livingEntity instanceof ZombieEntity && livingEntity.isChild() &&
                    babyZombieBurnInDaylight.get())) && world.isDaytime()) {
                float brightness = livingEntity.getBrightness();
                Random random = world.rand;
                BlockPos blockPos = livingEntity.func_233580_cy_();
                if (brightness > 0.5 && random.nextFloat() * 30 < (brightness - 0.4) * 2 && world.canBlockSeeSky(blockPos)) {
                    ItemStack itemstack = livingEntity.getItemStackFromSlot(EquipmentSlotType.HEAD);
                    boolean setFire = true;
                    if (!itemstack.isEmpty()) {
                        setFire = true;
                        if (itemstack.isDamageable()) {
                            itemstack.setDamage(itemstack.getDamage() + random.nextInt(2));
                            if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
                                livingEntity.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                            }
                        }
                    }
                    if (setFire) {
                        livingEntity.setFire(10);
                    }
                }
            }
        }
    }
}
