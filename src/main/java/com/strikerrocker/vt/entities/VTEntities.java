package com.strikerrocker.vt.entities;

import com.strikerrocker.vt.main.VT;
import com.strikerrocker.vt.main.VTModInfo;
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
        EntityRegistry.registerModEntity(new ResourceLocation(VTModInfo.MOD_ID, "dynamite"), EntityDynamite.class, VTModInfo.MOD_ID + ".worm", 0, VT.instance, 64, 1, false);
    }

}
