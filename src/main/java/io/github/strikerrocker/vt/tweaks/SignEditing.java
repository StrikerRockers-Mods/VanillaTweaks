package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SignEditing extends Feature {
    private boolean enableSignEditing;

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        boolean success = false;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign) te;
            if (enableSignEditing) {
                player.openEditSign(sign);
                success = true;
            }
        }
        if (success) {
            event.setCanceled(true);
            event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        enableSignEditing = config.get(category, "enableSignEditing", true, "Want a way to change text in signs without breaking them?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
