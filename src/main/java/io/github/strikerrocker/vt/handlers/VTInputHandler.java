package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.proxies.VTClientProxy;
import io.github.strikerrocker.vt.vt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VTInputHandler {
    @SubscribeEvent
    public static void input(InputEvent.KeyInputEvent event) {
        if (VTClientProxy.bauble.isPressed()) {
            VTEventHandler.fov = !VTEventHandler.fov;
        }
    }
}
