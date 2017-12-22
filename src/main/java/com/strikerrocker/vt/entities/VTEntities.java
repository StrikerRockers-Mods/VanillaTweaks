package com.strikerrocker.vt.entities;

import com.strikerrocker.vt.vt;
import net.minecraft.entity.Entity;
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
    public static void registerEntities(vt mod) {
        currentMod = mod;
        registerEntity(EntityDynamite.class, "Dynamite");
        registerEntity(EntitySitting.class, "Sit");
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String entityName) {
        EntityRegistry.registerModEntity(entityClass, entityName, ++currentEntityID, currentMod, 64, 10, true);
    }

}
