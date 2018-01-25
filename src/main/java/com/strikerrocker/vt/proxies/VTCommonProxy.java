package com.strikerrocker.vt.proxies;

import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * The common (dual-side) proxy for Vanilla Tweaks
 */

@Mod.EventBusSubscriber
public class VTCommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {    }
    public void registerRenderers() {    }
    public void init(FMLInitializationEvent e) {    }
}