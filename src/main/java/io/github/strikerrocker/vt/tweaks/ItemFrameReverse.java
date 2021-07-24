package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemFrameReverse extends Feature {
    private ForgeConfigSpec.BooleanValue itemFrameRotateBackwards;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (itemFrameRotateBackwards.get() && event.getTarget() instanceof ItemFrame frame && event.getPlayer().isCrouching()) {
            int rotation = frame.getRotation() - 2;
            if (rotation < 0)
                rotation = 6;
            frame.setRotation(rotation);
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
