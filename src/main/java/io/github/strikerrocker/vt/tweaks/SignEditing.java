package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class SignEditing extends Feature {
    private ForgeConfigSpec.BooleanValue enableSignClearing;
    private ForgeConfigSpec.BooleanValue enableSignEditing;

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getEntityPlayer();
        boolean success = false;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te instanceof SignTileEntity) {
            SignTileEntity sign = (SignTileEntity) te;
            if (player.isSneaking() && enableSignClearing.get()) {
                ITextComponent[] text = new ITextComponent[]{new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent("")};
                ObfuscationReflectionHelper.setPrivateValue(SignTileEntity.class, sign, text, "signText");
                success = true;
            } else if (enableSignEditing.get()) {
                player.openSignEditor(sign);
                success = true;
            }
        }
        if (success) {
            event.setCanceled(true);
            event.getEntityPlayer().swingArm(Hand.MAIN_HAND);
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSignEditing = builder
                .translation("config.vanillatweaks:enableSignEditing")
                .comment("Want a way to clear text in signs?")
                .define("enableSignEditing", true);
        enableSignClearing = builder
                .translation("config.vanillatweaks:enableSignClearing")
                .comment("Want a way to change text in signs without breaking them?")
                .define("enableSignClearing", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
