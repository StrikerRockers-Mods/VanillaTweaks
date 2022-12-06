package io.github.strikerrocker.vt.content;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CommonObjects {
    public static final Item FRIED_EGG = new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.6f).build()).tab(CreativeModeTab.TAB_FOOD));
    public static final Block CHARCOAL_BLOCK = new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6.0f));
    public static final Item CHARCOAL_BLOCK_ITEM = new BlockItem(CHARCOAL_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final Block SUGAR_BLOCK = new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.SAND));
    public static final Item SUGAR_BLOCK_ITEM = new BlockItem(SUGAR_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final Block FLINT_BLOCK = new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1.0f, 6.0f));
    public static final Item FLINT_BLOCK_ITEM = new BlockItem(FLINT_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
}