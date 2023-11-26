package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class BatLeather extends ForgeFeature {
    private ModConfigSpec.DoubleValue batLeatherDropChance;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
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