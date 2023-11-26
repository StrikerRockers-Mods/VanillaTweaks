package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class ItemFrameReverse extends ForgeFeature {
    private ModConfigSpec.BooleanValue itemFrameRotateBackwards;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getEntity().level().isClientSide() && TweaksImpl.triggerItemFrameReverse(event.getTarget(), event.getEntity(), itemFrameRotateBackwards.get())) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
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