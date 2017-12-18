package com.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;

public class ItemSlimeBucket extends ItemBase {

    private String name;

    public ItemSlimeBucket(String name) {
        super(name);
        this.name = name;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        setUnlocalizedName(name);
    }

}
