package io.github.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;

public class ItemSlimeBucket extends ItemBase {

    public ItemSlimeBucket(String name) {
        super(name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        setUnlocalizedName(name);
    }

}
