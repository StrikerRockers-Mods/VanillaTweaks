package io.github.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

import static io.github.strikerrocker.vt.vt.proxy;

public class ItemFriedEgg extends ItemFood
{
    public ItemFriedEgg() {
        super(3, 0.6f, false);
        setUnlocalizedName("friedegg");
        setRegistryName("friedegg");
        setCreativeTab(CreativeTabs.FOOD);
    }

    public void registerItemModel() {
        proxy.registerItemRenderer(this, 0, "friedegg");
    }
}
