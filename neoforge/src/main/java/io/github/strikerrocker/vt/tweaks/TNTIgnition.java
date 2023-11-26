package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.level.BlockEvent;

public class TNTIgnition extends ForgeFeature {
    private ModConfigSpec.BooleanValue enableTNTIgnition;

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity != null) {
            TweaksImpl.triggerTNTIgnition(entity.level(), event.getPos(), event.getPlacedBlock(), enableTNTIgnition.get());
        }
    }

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        enableTNTIgnition = builder
                .translation("config.vanillatweaks:tntIgnition")
                .comment("Want TNT to ignite when next to lava or magma block?")
                .define("tntIgnition", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}