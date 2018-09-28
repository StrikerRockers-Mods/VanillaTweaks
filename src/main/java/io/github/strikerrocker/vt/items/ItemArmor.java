package io.github.strikerrocker.vt.items;

import net.minecraft.inventory.EntityEquipmentSlot;


public class ItemArmor extends net.minecraft.item.ItemArmor {


    private final String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setTranslationKey(name);
        this.name = name;
        setMaxStackSize(1);
    }
}