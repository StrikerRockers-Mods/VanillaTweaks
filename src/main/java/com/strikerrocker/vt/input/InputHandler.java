package com.strikerrocker.vt.input;

import com.strikerrocker.vt.handlers.VTEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@Mod.EventBusSubscriber
public class InputHandler {
    @SubscribeEvent
    public void input(InputEvent.KeyInputEvent event) {
        if (KeyBindings.bauble.isPressed()) {
            if (VTEventHandler.fov = true) {
                VTEventHandler.fov = false;
            } else {
                VTEventHandler.fov = true;
            }
        }
    }
}
