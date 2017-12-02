package com.strikerrocker.vt.items;

import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

/**
 * Created by thari on 22/07/2017.
 */
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