package io.github.strikerrocker.vt.items;

import net.minecraft.inventory.EntityEquipmentSlot;


public class ItemArmor extends net.minecraft.item.ItemArmor {

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setTranslationKey(name);
        setMaxStackSize(1);
    }
}