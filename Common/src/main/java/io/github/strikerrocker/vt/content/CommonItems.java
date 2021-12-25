package io.github.strikerrocker.vt.content;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class CommonItems {
    public static final Item SLIME_BUCKET = new SlimeBucketItem();
    public static final Item FRIED_EGG = new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.6f).build()).tab(CreativeModeTab.TAB_FOOD));
    public static final Item CHARCOAL_BLOCK = new BlockItem(CommonBlocks.CHARCOAL_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final Item SUGAR_BLOCK = new BlockItem(CommonBlocks.SUGAR_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final Item FLINT_BLOCK = new BlockItem(CommonBlocks.FLINT_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
}