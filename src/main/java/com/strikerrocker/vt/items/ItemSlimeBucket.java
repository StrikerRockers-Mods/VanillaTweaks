package com.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

public class ItemSlimeBucket extends ItemBase {

    private String name;

    public ItemSlimeBucket(String name) {
        super(name);
        this.name = name;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        setUnlocalizedName(name);
    }

    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(this,0,name);
    }
}
