package com.strikerrocker.vt.items;

import com.strikerrocker.vt.vt;
import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

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
        vt.proxy.registerItemRenderer(this,0,name);
    }
}