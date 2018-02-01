package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.proxies.VTClientProxy;
import com.strikerrocker.vt.vt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class VTInputHandler {
    @SubscribeEvent
    public void input(InputEvent.KeyInputEvent event) {
        if (VTClientProxy.bauble.isPressed() && vt.baubles) {
            VTEventHandler.fov = !VTEventHandler.fov;
        }
    }
}
