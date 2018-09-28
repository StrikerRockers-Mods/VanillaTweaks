package io.github.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFriedEgg extends ItemFood {
    public ItemFriedEgg() {
        super(3, 0.6f, false);
        setTranslationKey("friedegg");
        setRegistryName("friedegg");
        setCreativeTab(CreativeTabs.FOOD);
    }
}
