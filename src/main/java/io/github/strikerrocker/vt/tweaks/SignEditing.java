package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SignEditing extends Feature {
    private ForgeConfigSpec.BooleanValue enableSignEditing;

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        boolean success = false;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te instanceof SignTileEntity && enableSignEditing.get() && !event.getWorld().isRemote) {
            SignTileEntity sign = (SignTileEntity) te;
            player.openSignEditor(sign);
            success = true;
        }
        if (success) {
            event.setCanceled(true);
            event.getPlayer().swingArm(Hand.MAIN_HAND);
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSignEditing = builder
                .translation("config.vanillatweaks:enableSignEditing")
                .comment("Want a way to clear text in signs?")
                .define("enableSignEditing", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
