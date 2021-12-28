package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TNTIgnition extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue enableTNTIgnition;

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity != null) {
            TweaksImpl.triggerTNTIgnition(entity.getLevel(), event.getPos(), event.getPlacedBlock(), enableTNTIgnition.get());
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
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