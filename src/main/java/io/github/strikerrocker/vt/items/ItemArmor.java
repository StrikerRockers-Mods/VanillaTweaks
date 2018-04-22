package io.github.strikerrocker.vt.items;

import net.minecraft.inventory.EntityEquipmentSlot;

import static io.github.strikerrocker.vt.vt.proxy;


public class ItemArmor extends net.minecraft.item.ItemArmor {


    private final String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
        setMaxStackSize(1);
    }

    public void registerItemModel() {
        proxy.registerItemRenderer(this, 0, name);
    }

}