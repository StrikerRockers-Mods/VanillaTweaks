package io.github.strikerrocker.vt.proxies;

import io.github.strikerrocker.vt.handlers.VTSoundHandler;
import io.github.strikerrocker.vt.handlers.events.*;
import io.github.strikerrocker.vt.misc.NetherPortalFix;
import io.github.strikerrocker.vt.worldgen.NetherPocketer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * The common (dual-side) proxy for Vanilla Tweaks
 */

public class VTCommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void regsiterRenderer() {

    }

    public void onInit(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new VTSoundHandler());
        MinecraftForge.EVENT_BUS.register(new NetherPortalFix());
        MinecraftForge.TERRAIN_GEN_BUS.register(new NetherPocketer());
        MinecraftForge.EVENT_BUS.register(new VTEventHandler());
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        MinecraftForge.EVENT_BUS.register(new EntityEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new TickEvents());
    }

}