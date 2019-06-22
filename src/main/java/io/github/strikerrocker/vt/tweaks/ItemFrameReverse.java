package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemFrameReverse extends Feature {
    private boolean shiftItemFrameRotateBackwards;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (shiftItemFrameRotateBackwards && event.getTarget() instanceof EntityItemFrame && event.getEntityPlayer().isSneaking()) {
            EntityItemFrame frame = (EntityItemFrame) event.getTarget();
            frame.setItemRotation(frame.getRotation() - 2);
        }
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        shiftItemFrameRotateBackwards = config.get(category, "shiftItemFrameRotateBackwards", true, "Want to shift click item frame to reverse rotate?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
