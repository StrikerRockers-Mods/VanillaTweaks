package io.github.strikerrocker.vt.entities;

import io.github.strikerrocker.vt.vt;
import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Registers Vanilla Tweaks's entities
 */
public class VTEntities
{

    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(vtModInfo.MOD_ID, "dynamite"), EntityDynamite.class, vtModInfo.MOD_ID + ".dynamite", 0, vt.instance, 64, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(vtModInfo.MOD_ID, "entity_sit"), EntitySitting.class, "entity_sit", 1, vt.instance, 256, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(vtModInfo.MOD_ID, "tnt"), EntityTntImproved.class, "tnt", 2, vt.instance, 64, 1, true);
    }
}
