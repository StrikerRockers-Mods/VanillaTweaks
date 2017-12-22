package com.strikerrocker.vt.blocks;

import com.strikerrocker.vt.blocks.pedestal.BlockPedestal;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.strikerrocker.vt.handlers.VTConfigHandler.storageBlocks;

/**
 * Contains, initializes, and registers all of Craft++'s blocks
 */
public class VTBlocks {

    public static BlockSugar sugar;
    public static BlockFlint flint;
    public static BlockCharcoal charcoal;
    public static BlockPedestal pedestal;

    public static void init() {
        if (storageBlocks) {
            sugar = register(new BlockSugar("sugarblock"));
            flint = register(new BlockFlint("flintblock"));
            charcoal = register(new BlockCharcoal("charcoalblock"));
        }
        if (VTConfigHandler.pedestal) {
            pedestal = register(new BlockPedestal());
        }
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if (itemBlock != null) {
            GameRegistry.register(itemBlock);

            if (block instanceof ItemModelProvider) {
                ((ItemModelProvider) block).registerItemModel(itemBlock);
            }
        }

        if (block instanceof BlockTileEntity) {
            GameRegistry.registerTileEntity(((BlockTileEntity<?>) block).getTileEntityClass(), block.getRegistryName().toString());
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }


}