package com.strikerrocker.vt.items;

import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel() {
        proxy.registerItemRenderer(this, 0, name);
    }


}