package io.github.strikerrocker.vt.content;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class CommonObjects {
    public static final Item FRIED_EGG = new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.6f).build()));
    public static final Block CHARCOAL_BLOCK = new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6.0f));
    public static final Item CHARCOAL_BLOCK_ITEM = new BlockItem(CHARCOAL_BLOCK, new Item.Properties());
    public static final Block SUGAR_BLOCK = new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.SAND));
    public static final Item SUGAR_BLOCK_ITEM = new BlockItem(SUGAR_BLOCK, new Item.Properties());
    public static final Block FLINT_BLOCK = new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1.0f, 6.0f));
    public static final Item FLINT_BLOCK_ITEM = new BlockItem(FLINT_BLOCK, new Item.Properties());
}
