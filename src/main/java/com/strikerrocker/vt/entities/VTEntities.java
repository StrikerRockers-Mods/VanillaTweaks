package com.strikerrocker.vt.entities;

import com.strikerrocker.vt.vt;
import com.strikerrocker.vt.vtModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Registers Vanilla Tweaks's entities
 */
public class VTEntities {

    /**
     * Registers the entities for Vanilla Tweaks
     */
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(vtModInfo.MOD_ID, "dynamite"), EntityDynamite.class, vtModInfo.MOD_ID + ".worm", 0, vt.instance, 64, 1, false);
    }

}
