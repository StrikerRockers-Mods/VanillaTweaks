package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod.EventBusSubscriber(modid = VTModInfo.MOD_ID)
public class VTSoundHandler {
    // LOWEST Priority so that everything else can do their thing if they need to first
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void onEvent(PlaySoundEvent event) {


        if ((event.getName().equals("entity.wither.spawn")) && ConfigHandler.miscellanious.silenceWither) {
            event.setResultSound(null);
        }


        if ((event.getName().equals("entity.enderdragon.death")) && ConfigHandler.miscellanious.silenceDragon) {
            event.setResultSound(null);
        }

        if ((event.getName().equals("entity.lightning.thunder")) && ConfigHandler.miscellanious.silenceLightning) {

            event.setResultSound(null);
        }

    }
}