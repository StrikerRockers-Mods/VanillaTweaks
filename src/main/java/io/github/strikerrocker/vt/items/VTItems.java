package io.github.strikerrocker.vt.items;


import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.compat.baubles.BaubleTools;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains, initializes, and registers all of VanillaTweaks's items
 */
@Mod.EventBusSubscriber(modid = VTModInfo.MODID)
public class VTItems {

    public static final ItemCraftingPad pad = new ItemCraftingPad("pad");
    public static final ItemDynamite dynamite = new ItemDynamite("dynamite");
    public static final Item fried_egg = new ItemFood(3, 0.6f, false).setRegistryName("friedegg").setTranslationKey("friedegg");
    public static final ItemSlimeBucket slime = new ItemSlimeBucket("slime");
    private static final ItemArmor.ArmorMaterial binocular_material = EnumHelper.addArmorMaterial("binoculars", VTModInfo.MODID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final Item binocular = new ItemArmor(binocular_material, 0, EntityEquipmentSlot.HEAD).setMaxStackSize(1).setRegistryName("binoculars").setTranslationKey("binoculars");
    private static final Item lens = new Item().setTranslationKey("lens").setRegistryName("lens");
    public static Item bb;
    public static List<Item> items = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        VT.logInfo("Registering Items");
        if (VT.baubles) {
            items.add(bb = BaubleTools.initBinocularBauble());
            event.getRegistry().register(bb);
        }
        items.addAll(Arrays.asList(pad, dynamite, fried_egg, slime, binocular, lens));
        event.getRegistry().registerAll(pad, dynamite, fried_egg, slime, binocular, lens);
        VT.logInfo("Registering OreDictionary");
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, i));
        }
        OreDictionary.registerOre("egg", VTItems.fried_egg);
        OreDictionary.registerOre("ingredientEgg", VTItems.fried_egg);
    }
}
