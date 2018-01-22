package com.strikerrocker.vt.blocks;

import com.strikerrocker.vt.blocks.pedestal.BlockPedestal;
import net.minecraft.block.Block;
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

    public static void init() {
            sugar = new BlockSugar("sugarblock");
            flint = new BlockFlint("flintblock");
            charcoal = new BlockCharcoal("charcoalblock");
            pedestal = new BlockPedestal();
    }


    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                flint,
                sugar,
                pedestal,
                charcoal
        );

        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName().toString());
    }


    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                pedestal.createItemBlock(),
                sugar.createItemBlock(),
                flint.createItemBlock(),
                charcoal.createItemBlock()

        );
    }

    public static void registerModels() {
        sugar.registerItemModel(Item.getItemFromBlock(sugar));
        charcoal.registerItemModel(Item.getItemFromBlock(charcoal));
        flint.registerItemModel(Item.getItemFromBlock(flint));
        pedestal.registerItemModel(Item.getItemFromBlock(pedestal));
    }
}