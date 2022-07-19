package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemFrameReverse extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue itemFrameRotateBackwards;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (TweaksImpl.triggerItemFrameReverse(event.getTarget(), event.getEntity(), itemFrameRotateBackwards.get())) {
            event.setCanceled(true);
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        itemFrameRotateBackwards = builder
                .translation("config.vanillatweaks:itemFrameRotateBackwards")
                .comment("Want to shift click item frame to rotate in the reverse direction?")
                .define("itemFrameRotateBackwards", true);

    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}