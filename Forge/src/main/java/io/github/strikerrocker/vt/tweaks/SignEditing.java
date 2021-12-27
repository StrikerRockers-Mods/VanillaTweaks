package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SignEditing extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue enableSignEditing;


    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockEntity be = event.getWorld().getBlockEntity(event.getPos());
        if (TweaksImpl.triggerSignEditing(event.getWorld(), event.getPlayer(), be, event.getHand(), enableSignEditing.get()))
            event.setCanceled(true);
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSignEditing = builder
                .translation("config.vanillatweaks:enableSignEditing")
                .comment("Want a way to change text in signs without breaking them?")
                .define("enableSignEditing", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
