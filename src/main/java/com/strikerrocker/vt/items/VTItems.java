package com.strikerrocker.vt.items;

import com.strikerrocker.vt.main.vt;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

;

/**
 * Contains, initializes, and registers all of Craft++'s items
 */
public class VTItems {

    public static ItemArmor binoculars;
    public static ItemCraftingPad pad;
    public static ItemDynamite dynamite;
    public static ItemFriedEgg friedegg;
    public static ItemBase lens;


    public static void init() {
        binoculars = register(new ItemArmor(vt.binoculars, EntityEquipmentSlot.HEAD, "binoculars"));
        pad = register(new ItemCraftingPad("pad"));
        dynamite = register(new ItemDynamite("dynamite"));
        friedegg = register(new ItemFriedEgg());
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