package com.strikerrocker.vt.blocks;

import com.strikerrocker.vt.blocks.pedestal.BlockPedestal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains, initializes, and registers all of Craft++'s blocks
 */
public class VTBlocks {

    public static BlockSugar sugar;
    public static BlockFlint flint;
    public static BlockCharcoal charcoal;
    public static BlockPedestal pedestal;
    public static BlockBark acaciabark;
    public static BlockBark birchbark;
    public static BlockBark oakbark;
    public static BlockBark darkoakbark;
    public static BlockBark sprucebark;
    public static BlockBark junglebark;

    public static void init() {
        sugar = new BlockSugar("sugarblock");
        flint = new BlockFlint("flintblock");
        charcoal = new BlockCharcoal("charcoalblock");
        pedestal = new BlockPedestal();
        acaciabark = new BlockBark(Material.WOOD, "acaciabark", MapColor.STONE);
        darkoakbark = new BlockBark(Material.WOOD, "darkoakbark", BlockPlanks.EnumType.DARK_OAK.getMapColor());
        oakbark = new BlockBark(Material.WOOD, "oakbark", BlockPlanks.EnumType.SPRUCE.getMapColor());
        sprucebark = new BlockBark(Material.WOOD, "sprucebark", BlockPlanks.EnumType.DARK_OAK.getMapColor());
        junglebark = new BlockBark(Material.WOOD, "junglebark", BlockPlanks.EnumType.SPRUCE.getMapColor());
        birchbark = new BlockBark(Material.WOOD, "birchbark", MapColor.QUARTZ);
    }


    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                flint, sugar, pedestal, charcoal, acaciabark, birchbark,
                darkoakbark, junglebark, oakbark, sprucebark
        );

        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName().toString());
    }


    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                pedestal.createItemBlock(), sugar.createItemBlock(), flint.createItemBlock(),
                charcoal.createItemBlock(), acaciabark.createItemBlock(), birchbark.createItemBlock(),
                oakbark.createItemBlock(), darkoakbark.createItemBlock(), sprucebark.createItemBlock(),
                junglebark.createItemBlock()
        );
    }

    public static void registerModels() {
        sugar.registerItemModel(Item.getItemFromBlock(sugar));
        charcoal.registerItemModel(Item.getItemFromBlock(charcoal));
        flint.registerItemModel(Item.getItemFromBlock(flint));
        pedestal.registerItemModel(Item.getItemFromBlock(pedestal));
        acaciabark.registerItemModel(Item.getItemFromBlock(acaciabark));
        birchbark.registerItemModel(Item.getItemFromBlock(birchbark));
        darkoakbark.registerItemModel(Item.getItemFromBlock(darkoakbark));
        oakbark.registerItemModel(Item.getItemFromBlock(oakbark));
        sprucebark.registerItemModel(Item.getItemFromBlock(sprucebark));
        junglebark.registerItemModel(Item.getItemFromBlock(junglebark));
    }
}