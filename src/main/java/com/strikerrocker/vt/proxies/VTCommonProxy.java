package com.strikerrocker.vt.proxies;

import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

/**
 * The common (dual-side) proxy for Vanilla Tweaks
 */

public class VTCommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerRenderers() {

    }

    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }

}