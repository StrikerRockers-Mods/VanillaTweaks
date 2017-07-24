package com.strikerrocker.vt.entities;

import com.strikerrocker.vt.main.vt;
import com.strikerrocker.vt.main.vtModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Registers Vanilla Tweaks's entities
 */
public class VTEntities {
    private static int currentEntityID = 0;
    private static vt currentMod;

    /**
     * Registers the entities for Vanilla Tweaks
     */
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(vtModInfo.MOD_ID, "worm"), EntityDynamite.class, vtModInfo.MOD_ID + ".worm", 0, vt.instance, 64, 1, false);
    }


}
