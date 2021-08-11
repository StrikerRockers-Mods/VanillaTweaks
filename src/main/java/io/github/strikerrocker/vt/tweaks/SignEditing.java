package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SignEditing extends Feature {
    private ForgeConfigSpec.BooleanValue enableSignEditing;

    /**
     * Open sign editor even when its already placed
     */
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        BlockEntity be = event.getWorld().getBlockEntity(event.getPos());
        if (be instanceof SignBlockEntity sign && enableSignEditing.get() && !event.getWorld().isClientSide() && player.isCrouching()) {
            sign.setEditable(true);
            player.openTextEdit(sign);
            event.getPlayer().swing(InteractionHand.MAIN_HAND);
        }
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
