package com.strikerrocker.vt.items;

import net.minecraft.item.Item;

import static com.strikerrocker.vt.main.vt.proxy;

/**
 * Created by thari on 22/07/2017.
 */
public class ItemBase extends Item implements ItemModelProvider {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }


    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(this, 0, name);
    }


}

