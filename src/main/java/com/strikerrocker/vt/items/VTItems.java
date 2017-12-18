package com.strikerrocker.vt.items;

import com.strikerrocker.vt.vt;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import static com.strikerrocker.vt.handlers.VTConfigHandler.craftingPad;
import static com.strikerrocker.vt.handlers.VTConfigHandler.slimeChunkFinder;

/**
 * Contains, initializes, and registers all of VanillaTweaks's items
 */
public class VTItems {

    public static ItemArmor binoculars = new ItemArmor(vt.binoculars, EntityEquipmentSlot.HEAD, "binoculars");
    public static ItemCraftingPad pad = new ItemCraftingPad("pad");
    public static ItemDynamite dynamite = new ItemDynamite("dynamite");
    public static ItemFriedEgg friedegg = new ItemFriedEgg();
    public static ItemSlimeBucket slime = new ItemSlimeBucket("slime");
    public static ItemBase lens = new ItemBase("lens");


    public static void register(IForgeRegistry<Item> registry) {
        if (craftingPad) {
            registry.register(pad);
        }
        registry.register(binoculars);
        registry.register(friedegg);
        registry.register(dynamite);
        registry.register(lens);
        if (slimeChunkFinder) {
            registry.register(slime);
        }
    }

    public static void registerModels() {
        binoculars.registerItemModel();
        if (craftingPad) {
            pad.registerItemModel();
        }
        friedegg.registerItemModel();
        dynamite.registerItemModel();
        lens.registerItemModel();
        if (slimeChunkFinder) {
            slime.registerItemModel();
        }
    }

}
