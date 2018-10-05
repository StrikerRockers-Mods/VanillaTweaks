package io.github.strikerrocker.vt.blocks;

import io.github.strikerrocker.vt.blocks.pedestal.BlockPedestal;
import io.github.strikerrocker.vt.items.VTItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains, initializes, and registers all of Craft++'s blocks
 */
public class VTBlocks {

    private static final BlockSugar sugar = new BlockSugar("sugarblock");
    private static final Block flint = new Block(Material.SAND, MapColor.BROWN).setHardness(1.0f).setTranslationKey("flintblock").setRegistryName("flintblock");
    public static final BlockCharcoal charcoal = new BlockCharcoal("charcoalblock");
    public static final BlockPedestal pedestal = new BlockPedestal();
    public static final BlockBark acaciabark = new BlockBark("acaciabark", MapColor.STONE);
    public static final BlockBark darkoakbark = new BlockBark("darkoakbark", BlockPlanks.EnumType.DARK_OAK.getMapColor());
    public static final BlockBark oakbark = new BlockBark("oakbark", BlockPlanks.EnumType.OAK.getMapColor());
    public static final BlockBark sprucebark = new BlockBark("sprucebark", BlockPlanks.EnumType.SPRUCE.getMapColor());
    public static final BlockBark junglebark = new BlockBark("junglebark", BlockPlanks.EnumType.JUNGLE.getMapColor());
    public static final BlockBark birchbark = new BlockBark("birchbark", BlockPlanks.EnumType.BIRCH.getMapColor());
    public static List<Block> blocks = new ArrayList();


    public static void register(IForgeRegistry<Block> registry) {
        blocks.add(sugar);
        blocks.add(flint);
        blocks.add(charcoal);
        blocks.add(pedestal);
        blocks.add(acaciabark);
        blocks.add(darkoakbark);
        blocks.add(oakbark);
        blocks.add(sprucebark);
        blocks.add(junglebark);
        blocks.add(birchbark);
        for (Block block : blocks) {
            registry.register(block);
        }
        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName());
    }


    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (Block block : blocks) {
            Item itemBlock = createItemBlock(block);
            VTItems.items.add(itemBlock);
            registry.register(itemBlock);
        }
    }

    private static Item createItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }
}