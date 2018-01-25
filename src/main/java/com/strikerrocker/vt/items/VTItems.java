package com.strikerrocker.vt.items;


import com.strikerrocker.vt.compat.baubles.BinocularBauble;
import com.strikerrocker.vt.vt;
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

    public static ItemArmor binocular;
    public static ItemCraftingPad pad;
    public static ItemDynamite dynamite;
    public static ItemFriedEgg fried_egg;
    public static ItemSlimeBucket slime;
    private static ItemBase lens;
    public static BinocularBauble bb= new BinocularBauble();

    public static void init() {
        binocular = new ItemArmor(binoculars, EntityEquipmentSlot.HEAD, "binoculars");
        pad = new ItemCraftingPad("pad");
        dynamite = new ItemDynamite("dynamite");
        fried_egg = new ItemFriedEgg();
        slime = new ItemSlimeBucket("slime");
        lens = new ItemBase("lens");
    }


    public static void register(IForgeRegistry<Item> registry) {
        registry.register(pad);
        registry.register(binocular);
        registry.register(fried_egg);
        registry.register(dynamite);
        registry.register(lens);
        registry.register(slime);
    }

    public static void registerModels() {
        binocular.registerItemModel();
        pad.registerItemModel();
        fried_egg.registerItemModel();
        dynamite.registerItemModel();
        lens.registerItemModel();
        slime.registerItemModel();
    }

}
