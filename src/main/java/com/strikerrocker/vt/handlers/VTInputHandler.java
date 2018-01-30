package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.proxies.VTClientProxy;
import com.strikerrocker.vt.vt;
import com.strikerrocker.vt.vtModInfo;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = vtModInfo.MOD_ID, value = Side.CLIENT)
public class VTInputHandler {
    @SubscribeEvent
    public static void input(InputEvent.KeyInputEvent event) {
        if (VTClientProxy.bauble.isPressed() && vt.baubles) {
            VTEventHandler.fov = !VTEventHandler.fov;
        }
    }
}
