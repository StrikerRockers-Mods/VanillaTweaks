package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;

public class BinocularItem extends ArmorItem {
    public BinocularItem() {
        super(new BasicArmorMaterial(VTModInfo.MODID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.fromItems(net.minecraft.item.Items.IRON_INGOT))
                , EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS));
        setRegistryName("binoculars");
    }
}
