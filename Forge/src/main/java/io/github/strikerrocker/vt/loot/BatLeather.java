package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BatLeather extends ForgeFeature {
    private ForgeConfigSpec.DoubleValue batLeatherDropChance;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        batLeatherDropChance = builder
                .translation("config.vanillatweaks:batLeatherDropChance")
                .comment("Do you want bat to drop leather when killed by a player?")
                .defineInRange("batLeatherDropChance", 1F, 0, 10);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDeathEvent event) {
        LootImpl.triggerBatLeatherDrop(event.getEntity(), event.getSource().getEntity(), batLeatherDropChance.get());
    }
}