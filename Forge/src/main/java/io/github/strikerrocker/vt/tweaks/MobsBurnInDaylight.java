package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MobsBurnInDaylight extends ForgeFeature {
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
        LivingEntity entity = event.getEntityLiving();
        TweaksImpl.triggerMobsBurnInSun(entity, creeperBurnInDaylight.get(), babyZombieBurnInDaylight.get());
    }
}