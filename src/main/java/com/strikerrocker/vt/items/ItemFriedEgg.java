package com.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import static com.strikerrocker.vt.vt.proxy;

/**
 * Created by thari on 23/07/2017.
 */
public class ItemFriedEgg extends ItemFood implements ItemModelProvider {
    String name = "friedegg";

    public ItemFriedEgg() {
        super(3, 1f, false);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.FOOD);
    }

    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(item,0,name);
    }
}
