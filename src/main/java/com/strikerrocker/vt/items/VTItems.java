package com.strikerrocker.vt.items;

import com.strikerrocker.vt.vt;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.strikerrocker.vt.handlers.VTConfigHandler.craftingPad;
import static com.strikerrocker.vt.handlers.VTConfigHandler.slimeChunkFinder;

/**
 * Contains, initializes, and registers all of VanillaTweaks's items
 */
public class VTItems {


    public static ItemArmor binoculars;
    public static ItemCraftingPad pad;
    public static ItemDynamite dynamite;
    public static ItemFriedEgg friedegg;
    public static ItemSlimeBucket slime;
    public static ItemBase lens;


    public static void init() {
        binoculars = register(new ItemArmor(vt.binoculars, EntityEquipmentSlot.HEAD, "binoculars"));

        if (craftingPad) {
            pad = register(new ItemCraftingPad("pad"));
        }
        dynamite = register(new ItemDynamite("dynamite"));
        friedegg = register(new ItemFriedEgg());
        if (slimeChunkFinder) {
            slime = register(new ItemSlimeBucket("slime"));
        }
        lens = register(new ItemBase("lens"));
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemModelProvider) {
            ((ItemModelProvider) item).registerItemModel(item);
        }

        return item;
    }

}
