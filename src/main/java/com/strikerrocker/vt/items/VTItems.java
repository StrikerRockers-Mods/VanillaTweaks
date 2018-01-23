package com.strikerrocker.vt.items;

import com.strikerrocker.vt.vtModInfo;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains, initializes, and registers all of VanillaTweaks's items
 */
public class VTItems {

    private static final ItemArmor.ArmorMaterial binoculars = EnumHelper.addArmorMaterial("binoculars", vtModInfo.MOD_ID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    public static ItemArmor binocular = new ItemArmor(binoculars, EntityEquipmentSlot.HEAD, "binoculars");
    public static ItemCraftingPad pad = new ItemCraftingPad("pad");
    public static ItemDynamite dynamite = new ItemDynamite("dynamite");
    public static ItemFriedEgg friedegg = new ItemFriedEgg();
    public static ItemSlimeBucket slime = new ItemSlimeBucket("slime");
    private static ItemBase lens = new ItemBase("lens");


    public static void register(IForgeRegistry<Item> registry) {
        registry.register(pad);
        registry.register(binocular);
        registry.register(friedegg);
        registry.register(dynamite);
        registry.register(lens);
        registry.register(slime);
    }

    public static void registerModels() {
        binocular.registerItemModel();
        pad.registerItemModel();
        friedegg.registerItemModel();
        dynamite.registerItemModel();
        lens.registerItemModel();
        slime.registerItemModel();
    }

}
