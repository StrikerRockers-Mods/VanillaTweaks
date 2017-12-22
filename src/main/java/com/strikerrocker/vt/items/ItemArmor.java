package com.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

public class ItemArmor extends net.minecraft.item.ItemArmor implements ItemModelProvider {


    private String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }


    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(item, 0, name);
    }
}