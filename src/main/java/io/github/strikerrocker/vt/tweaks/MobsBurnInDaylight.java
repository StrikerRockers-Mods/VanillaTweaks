package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
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

    /**
     * Handles burning of baby zombie and creepers in daylight
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!livingEntity.level.isClientSide()) {
            Level world = livingEntity.level;
            if (((livingEntity instanceof Creeper && creeperBurnInDaylight.get()) || (livingEntity instanceof Zombie && livingEntity.isBaby() &&
                    babyZombieBurnInDaylight.get())) && world.isDay()) {
                float brightness = livingEntity.getBrightness();
                Random random = world.random;
                BlockPos blockPos = livingEntity.blockPosition();
                if (brightness > 0.5 && random.nextFloat() * 30 < (brightness - 0.4) * 2 && world.canSeeSky(blockPos)) {
                    livingEntity.setSecondsOnFire(10);
                }
            }
        }
    }
}
