package com.strikerrocker.vt.handlers;

import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VTSoundHandler {
    // LOWEST Priority so that everything else can do their thing if they need to first
    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
    public void onEvent(PlaySoundEvent event) {


        if ((event.getName().equals("entity.wither.spawn")) && VTConfigHandler.silenceWither) {
            event.setResultSound(null);
        }


        if ((event.getName().equals("entity.enderdragon.death")) && VTConfigHandler.silenceDragon) {
            event.setResultSound(null);
        }

        if ((event.getName().equals("entity.lightning.thunder")) && VTConfigHandler.silenceLightning) {

            event.setResultSound(null);
        }

    }
}