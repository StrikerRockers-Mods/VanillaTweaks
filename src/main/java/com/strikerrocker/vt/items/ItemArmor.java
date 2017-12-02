package com.strikerrocker.vt.items;

import net.minecraft.inventory.EntityEquipmentSlot;

import static com.strikerrocker.vt.vt.proxy;

public class ItemArmor extends net.minecraft.item.ItemArmor {


    private String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
    }

    public void registerItemModel() {
        proxy.registerItemRenderer(this, 0, name);
    }

}