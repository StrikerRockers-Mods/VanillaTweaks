package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;

public class BinocularItem extends ArmorItem {
    public BinocularItem() {
        super(new BasicArmorMaterial(VanillaTweaks.MOD_ID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.of(net.minecraft.item.Items.IRON_INGOT))
                , EquipmentSlotType.HEAD, new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TOOLS));
        setRegistryName("binoculars");
    }
}
