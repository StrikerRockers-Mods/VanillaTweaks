package com.strikerrocker.vt.items;

import com.strikerrocker.vt.main.VT;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains, initializes, and registers all of Craft++'s items
 */
public class VTItems {

    public static ItemArmor binoculars = new ItemArmor(VT.binoculars, EntityEquipmentSlot.HEAD, "binoculars");
    public static ItemCraftingPad pad = new ItemCraftingPad("pad");
    public static ItemDynamite dynamite = new ItemDynamite("dynamite");
    public static ItemFriedEgg friedegg = new ItemFriedEgg();
    public static ItemBase lens = new ItemBase("lens");


    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                binoculars, pad, friedegg, dynamite, lens
        );
    }

    public static void registerModels() {
        binoculars.registerItemModel();
        pad.registerItemModel();
        friedegg.registerItemModel();
        dynamite.registerItemModel();
        lens.registerItemModel();
    }

}
